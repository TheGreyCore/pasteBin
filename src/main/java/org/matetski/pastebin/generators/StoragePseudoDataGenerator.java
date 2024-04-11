package org.matetski.pastebin.generators;

import org.matetski.pastebin.entity.Storage;
import org.springframework.boot.CommandLineRunner;
import org.matetski.pastebin.repository.StorageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class StoragePseudoDataGenerator {

//    @Bean
//    CommandLineRunner commandLineRunner(StorageRepository storageRepository) {
//        return args -> {
//            Storage storage = new Storage(
//                    "test.txt",
//                    LocalDate.now().plusDays(7),
//                    "1111"
//            );
//
//            storageRepository.saveAll(
//                    List.of(storage)
//            );
//        };
//    }
}
