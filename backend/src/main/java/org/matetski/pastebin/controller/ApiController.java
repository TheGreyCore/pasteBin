package org.matetski.pastebin.controller;

import org.matetski.pastebin.dto.CreateNewBinRequestDTO;
import org.matetski.pastebin.dto.UpdateBlobFileDTO;
import org.matetski.pastebin.exceptions.AccountLimitReached;
import org.matetski.pastebin.service.ApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Map;

/**
 * Rest Controller for handling API requests.
 * This class is annotated with @RestController to indicate that it is a RESTful controller.
 * The @RequestMapping("/api") annotation is used to map web requests onto specific handler methods.
 */
@RestController()
@RequestMapping("/api")
public class ApiController {
    /**
     * ApiService instance for performing API related operations.
     */
    private final ApiService apiService;
    /**
     * Constructor for ApiController.
     * @param apiService An instance of ApiService.
     */
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Endpoint for creating a new bin.
     * @param requestDTO A request DTO for creating a new bin.
     * @return A ResponseEntity with the result of the operation.
     */
    @PostMapping("/createNewBin")
    private ResponseEntity<?> createNewBin(@RequestBody CreateNewBinRequestDTO requestDTO, @AuthenticationPrincipal OAuth2User principal){
        try {
            String createdUrl = apiService.createNewBin(requestDTO, principal);
            return new ResponseEntity<>(createdUrl, HttpStatus.CREATED);
        } catch (AccountLimitReached e) {
            return new ResponseEntity<>("You have exceeded your allotted paste creation limit.", HttpStatus.TOO_MANY_REQUESTS);
        }
    }

    /**
     * GET Endpoint for getting a bin by URL.
     * @param url The URL of the bin to retrieve.
     * @return A ResponseEntity with the result of the operation.
     * @throws IOException If an I/O error occurs. TODO: Better exception handling
     */
    @GetMapping("/getBinByURL")
    private ResponseEntity<Map<String, String>> getBinByURL(@RequestParam String url) throws IOException {
        return apiService.getBinByURL(url);
    }

    @PostMapping("/updateBlob")
    private ResponseEntity<?> updateBlob(@RequestBody UpdateBlobFileDTO updateBlobFileDTO, @AuthenticationPrincipal OAuth2User principal){
        return apiService.updateBlob(updateBlobFileDTO, principal);
    }

    @GetMapping("/user")
    private ResponseEntity<?> getUserData(@AuthenticationPrincipal OAuth2User principal){
        return apiService.getUserData(principal);
    }

    /**
     * DELETE endpoint for deleting a bin by URL
     * @param principal Represent logged user.
     * @param url Bin URL
     */
    @DeleteMapping("/deleteBin")
    private ResponseEntity<?> deleteBin(@AuthenticationPrincipal OAuth2User principal, @RequestParam String url){
        return apiService.deleteBin(principal, url);
    }
}
