package org.matetski.pastebin.controller;

import org.matetski.pastebin.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for handling storage related requests.
 * This class is annotated with @RestController to indicate that it is a RESTful controller.
 * The @RequestMapping("/storage") annotation is used to map web requests onto specific handler methods.
 */
@RestController
@RequestMapping("/storage")
public class StorageController {

    /**
     * StorageService instance for performing storage related operations.
     */
    private final StorageService storageService;

    /**
     * Constructor for StorageController.
     * @param storageService An instance of StorageService.
     */
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * Endpoint for checking expiry data.
     * This method is annotated with @DeleteMapping to indicate that it is a DELETE request.
     * @return A ResponseEntity with the result of the operation.
     */
    @DeleteMapping("/checkExpiryData")
    public ResponseEntity<String> checkExpiryData(){
        return ResponseEntity.ok().body(storageService.checkExpiryData());
    }
}

