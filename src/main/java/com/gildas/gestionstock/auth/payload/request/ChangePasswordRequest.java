package com.gildas.gestionstock.auth.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
	
	private int id;
	private String oldPassword;
	private String newPassword;
	public int getId() {
		return id;
	}

	public ChangePasswordRequest(String oldPassword, String newPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	

}
