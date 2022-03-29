package com.victorgvc.multilanguagespring.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.victorgvc.multilanguagespring.model.User;

public class Validations {
    
    public static void notExists(Object obj, String msg) throws RuntimeException{

        if(obj == null)
            throw new RuntimeException(msg);
        if(obj instanceof String && ((String)obj).isEmpty())
            throw new RuntimeException(msg);
    }

    public static void notEquals(Object obj1, Object obj2, String msg) throws RuntimeException{

        if(obj1 instanceof String && !((String)obj1).equals(obj2))
            throw new RuntimeException(msg);
        if(!obj1.equals(obj2))
            throw new RuntimeException(msg);
    }

    public static void exists(Object obj, String msg) throws Exception{

        if(obj != null)
            throw new RuntimeException(msg);
    }

    public static void notOwner(User user, String authorizationHeader, String msg) throws Exception{
        if(authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // TODO remember to use heroku secret
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            
            if(!user.getUsername().equals(decodedJWT.getSubject()))
                throw new RuntimeException(msg);
        } else {
            throw new RuntimeException("Invalid JWT token");
        }
    }
}
