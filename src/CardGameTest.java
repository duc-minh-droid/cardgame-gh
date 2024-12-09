import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CardGameTest {
    private static final String FILE_PATH = "src/four.txt";

    @Test 
    public void testReadPack(){
        List<Card> pack = CardGame.readPack(FILE_PATH, 4);

        assertNotNull(pack);
        assertEquals(32,pack.size());
    }

    @Test
    public void testDistributeCards(){
        List<Player> players = new ArrayList<>();
        List<Deck> decks = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            players.add(new Player(i, new Deck(i), null));
            decks.add(new Deck(i)); 
        }
        List<Card> pack = CardGame.readPack(FILE_PATH, 4);

        CardGame.distributeCards(players, decks, pack);
 
        for(Player player : players){
            assertEquals(4, player.getHand().size());
        }
        for(Deck deck : decks){
            assertEquals(4, deck.size());
        }
    }

    @Test
    public void testGetNumberOfPlayers() {
        String simulatedInput = "4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        int numberOfPlayers = CardGame.getNumberOfPlayers();
        assertEquals("Number of players should match user input.", 4, numberOfPlayers);
    }

    @Test
    public void testGetPackLocation() {
        String simulatedInput = FILE_PATH + "\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        String packLocation = CardGame.getPackLocation();
        assertEquals("Pack location should match the file path.", FILE_PATH, packLocation);
    }

    @Test
    public void testInitializeGame() {
        CardGame game = new CardGame();
        game.initializeGame(4,"src/four.txt");

        assertEquals("Number of players should be correct.", 4, game.players.size());

        for (Deck deck : game.decks) {
            assertEquals("Each deck should initially have the correct number of cards.", 4, deck.size());
        }
    }

    @Test
    public void testStartGame() throws Exception {
        CardGame game = new CardGame();
        game.startGame();

        for (Player player : game.players) {
            assertTrue("Player thread should be running or runnable.",
                    player.getState() == Thread.State.RUNNABLE || player.getState() == Thread.State.TIMED_WAITING);
        }
        game.endGame();
    }
}
