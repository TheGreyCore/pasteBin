package org.matetski.pastebin.microservice.blobstorage;


import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;

@Service
public class BlobService {
    private final String fileFormat = ".txt";

    @Autowired
    BlobServiceClient blobServiceClient;

    @Autowired
    BlobContainerClient blobContainerClient;

    public void updateBlobFile(String fileName, String body){
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName + fileFormat);
        blobClient.upload(BinaryData.fromString(body), true);
    }

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

    public void deleteBlobFile(String fileName){
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName + fileFormat);
        blobClient.deleteIfExists();
    }

    public void postBlobFile(String fileName, String body) {
        BlobClient blobClient = blobContainerClient.getBlobClient(fileName + fileFormat);
        blobClient.upload(BinaryData.fromString(body), false);
    }

    public String status(){
        if (blobContainerClient.exists()) return "blob ok";
        else return "blob not okay";
    }
}
