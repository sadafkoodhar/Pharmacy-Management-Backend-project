package com.example.pharmacy.helper;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Getter
    private static Boolean showErrors = true;

    @Value("${app.show-errors:true}")
    private Boolean showErrorsValue;

    @PostConstruct
    public void init() {
        showErrors = this.showErrorsValue;
    }
}