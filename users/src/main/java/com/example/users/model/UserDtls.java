package com.example.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtls {

	private String username;
	private String password;
	private String role;
	private String email;
	private String mobile;
	private String confirmed;
}
