package com.company;

import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(initializers = TestcontainersPostgresInitializer.Initializer.class)
public class TestcontainersPostgresInitializer {
    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext ctx) {
            EnvironmentTestUtils.addEnvironment("testContainers", ctx.getEnvironment(),
                    "spring.datasource.driverClassName=org.testcontainers.jdbc.ContainerDatabaseDriver",
                    "spring.datasource.url=jdbc:tc:postgresql:///rand"
            );
        }
    }
}
