package org.matetski.pastebin.service;


import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobStorageException;
import org.matetski.pastebin.dto.UpdatePasteFileDTO;
import org.matetski.pastebin.exceptions.BlobWasNotCreated;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.util.logging.Logger;

/**
 * Service class for managing blobs in Azure Blob Storage.
 */
@Service
public class BlobService {
    /**
     * The file format for the blobs.
     */
    private final String fileFormat = ".txt";

    /**
     * The client for interacting with the Azure Blob Storage container.
     */
    private final BlobContainerClient blobContainerClient;

    /**
     * Constructs a new BlobService.
     *
     * @param blobContainerClient The client for the Azure Blob Storage container.
     */
    public BlobService(BlobContainerClient blobContainerClient) {
        this.blobContainerClient = blobContainerClient;
    }

    /**
     * Logger for logging exceptions.
     */
    Logger logger = Logger.getLogger(BlobService.class.getName());

    /**
     * Updates the content of a blob file.
     *
     * @param updatePasteFileDTO Represent an request DTO.
     */
    public void updateBlobFile(UpdatePasteFileDTO updatePasteFileDTO){
        try {
            System.out.println(updatePasteFileDTO.getFileName());
            System.out.println(updatePasteFileDTO.getBody());
            BlobClient blobClient = blobContainerClient.getBlobClient(updatePasteFileDTO.getFileName() + ".txt");
            blobClient.upload(BinaryData.fromString(updatePasteFileDTO.getBody()), true);
        } catch (BlobStorageException e){
            logger.warning("An exception was thrown: " + e);
            throw new InternalError("An internal error occurred!");
        }
    }

    /**
     * Reads the content of a blob file.
     *
     * @param fileName The name of the file.
     * @return The content of the file.
     * @throws IOException If an I/O error occurs.
     */
    public String readBlobFile(String fileName) throws IOException, InaccessibleObjectException {
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName + fileFormat);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            blobClient.downloadStream(outputStream);
            return outputStream.toString();
        }
    }

    /**
     * Deletes a blob file if it exists.
     *
     * @param fileName The name of the file.
     */
    public void deleteBlobFile(String fileName){
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName + fileFormat);
        blobClient.deleteIfExists();
    }

    /**
     * Creates a new blob file.
     *
     * @param body The content for the new file.
     * @param fileName The name of the new file.
     * @throws BlobWasNotCreated When some error appears.
     */
    public void createBlobFile(String body, String fileName) throws BlobWasNotCreated {
        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(fileName + fileFormat);
            blobClient.upload(BinaryData.fromString(body), false);
        } catch (Exception e){
            throw new BlobWasNotCreated(e);
        }
    }

    /**
     * Checks the status of the blob container.
     *
     * @return "blob ok" if the blob container exists, "blob not okay" otherwise.
     */
    public String status(){
        if (blobContainerClient.exists()) return "blob ok";
        else return "blob not okay";
    }
}
