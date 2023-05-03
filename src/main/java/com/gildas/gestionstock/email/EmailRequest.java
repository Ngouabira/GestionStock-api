package com.gildas.gestionstock.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailRequest {

    private String to;
    private String subject;
    private String body;

}
