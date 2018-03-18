package org.pac4j.demo.spark;

import org.pac4j.core.config.Config;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;
import org.pac4j.sparkjava.SecurityFilter;

import java.util.HashMap;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.port;

public class SimpleJWTSecured {

    private final static String JWT_SALT = "12345678901234567890123456789012";

    public static void main(String[] args) {
        port(8081);
        final Config config = new JwtTokenConfigFactory(JWT_SALT).build();

        before("/secure", new SecurityFilter(config,"ParameterClient"));
        get("/secure", ((request, response) -> "Estamos Protegidos"));

        get("/", ((request, response) -> "Olá Mundo!"));
    }

    private void generateToken() {
        JwtGenerator generator = new JwtGenerator(new SecretSignatureConfiguration(JWT_SALT));
        String token = generator.generate(new HashMap<>());
    }
}
