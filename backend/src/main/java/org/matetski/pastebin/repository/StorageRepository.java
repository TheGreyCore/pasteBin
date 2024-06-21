package org.matetski.pastebin.repository;

import jakarta.transaction.Transactional;
import org.matetski.pastebin.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface StorageRepository extends JpaRepository<Storage, Long> {
    @Query("SELECT COUNT(s.uniqIdentificatorOfCreator) FROM Storage s WHERE s.uniqIdentificatorOfCreator = ?1")
    int countUserData(String uniqIdentificator);

    @Query("SELECT blobFileName FROM Storage WHERE expireDate < ?1")
    Optional<List<String>>  findAllIdByExpireDate(LocalDate date);

    @Modifying
    @Query("DELETE FROM Storage WHERE blobFileName = ?1")
    void deleteByBlobName(String blobName);

    @Query("SELECT blobFileName FROM Storage WHERE uniqIdentificatorOfCreator = ?1")
    Optional<List<String>> findAllByIndificator(String uniqIdentificatorOfCreator);

    @Query("SELECT uniqIdentificatorOfCreator FROM Storage WHERE blobFileName = ?1")
    String findOwnerByBlobFileName(String fileName);

    @Query("SELECT expireDate FROM Storage WHERE blobFileName = ?1")
    LocalDate findExpireDateByBlobFileName(String blobFileName);
}
