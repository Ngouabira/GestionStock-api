package com.gildas.gestionstock.auth.response;

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
	private String status;
	private List<String> roles;
	public JwtResponse(String accessToken, String status,  List<String> roles) {
		this.token = accessToken;
		this.status = status;
		this.roles = roles;
	}

}
