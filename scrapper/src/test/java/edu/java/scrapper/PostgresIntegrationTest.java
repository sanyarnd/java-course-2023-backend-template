package edu.java.scrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Log4j2
@Testcontainers
public abstract class PostgresIntegrationTest {
    private static final String DOCKER_IMAGE_NAME = "postgres:16";
    private static final String DATABASE_NAME = "scrapper";
    private static final String DATABASE_USER_NAME = "postgres";
    private static final String DATABASE_USER_PASSWORD = "postgres";
    public PostgreSQLContainer<?> postgreSQLContainer;

    @SuppressWarnings("resource")
    public PostgresIntegrationTest() {
        postgreSQLContainer = new PostgreSQLContainer<>(DOCKER_IMAGE_NAME)
            .withDatabaseName(DATABASE_NAME)
            .withUsername(DATABASE_USER_NAME)
            .withPassword(DATABASE_USER_PASSWORD);
        postgreSQLContainer.start();
        runMigrations(postgreSQLContainer);
    }

    @SuppressWarnings("deprecation")
    private static void runMigrations(JdbcDatabaseContainer<?> jdbcDatabaseContainer) {
        Path changelogPath = new File(".").toPath().toAbsolutePath().resolve("../migrations/");

        try (Connection connection = DriverManager.getConnection(
            jdbcDatabaseContainer.getJdbcUrl(),
            jdbcDatabaseContainer.getUsername(),
            jdbcDatabaseContainer.getPassword()
        )
        ) {
            Database db = DatabaseFactory
                .getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("master.xml", new DirectoryResourceAccessor(changelogPath), db);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | LiquibaseException | FileNotFoundException e) {
            log.error(e);
        }
    }

    @DynamicPropertySource
    public void jdbcProperties(@NotNull DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
}
