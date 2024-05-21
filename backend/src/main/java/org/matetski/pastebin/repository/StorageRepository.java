package org.matetski.pastebin.repository;

import org.matetski.pastebin.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    @Query("SELECT COUNT(s.uniqIdentificatorOfCreator) FROM Storage s WHERE s.uniqIdentificatorOfCreator = ?1")
    int countUserData(String uniqIdentificator);

    @Query("SELECT blobFileName FROM Storage WHERE expireDate = ?1")
    Optional<List<String>>  findAllIdByExpireDate(LocalDate date);

    @Query("DELETE FROM Storage WHERE blobFileName = ?1")
    void deleteByBlobName(String blobName);
}
