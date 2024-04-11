package org.matetski.pastebin.representations;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetBinContentResponseRepresentation {
    private String body;
    private LocalDate expiryDate;
}
