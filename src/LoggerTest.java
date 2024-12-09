import org.junit.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import static org.junit.Assert.*;

public class LoggerTest {

    private static final String TEST_DIR = "testLogs";
    private static final String TEST_FILE = "test_log.txt";
    private Logger logger;

    private void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) { // Check if the directory contains any files
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file); // Recursively delete subdirectories
                    } else {
                        file.delete(); // Delete files
                    }
                }
            }
            directory.delete(); // Delete the now-empty directory
        }
    }    

    @Before
    public void setUp() {
        logger = new Logger(TEST_DIR, TEST_FILE, true); // Clear the log file on initialization
    }

    @After
    public void tearDown() {
        // Clean up all test files after each test
        deleteDirectory(new File("gameOutput")); 
        deleteDirectory(new File(TEST_DIR));
    }

    @Test
    public void testLog() throws IOException {
        String message = "drew a card";
        int playerId = 1;
        logger.log(message, playerId);

        
        Path logPath = Paths.get(TEST_DIR, TEST_FILE);
        assertTrue(Files.exists(logPath));
        String content = Files.readString(logPath);
        assertEquals("player 1 drew a card" + System.lineSeparator(), content);
    }

    @Test
    public void testLogDeckContents() throws IOException {
        // Log deck contents
        logger.logDeckContents(1, Arrays.asList(new Card(3), new Card(7), new Card(5)));

        // Verify the deck file content
        Path deckPath = Paths.get("gameOutput", "deck1_output.txt");
        assertTrue(Files.exists(deckPath));
        String content = Files.readString(deckPath);
        assertEquals("deck1 contents: 3 7 5", content);
    }
}
