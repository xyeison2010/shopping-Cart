package com.example.leo.global;

import javax.servlet.http.HttpServletRequest;
//nos sirve para el mandar el parametro del token , FORGOT PASSWORD
public class Utility {

	public static String getSiteUrl(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}
}
