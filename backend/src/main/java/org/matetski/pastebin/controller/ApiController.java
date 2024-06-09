package org.matetski.pastebin.controller;

import org.matetski.pastebin.dto.CreateNewBinRequestDTO;
import org.matetski.pastebin.service.ApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
        return apiService.createNewBin(requestDTO, principal);
    }

    /**
     * Endpoint for getting a bin by URL.
     * This method is annotated with @GetMapping to indicate that it is a GET request.
     * @param url The URL of the bin to retrieve.
     * @return A ResponseEntity with the result of the operation.
     * @throws IOException If an I/O error occurs. TODO: Better exception handling
     */
    @GetMapping("/getBinByURL")
    private ResponseEntity<String> getBinByURL(@RequestParam String url) throws IOException {
        return apiService.getBinByURL(url);
    }


    @GetMapping("/user")
    private ResponseEntity<?> getUserData(@AuthenticationPrincipal OAuth2User principal){
        return apiService.getUserData(principal);
    }
}
