package med.voll.api.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import med.voll.api.domain.user.User;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
		
	public String generateToken (User user) {
		try {
		    var algorithm = Algorithm.HMAC256(secret);
		    return JWT.create()
		    	.withSubject(user.getLogin())
		        .withIssuer("API Vollmed")
		        .withExpiresAt(dataExpires())
		        .sign(algorithm);
		} catch (JWTCreationException exception){
		    throw new RuntimeException("Error token", exception);
		}
	}
	
	public String getSubject(String tokenJWT) {
		try {
			var algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("API Vollmed")
					.build()
					.verify(tokenJWT)
					.getSubject();
			
		} catch (Exception e) {
			throw new RuntimeException("Token invalid or expired");
		}
	}

	private Instant dataExpires() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
}
