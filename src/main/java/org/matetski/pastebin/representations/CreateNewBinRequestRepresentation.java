package org.matetski.pastebin.representations;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateNewBinRequestRepresentation {
    private String body;
    private String identificator;
    private Integer expireTimeInDays;

    public boolean isValid(){
        return body != null && identificator != null && expireTimeInDays != null;
    }

    @Override
    public String toString() {
        return "RequestTemplate{" +
                "body='" + body + '\'' +
                ", identificator='" + identificator + '\'' +
                ", expireTime=" + expireTimeInDays +
                '}';
    }
}
