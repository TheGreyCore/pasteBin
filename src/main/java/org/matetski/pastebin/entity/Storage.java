package org.matetski.pastebin.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Table
public class Storage {
    @Id
    private String blobFileName;
    private LocalDate expireDate;
    private String uniqIdentificatorOfCreator;

    public Storage() {}
    @Override
    public String toString() {
        return "Storage{" +
                ", blobFileName='" + blobFileName + '\'' +
                ", expireDate=" + expireDate +
                ", uniqIdentificator='" + uniqIdentificatorOfCreator + '\'' +
                '}';
    }
}

