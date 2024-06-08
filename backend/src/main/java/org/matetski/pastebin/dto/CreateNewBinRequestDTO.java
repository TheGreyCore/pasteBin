package org.matetski.pastebin.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateNewBinRequestDTO {
    private String body;
    private Integer expireTimeInDays;

    public boolean isValid(){
        return body != null && expireTimeInDays != null;
    }

    @Override
    public String toString() {
        return "RequestTemplate{" +
                "body='" + body + '\'' +
                ", expireTime=" + expireTimeInDays +
                '}';
    }
}
