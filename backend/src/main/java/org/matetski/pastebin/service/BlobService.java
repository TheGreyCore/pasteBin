package org.matetski.pastebin.service;


import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import org.matetski.pastebin.dto.UpdateBlobFileDTO;
import org.matetski.pastebin.exceptions.BlobWasNotCreated;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;

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
     * @param updateBlobFileDTO Represent an request DTO.
     * @return
     */
    public boolean updateBlobFile(UpdateBlobFileDTO updateBlobFileDTO){
        try {
            System.out.println(updateBlobFileDTO.getFileName());
            System.out.println(updateBlobFileDTO.getBody());
            BlobClient blobClient = blobContainerClient.getBlobClient(updateBlobFileDTO.getFileName() + ".txt");
            blobClient.upload(BinaryData.fromString(updateBlobFileDTO.getBody()), true);
            return true;
        } catch (Exception e){
            return false;
        }
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
