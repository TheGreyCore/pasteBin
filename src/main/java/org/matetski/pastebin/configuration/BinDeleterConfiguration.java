package org.matetski.pastebin.configuration;

import org.matetski.pastebin.service.StorageService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.logging.Logger;

/**
 * Configuration class for scheduling tasks related to bin deletion.
 * This class is annotated with @Configuration to indicate that it is a source of bean definitions.
 * The @EnableScheduling annotation is used to switch on Spring’s scheduling.
 */
@Configuration
@EnableScheduling
public class BinDeleterConfiguration {
    /**
     * Logger instance for logging events.
     */
    Logger logger = Logger.getLogger(BinDeleterConfiguration.class.getName());
    /**
     * StorageService instance for performing storage related operations.
     */
    private final StorageService storageService;

    /**
     * Constructor for BinDeleterConfiguration.
     * @param storageService An instance of StorageService.
     */
    public BinDeleterConfiguration(StorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * Scheduled task for deleting expired data.
     * This method is annotated with @Scheduled to indicate that it is a scheduled task.
     * The cron expression "0 23 50 * * ?" specifies running code every day at 23.50 by Estonian time.
     */
    @Scheduled(cron = "0 23 50 * * ?", zone = "EET")
    public void deleteExpiryDate(){
        logger.info(storageService.checkExpiryData());
    }
}
