package org.matetski.pastebin.service;

import org.matetski.pastebin.representations.CreateNewBinRequestRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

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
     * Constructor for ApiService.
     * @param storageService An instance of StorageService.
     * @param blobService An instance of BlobService.
     */
    public ApiService(StorageService storageService, BlobService blobService) {
        this.storageService = storageService;
        this.blobService = blobService;
    }

    /**
     * Method for creating a new bin.
     * @param request A request representation for creating a new bin.
     * @return A ResponseEntity with the result of the operation.
     */
    public ResponseEntity<String> createNewBin(CreateNewBinRequestRepresentation request) {
        if (!storageService.userCanCreateMore(request.getIdentificator())) return ResponseEntity.accepted().body("User can not create more bins!");

        LocalDate expiryDate = LocalDate.now().plusDays(request.getExpireTimeInDays());
        String fileName = blobService.createBlobFile(request.getBody());
        if (fileName == null) return ResponseEntity.internalServerError().body("Blob wasn't created!");
        storageService.createStorage(fileName, request.getIdentificator(), expiryDate);
        String encodedURL = new String(Base64.getEncoder().encode(fileName.getBytes()));

        return ResponseEntity.ok().body("Was created with url" + encodedURL);
    }


    /**
     * Method for getting a bin by URL.
     * @param url The URL of the bin to retrieve.
     * @return A ResponseEntity with the result of the operation.
     * @throws IOException If an I/O error occurs. TODO: Better exception handling
     */
    public ResponseEntity<String> getBinByURL(String url) throws IOException {
        String decodedURL = new String(Base64.getDecoder().decode(url));
        String body = blobService.readBlobFile(decodedURL);
        return ResponseEntity.ok().body(body);
    }
}
