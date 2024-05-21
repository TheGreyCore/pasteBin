package org.matetski.pastebin.controller;

import org.matetski.pastebin.service.BlobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for handling blob related requests.
 * This class is annotated with @RestController to indicate that it is a RESTful controller.
 * The @RequestMapping("blob") annotation is used to map web requests onto specific handler methods.
 */
@RestController
@RequestMapping("blob")
public class BlobController {

    /**
     * BlobService instance for performing blob related operations.
     */
    private final BlobService blobService;

    /**
     * Constructor for BlobController.
     * @param blobService An instance of BlobService.
     */
    public BlobController(BlobService blobService) {
        this.blobService = blobService;
    }

    /**
     * Endpoint for checking the status of the blob service.
     * This method is annotated with @GetMapping to indicate that it is a GET request.
     * @return The status of the blob service.
     */
    @GetMapping("/api")
    public String status(){
        return this.blobService.status();
    }
}