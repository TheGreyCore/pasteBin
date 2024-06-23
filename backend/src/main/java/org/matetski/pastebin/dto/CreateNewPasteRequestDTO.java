package org.matetski.pastebin.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateNewPasteRequestDTO {
    private String body;
    private Integer expireTimeInDays;

    @Override
    public String toString() {
        return "RequestTemplate{" +
                "body='" + body + '\'' +
                ", expireTime=" + expireTimeInDays +
                '}';
    }
}
