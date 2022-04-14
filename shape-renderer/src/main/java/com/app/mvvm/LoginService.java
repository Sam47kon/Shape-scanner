package com.app.mvvm;

import com.app.HttpConnectHelper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;

public class LoginService {

	public Boolean authorize(String name, String password) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("name", name);
		params.add("password", password);
		try {
			ResponseEntity<String> responseLogin = HttpConnectHelper.getResponse(HttpMethod.GET, "login/", null, params, null, String.class);
			return responseLogin.getStatusCode() == HttpStatus.OK;
		} catch (HttpStatusCodeException e) {
			return false;
		}
	}
}
