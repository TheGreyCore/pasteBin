package org.matetski.pastebin.service;

import org.matetski.pastebin.dto.CreateNewPasteRequestDTO;
import org.matetski.pastebin.dto.GetPasteContentResponseDTO;
import org.matetski.pastebin.dto.UpdatePasteFileDTO;
import org.matetski.pastebin.exceptions.AccountLimitReached;
import org.matetski.pastebin.exceptions.BlobWasNotCreated;
import org.matetski.pastebin.exceptions.OpenIDNotFound;
import org.matetski.pastebin.repository.StorageRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

/**
 * Service class for handling API related operations.
 * This class is annotated with @Service to indicate that it is a service component.
 */
@Service
public class ApiService {
    /**
     * StorageService instance for performing storage related operations.
     */
    private final StorageService storageService;

    /**
     * BlobService instance for performing blob related operations.
     */
    private final BlobService blobService;

    /**
     * StorageRepository instance for performing actions related to the storage database.
     */
    private final StorageRepository storageRepository;

    /**
     * Logger for logging exceptions.
     */
    Logger logger = Logger.getLogger(ApiService.class.getName());

    /**
     * Constructor for ApiService.
     * @param storageService An instance of StorageService.
     * @param blobService An instance of BlobService.
     */
    public ApiService(StorageService storageService, BlobService blobService, StorageRepository storageRepository) {
        this.storageService = storageService;
        this.blobService = blobService;
        this.storageRepository = storageRepository;
    }

    /**
     * Method for creating a new bin.
     *
     * @param request   A request representation for creating a new bin.
     * @param principal principal of logged user.
     * @return A ResponseEntity with the result of the operation.
     */
    public String createNewBin(CreateNewPasteRequestDTO request, OAuth2User principal) throws AccountLimitReached, BlobWasNotCreated {
        if (!storageService.userCanCreateMore(principal.getAttribute("sub")))
            throw new AccountLimitReached("User reached his limit.");

        LocalDate expiryDate = LocalDate.now().plusDays(request.getExpireTimeInDays());

        try {
            String fileName = generateFilename();
            blobService.createBlobFile(request.getBody(), fileName);
            storageService.createStorage(fileName, principal.getAttribute("sub"), expiryDate);
            return fileName;
        } catch (BlobWasNotCreated e) {
            logger.warning("An exception was thrown: " + e);
            throw new BlobWasNotCreated("Blob was not created due to error! Please wait sometime and try again!");
        }
    }

    /**
     * Generates a filename for a new blob file.
     *
     * @return The generated filename.
     */
    private static String generateFilename() {
        long timestamp = Instant.now().toEpochMilli();
        int randomNumber = new Random().nextInt(9000) + 1000;
        String fileName = timestamp + "_" + randomNumber;
        return new String(Base64.getEncoder().encode(fileName.getBytes()));
    }

    /**
     * Method for getting a bin by URL.
     *
     * @param url The URL of the bin to retrieve.
     * @return A GetPasteContentResponseDTO which contains paste content (body) and expire date.
     */
    public GetPasteContentResponseDTO getPasteDataByURL(String url) throws InaccessibleObjectException{
        try{
            String body = blobService.readBlobFile(url);
            LocalDate expireDate = storageRepository.findExpireDateByBlobFileName(url);
            return new GetPasteContentResponseDTO(body, expireDate);
        } catch (IOException e)
        {
            logger.warning("An exception was thrown: " + e);
            throw new InternalError("An internal error was happened.");
        }
    }

    /**
     * Method for getting user data, include first and second name, openid and list of paste names that saved by user.
     * @param principal principal of logged user.
     * @return A ResponseEntity with of data described above.
     */
    public Map<String, Object> getUserData(OAuth2User principal) throws OpenIDNotFound {
        Map<String, Object> userData = new HashMap<>();
        userData.put("first_name", principal.getAttribute("given_name"));
        userData.put("family_name", principal.getAttribute("family_name"));

        Object openId = principal.getAttribute("sub");
        if (openId == null) {
            throw new OpenIDNotFound("Given openId not found!");
        }
        userData.put("openid", openId);

        Optional<List<String>> optionalAllPaste = storageRepository.findAllByIndificator((String) openId);
        List<String> allBins;
        if(optionalAllPaste.isPresent())
        {
            allBins = optionalAllPaste.get();
            userData.put("binNames", allBins);
        }

        return userData;
    }

    /**
     * Method for updating blob file content by overwriting them.
     * @param updatePasteFileDTO Data representation.
     * @param principal Represent an authorized user.
     */
    public void updatePaste(UpdatePasteFileDTO updatePasteFileDTO, OAuth2User principal) throws AccessDeniedException, InternalError {
        if(checkIfUserOwner(principal, updatePasteFileDTO.getFileName()))
            throw new AccessDeniedException("Access denied, only creator of the paste can change it.");

        blobService.updateBlobFile(updatePasteFileDTO);
    }

    /**
     * Method for deleting bin file and metadata.
     * @param principal represents an authorized user
     * @param url url to be deleted
     */
    public void deletePaste(OAuth2User principal, String url) throws AccessDeniedException {
        if(checkIfUserOwner(principal, url))
            throw new AccessDeniedException("Access denied, only creator of the paste can delete it.");

        storageRepository.deleteByBlobName(url);
        blobService.deleteBlobFile(url);
    }

    /**
     * Help method for checking if the given user is owner of the bin.
     * @param principal represents an authorized user
     * @param url bin to be checked
     * @return true if owner, false if not owner.
     */
    private boolean checkIfUserOwner(OAuth2User principal, String url){
        String userId = principal.getAttribute("sub");
        return !Objects.equals(userId, storageRepository.findOwnerByBlobFileName(url));
    }
}
