package org.matetski.pastebin.microservice.blobstorage;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("blob")
public class BlobController {
    private final BlobService blobService;

    public BlobController(BlobService blobService) {
        this.blobService = blobService;
    }

    @PostMapping
    public void postBlobFile(@RequestBody String fileName,
            @RequestBody String body){
        this.blobService.postBlobFile(fileName, body);
    }

    @DeleteMapping
    public void deleteBlobFile(@RequestBody String fileName){
        this.blobService.deleteBlobFile(fileName);
    }

    @PutMapping
    public void updateBlobFile(@RequestBody String fileName,
                               @RequestBody String body){
        this.blobService.updateBlobFile(fileName, body);
    }

    @GetMapping
    public String readBlobFile(@RequestBody String fileName) throws IOException {
        return this.blobService.readBlobFile(fileName);
    }

    @GetMapping("/api")
    public String status(){
        return this.blobService.status();
    }
}