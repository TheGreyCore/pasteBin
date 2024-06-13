package org.matetski.pastebin.service;

import org.matetski.pastebin.entity.Storage;
import org.matetski.pastebin.repository.StorageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * Service class for managing storage in the application.
 */
@Service
public class StorageService{
    /**
     * The repository for interacting with the storage data.
     */
    private final StorageRepository storageRepository;

    /**
     * The service for managing blobs.
     */
    private final BlobService blobService;

    /**
     * Constructs a new StorageService.
     *
     * @param storageRepository The repository for the storage data.
     * @param blobService The service for managing blobs.
     */
    public StorageService(StorageRepository storageRepository, BlobService blobService) {
        this.storageRepository = storageRepository;
        this.blobService = blobService;
    }

    /**
     * Checks if a user can create more storage.
     *
     * @param uniqIdentificator The unique identifier of the user.
     * @return true if the user can create more storage, false otherwise.
     */
    public boolean userCanCreateMore(String uniqIdentificator){
        return storageRepository.countUserData(uniqIdentificator) < 3; // TODO: Change to 3
    }

    /**
     * Creates a new storage.
     *
     * @param blobFileName The name of the blob file.
     * @param uniqIdentificator The unique identifier of the user.
     * @param expireDate The expiration date of the storage.
     */
    public void createStorage(String blobFileName, String uniqIdentificator, LocalDate expireDate){
        storageRepository.save(new Storage(blobFileName, expireDate, uniqIdentificator));
    }

    /**
     * Checks the expiry data of the storage and deletes expired storage.
     *
     * @return The number of bins that were deleted.
     */
    public String checkExpiryData() {
        System.out.println(LocalDate.now());
        Optional<List<String>> expiryData = storageRepository.findAllIdByExpireDate(LocalDate.now());
        if (expiryData.isEmpty()) return "0 bins was deleted.";

        for (String blobName : expiryData.get())
        {
            blobService.deleteBlobFile(blobName);
            storageRepository.deleteByBlobName(blobName);
        }

        return  expiryData.get().size() + " bins was deleted.";
    }

}
