package org.matetski.pastebin.service;

import org.matetski.pastebin.dto.UpdateBlobFileDTO;
import org.matetski.pastebin.repository.StorageRepository;
import org.matetski.pastebin.dto.CreateNewBinRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

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
    public ResponseEntity<?> createNewBin(CreateNewBinRequestDTO request, OAuth2User principal) {
        if (!storageService.userCanCreateMore(principal.getAttribute("sub"))) return ResponseEntity.accepted().body("User can not create more bins!");

        LocalDate expiryDate = LocalDate.now().plusDays(request.getExpireTimeInDays());

        String fileName = generateFilename();
        boolean blobWasCreated = blobService.createBlobFile(request.getBody(), fileName);
        if (!blobWasCreated) return ResponseEntity.internalServerError().body(Collections.singletonMap("error",  "Blob wasn't created!"));
        storageService.createStorage(fileName, principal.getAttribute("sub"), expiryDate);

        return ResponseEntity.ok().body(Collections.singletonMap("url", fileName));
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
     * @param url The URL of the bin to retrieve.
     * @return    A ResponseEntity with the result of the operation.
     * @throws IOException If an I/O error occurs. TODO: Better exception handling
     */
    public ResponseEntity<Map<String, String>> getBinByURL(String url) throws IOException {
        String body = blobService.readBlobFile(url);
        return ResponseEntity.ok().body(Collections.singletonMap("body", body));
    }

    /**
     * Method for getting user data, include first and second name, openid and list of bins names that saved by user.
     * @param principal principal of logged user.
     * @return A ResponseEntity with of data described above.
     */
    public ResponseEntity<Map<String, Object>> getUserData(OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("first_name", principal.getAttribute("given_name"));
        response.put("family_name", principal.getAttribute("family_name"));

        Object openId = principal.getAttribute("sub");
        if (openId == null) {
            throw new RuntimeException("openId not found!");
        }
        response.put("openid", openId);

        Optional<List<String>> optionalAllBins = storageRepository.findAllByIndificator((String) openId);
        List<String> allBins;
        if(optionalAllBins.isPresent())
        {
            allBins = optionalAllBins.get();
            response.put("binNames", allBins);
        }

        return ResponseEntity.ok().body(response);
    }

    /**
     * Method for updating blob file content by overwriting them.
     * @param updateBlobFileDTO Data representation.
     * @param principal principal of logged user.
     */
    public ResponseEntity<?> updateBlob(UpdateBlobFileDTO updateBlobFileDTO, OAuth2User principal) {
        // Check if user can update this blob
        if(checkIfUserOwner(principal, updateBlobFileDTO.getFileName()))
            return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);

        boolean wasUpdated = blobService.updateBlobFile(updateBlobFileDTO);
        if(wasUpdated) return ResponseEntity.ok().body("ok");
        else return ResponseEntity.internalServerError().body("Not ok :(");
    }

    /**
     * Method for deleting bin file and metadata.
     * @param principal represents a logged-in user
     * @param url url to be deleted
     * @return //TODO: void instead of responseEntity.
     */
    public ResponseEntity<?> deleteBin(OAuth2User principal, String url) {
        if(checkIfUserOwner(principal, url))
            return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);

        blobService.deleteBlobFile(url);

        return ResponseEntity.ok().body("Deleted");
    }

    /**
     * Help method for checking if the given user is owner of the bin.
     * @param principal represents a logged-in user
     * @param url bin to be checked
     * @return true if owner, false if not owner.
     */
    private boolean checkIfUserOwner(OAuth2User principal, String url){
        String userId = principal.getAttribute("sub");
        return !Objects.equals(userId, storageRepository.findOwnerByBlobFileName(url));
    }
}
