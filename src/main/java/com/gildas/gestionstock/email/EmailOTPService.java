package com.gildas.gestionstock.email;

import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class EmailOTPService {

    public String generateOTP() {
        return new DecimalFormat("000000").format(Math.random() * 999999);
    }

}
