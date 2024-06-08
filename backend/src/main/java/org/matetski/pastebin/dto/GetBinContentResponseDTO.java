package org.matetski.pastebin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetBinContentResponseDTO{
    private String body;
    private LocalDate expiryDate;
}
