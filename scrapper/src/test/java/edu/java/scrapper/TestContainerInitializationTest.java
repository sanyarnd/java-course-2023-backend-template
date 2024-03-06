package edu.java.scrapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestContainerInitializationTest extends IntegrationTest {
    Connection dbConnection = DriverManager.getConnection(
        POSTGRES.getJdbcUrl(),
        POSTGRES.getUsername(),
        POSTGRES.getPassword()
    );

    public TestContainerInitializationTest() throws SQLException {
    }

    @Test
    public void isRunningTest() {
        Assertions.assertTrue(POSTGRES.isRunning());
    }

    @Test
    public void tablesExists() throws SQLException {
        var chats = dbConnection.getMetaData().getTables(null, null, "chats", null);
        var links = dbConnection.getMetaData().getTables(null, null, "links", null);
        var linksToChats = dbConnection.getMetaData().getTables(null, null, "links_to_chats", null);

        Assertions.assertTrue(chats.next());
        Assertions.assertTrue(links.next());
        Assertions.assertTrue(linksToChats.next());
    }

    @Test
    public void migrationWorked() throws SQLException {
        var changelog = dbConnection.getMetaData().getTables(null, null, "databasechangelog", null);
        var changeloglock = dbConnection.getMetaData().getTables(null, null, "databasechangeloglock", null);
        Assertions.assertTrue(changelog.next());
        Assertions.assertTrue(changeloglock.next());
    }
}
