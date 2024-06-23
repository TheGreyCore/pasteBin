package org.matetski.pastebin.controller;

import org.matetski.pastebin.dto.CreateNewPasteRequestDTO;
import org.matetski.pastebin.dto.ErrorDetailsDTO;
import org.matetski.pastebin.dto.UpdatePasteFileDTO;
import org.matetski.pastebin.exceptions.AccountLimitReached;
import org.matetski.pastebin.exceptions.BlobWasNotCreated;
import org.matetski.pastebin.exceptions.OpenIDNotFound;
import org.matetski.pastebin.service.ApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InaccessibleObjectException;
import java.nio.file.AccessDeniedException;

/**
 * Rest Controller for handling API requests.
 * This class is annotated with @RestController to indicate that it is a RESTFull controller.
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
     */
    @PostMapping("/createNewPaste")
    private ResponseEntity<?> createNewPaste(@RequestBody CreateNewPasteRequestDTO requestDTO, @AuthenticationPrincipal OAuth2User principal){
        try {
            String createdUrl = apiService.createNewBin(requestDTO, principal);
            return new ResponseEntity<>(createdUrl, HttpStatus.CREATED);
        } catch (AccountLimitReached e) {
            return new ResponseEntity<>(new ErrorDetailsDTO("You have exceeded your allotted paste creation limit."), HttpStatus.TOO_MANY_REQUESTS);
        } catch (BlobWasNotCreated blobWasNotCreatedError) {
            return new ResponseEntity<>(new ErrorDetailsDTO("Paste wasn't created, please reach us if error appears again after several more tries."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET Endpoint for getting a paste by URL.
     * @param url The URL of the bin to retrieve.
     */
    @GetMapping("/getPasteByURL")
    private ResponseEntity<?> getPasteByURL(@RequestParam String url){
        try {
            return new ResponseEntity<>(apiService.getPasteDataByURL(url), HttpStatus.OK);
        } catch (InternalError e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ErrorDetailsDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InaccessibleObjectException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ErrorDetailsDTO("You are trying to access an inaccessible paste."), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * POST endpoint for updating paste content.
     * @param updatePasteFileDTO represent user request.
     * @param principal Represent authorized user.
     */
    @PostMapping("/updatePaste")
    private ResponseEntity<?> updatePaste(@RequestBody UpdatePasteFileDTO updatePasteFileDTO, @AuthenticationPrincipal OAuth2User principal){
        try {
            apiService.updatePaste(updatePasteFileDTO, principal);
            return ResponseEntity.ok().body("");
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(new ErrorDetailsDTO(e.getMessage()), HttpStatus.FORBIDDEN);
        } catch (InternalError e){
            return new ResponseEntity<>(new ErrorDetailsDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * GET endpoint for getting user data by it principal
     * @param principal Represent authorized user.
     * @return user information (Name, family name, openID, pastes) TODO: Check and change
     */
    @GetMapping("/user")
    private ResponseEntity<?> getUserData(@AuthenticationPrincipal OAuth2User principal){
        try {
            return new ResponseEntity<>(apiService.getUserData(principal), HttpStatus.OK);
        } catch (OpenIDNotFound e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DELETE endpoint for deleting a paste by URL
     * @param principal Represent authorized user.
     * @param url Bin URL
     */
    @DeleteMapping("/deletePaste")
    private ResponseEntity<?> deletePaste(@AuthenticationPrincipal OAuth2User principal, @RequestParam String url){
        try{
            apiService.deletePaste(principal, url);
            return new ResponseEntity<>( "URL: " + url + " - deleted", HttpStatus.OK);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(new ErrorDetailsDTO(e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }
}
