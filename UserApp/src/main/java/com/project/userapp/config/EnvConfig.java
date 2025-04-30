package com.project.userapp.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.*;

public class EnvConfig implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        Map<String, Object> dotenvProperties = new HashMap<>();
        dotenvProperties.put("aws.accessKey", dotenv.get("AWS_ACCESS_KEY_ID"));
        dotenvProperties.put("aws.secretKey", dotenv.get("AWS_SECRET_ACCESS_KEY"));
        dotenvProperties.put("aws.region", dotenv.get("AWS_REGION"));
        dotenvProperties.put("aws.bucket", dotenv.get("AWS_S3_BUCKET_NAME"));

        environment.getPropertySources().addFirst(new MapPropertySource("dotenv", dotenvProperties));
    }
}
