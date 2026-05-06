package com.example.pharmacy.util;

import com.example.pharmacy.exception.PharmacyException;
import com.example.pharmacy.helper.ApplicationProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class GenericResponse<T> {
    private boolean success;
    private String message;
    private String devMsg;
    private String errorStack;
    private String responsecode;
    private T data;

    public GenericResponse(boolean success, String message, String responsecode, T data) {
        this.success = success;
        this.message = message;
        this.responsecode = responsecode;
        this.data = data;
    }



    public static <T> GenericResponse<T> success(T data) {
        return GenericResponse.<T>builder()
                .message("SUCCESS!")
                .responsecode("200")
                .data(data)
                .success(true)
                .build();
    }

    public static <T> GenericResponse<T> failed(String msg) {
        return GenericResponse.<T>builder()
                .message(msg)
                .responsecode("404")
                .success(false)
                .build();
    }

    public static <T> GenericResponse<T> defaultServerError(Exception e) {
        String errorStack = getStackTraceAsString(e);
        GenericResponse<T> response = new GenericResponse<>(false, "Server Side Error", "500", null);
        if (ApplicationProperties.getShowErrors()) {
            response.setDevMsg(e.getMessage());
            response.setErrorStack(errorStack);
        }
        return response;
    }

    public static <T> GenericResponse<T> defaultPharmacyException(Exception e) {
        if (e instanceof PharmacyException) {
            return failed(e.getMessage());
        }
        return defaultServerError(e);
    }
    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);

        return stringWriter.toString();
    }
    public static <T> GenericResponse<T> notFound(String msg) {
        return GenericResponse.<T>builder()
                .message(msg)
                .responsecode("404")
                .success(false)
                .build();
    }

    public static <T> GenericResponse<T> alreadyExists(T data, String msg) {
        return GenericResponse.<T>builder()
                .message(msg)
                .responsecode("403")
                .success(false)
                .data(data)
                .build();
    }
}