package org.matetski.pastebin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class GetPasteContentResponseDTO {
    private String body;
    private LocalDate expiryDate;
}
