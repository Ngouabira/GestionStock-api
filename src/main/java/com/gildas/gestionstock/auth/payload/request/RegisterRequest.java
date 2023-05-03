package com.gildas.gestionstock.auth.payload.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank
    @Size(min = 2, max = 100)
    private String nom;
    
    @NotBlank
    @Size(min = 2, max = 100)
    private String prenom;
    
    @NotBlank
    @Size(min = 3, max = 100)
    private String telephone;
    
    @NotBlank
    @Size(min = 2, max = 15)
    private String genre;
 
    @NotBlank
    @Size(min = 6)
    private String username;
    
    private String role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;


}
