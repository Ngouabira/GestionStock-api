package com.gildas.gestionstock.auth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String etat;
	private List<String> roles;
	public JwtResponse(String accessToken, String etat,  List<String> roles) {
		this.token = accessToken;
		this.etat = etat;
		this.roles = roles;
	}

}
