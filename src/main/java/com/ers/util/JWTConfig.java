package com.ers.util;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JSON Web Token ( JWT ) config class used to set up the variables needed to
 * generate a JWT for a user.
 * 
 * @author Jose Rivera
 *
 */
public class JWTConfig {

	public static final String URI = "/**";
	public static final String HEADER = "Authorization";
	public static final String PREFIX = "Bearer ";
	public static final int EXPIRATION = 24 * 60 * 60;
	public static final String SECRET = "JwtSecretKey";
	public static final Key signingKey;

	/**
	 * Static block to create a signing key using the JWT secret key.
	 */
	static {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] secretBytes = DatatypeConverter.parseBase64Binary(SECRET);
		signingKey = new SecretKeySpec(secretBytes, signatureAlgorithm.getJcaName());
	}

	/**
	 * Private constructor so class cannot be instantiated.
	 */
	private JWTConfig() {
	}
}
