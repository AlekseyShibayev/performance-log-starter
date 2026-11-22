package com.company.app.infrastructure.log.performance.test.config;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;


@Slf4j
public class TestContainers {

    private static final PostgreSQLContainer<?> postgreSQLContainer;
    private static final String PG_HOST = "postgres";
    private static final int PG_PORT = 5432;
    private static final String PG_USER = "pg";
    private static final String PG_PASS = "pg";
    private static final String PG_DB = "pg";

    static {
        // docker-java does not negotiate the API version, but Docker 23+ rejects requests with API < 1.41
        if (System.getProperty("api.version") == null) {
            System.setProperty("api.version", "1.43");
        }
        try {
            postgreSQLContainer = new PostgreSQLContainer<>(createDockerImage())
                    .withDatabaseName(PG_DB)
                    .withUsername(PG_USER)
                    .withPassword(PG_PASS)
                    .withReuse(true)
                    .withExposedPorts(PG_PORT)
                    .withNetworkAliases(PG_HOST)
                    .waitingFor(Wait.forListeningPort());
            postgreSQLContainer.start();
            postgreSQLContainer.followOutput(new Slf4jLogConsumer(log));

        } catch (Exception e) {
            var msg = "Error starting test containers";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    private static DockerImageName createDockerImage() {
        return DockerImageName.parse("postgres:16")
                .asCompatibleSubstituteFor("postgres");
    }

    public static PostgreSQLContainer<?> getPostgresContainer() {
        return postgreSQLContainer;
    }

}
