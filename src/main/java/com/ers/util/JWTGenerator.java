package com.ers.util;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ers.models.User;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Generator class used to generate a JSON Web Token for a user.
 * 
 * @author Jose Rivera
 *
 */
public class JWTGenerator {

	private static final Logger LOG = Logger.getLogger(JWTGenerator.class);

	/**
	 * Private constructor so the class cannot be instantiated.
	 */
	private JWTGenerator() {

	}

	/**
	 * Method to create a JWT for a user ( subject ). Creates the JWT with the
	 * signature algorithm and expiration date.
	 * 
	 * @param subject The user that the JWT will be assigned to.
	 * @return Built JWT and serialized into a compact, URL-safe string
	 */
	public static String createJWT(User subject) {

		LOG.info("Creating new JWT for: " + subject.getUsername());

		// The JWT Signature Algorithm used to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		long nowMillis = System.currentTimeMillis();

		// Configure the JWT and set its claims
		JwtBuilder builder = Jwts.builder().setId(Integer.toString(subject.getId())).setSubject(subject.getUsername())
				.setIssuer("ADMIN").claim("role", subject.getRole().getRole()).claim("password", subject.getPassword())
				.setExpiration(new Date(nowMillis + JWTConfig.EXPIRATION * 1000))
				.signWith(signatureAlgorithm, JWTConfig.signingKey);

		LOG.info("JWT successfully created");

		// Build the JWT and serialize it into a compact, URL-safe string
		return builder.compact();
	}

}
