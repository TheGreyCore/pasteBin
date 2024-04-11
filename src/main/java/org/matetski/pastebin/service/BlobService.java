package org.matetski.pastebin.service;


import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.time.Instant;
import java.util.Random;

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
     * Updates the content of a blob file.
     *
     * @param fileName The name of the file.
     * @param body The new content for the file.
     */
    public void updateBlobFile(String fileName, String body){
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName + fileFormat);
        blobClient.upload(BinaryData.fromString(body), true);
    }

    /**
     * Reads the content of a blob file.
     *
     * @param fileName The name of the file.
     * @return The content of the file.
     * @throws IOException If an I/O error occurs.
     */
    public String readBlobFile(String fileName) throws IOException {
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName + fileFormat);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            blobClient.downloadStream(outputStream);
            return outputStream.toString();
        }
        catch (InaccessibleObjectException error)
        {
            return null;
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
     * @return The name of the new file.
     */
    public String createBlobFile(String body) {
        String fileName = generateFilename();
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName + fileFormat);
        blobClient.upload(BinaryData.fromString(body), false);
        return fileName;
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

    /**
     * Generates a filename for a new blob file.
     *
     * @return The generated filename.
     */
    private static String generateFilename() {
        long timestamp = Instant.now().toEpochMilli();
        int randomNumber = new Random().nextInt(9000) + 1000;
        return timestamp + "_" + randomNumber + ".txt";
    }
}
