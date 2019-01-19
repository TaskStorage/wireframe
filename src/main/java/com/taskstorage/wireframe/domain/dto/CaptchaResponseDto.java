package com.taskstorage.wireframe.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

//Игнорируем неизвестные поля
@JsonIgnoreProperties(ignoreUnknown = true)
//Гугл вощвращает success и error-codes
public class CaptchaResponseDto {
    private boolean success;

    //Переопределение, java не поддерживает переменные с дефисом
    @JsonAlias("error-codes")
    private Set<String> errorCodes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Set<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(Set<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}