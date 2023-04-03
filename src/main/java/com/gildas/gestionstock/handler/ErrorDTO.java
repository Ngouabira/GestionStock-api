package com.gildas.gestionstock.handler;

import com.gildas.gestionstock.exception.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDTO {

    private Integer httpCode;
    private ErrorCodes code;

    private String message;

    private List<String> errors = new ArrayList<>();

}
