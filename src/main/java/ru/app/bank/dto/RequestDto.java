package ru.app.bank.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties
public class RequestDto {
    private String name;
    private String data;
    private Long number;
    private Integer operation;

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", data='" + data + '\'' +
                ", number=" + number +
                ", operation=" + operation;
    }
}
