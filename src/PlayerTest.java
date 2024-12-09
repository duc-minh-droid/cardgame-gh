import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {
    private Deck deck;
    private CardGame game;
    private Player player;
    private static final String OUTPUT_DIR = "gameOutput";

    @Before
    public void setUp() {
        deck = new Deck(1);
        for (int i = 1; i <= 5; i++) {
            deck.addCard(new Card(i));
        }

        game = new CardGame();
        game.decks.add(deck);

        player = new Player(1, deck, game);
    }

    @Test
    public void testAddCard() {
        Card newCard = new Card(10);
        player.addCard(newCard);
        List<Card> hand = player.getHand();
        assertEquals(1, hand.size());
        assertEquals(10, hand.get(0).getValue());
    }

    @Test
    public void testCheckWinningHand_True() {
        // Set up a winning hand
        player.addCard(new Card(3));
        player.addCard(new Card(3));
        player.addCard(new Card(3));
        player.addCard(new Card(3));

        assertTrue(player.checkWinningHand());
    }

    @Test
    public void testPlayTurn() {
        Deck deck2 = new Deck(2);
        Player player2 = new Player(2, deck2, game);
        
        game.players = new ArrayList<>();
        game.players.add(player);
        game.players.add(player2);

        // Prepare the source deck
        deck.addCard(new Card(6));

        // Initialise player's hand
        player.addCard(new Card(3));
        player.addCard(new Card(3));
        player.addCard(new Card(4));
        player.addCard(new Card(3));

        player.playTurn();

        // Validate player's hand has the correct number of cards after drawing and discarding
        assertEquals(4, player.getHand().size());

        // Validate the next player's deck has received the discarded card
        assertEquals(1, deck2.size());
    }

    @Test
    public void testRun() throws IOException {
        Deck deck2 = new Deck(2);
        Player player2 = new Player(2, deck2, game);
        
        game.decks.clear();
        game.decks.add(deck);
        game.decks.add(deck2);
        
        // Set up a winning hand for the player
        player.getHand().clear();
        player.getHand().add(new Card(10));
        player.getHand().add(new Card(10));
        player.getHand().add(new Card(10));
        player.getHand().add(new Card(10));

        game.players.clear();
        game.players.add(player);
        game.players.add(player2);

        // Prepare decks with some cards to prevent immediate emptiness
        deck.addCard(new Card(5));
        deck2.addCard(new Card(5));

        // Prepare game state
        game.winningPlayer.set(0);

        // Start the player's thread
        player.start();

        // Wait for the thread to finish
        try {
            player.join(1000); // Add a timeout to prevent hanging
        } catch (InterruptedException e) {
            fail("Thread was interrupted");
        }

        // Verify that the player wins
        assertEquals(1, game.winningPlayer.get());

        // Verify log files
        File winnerLogFile = new File(OUTPUT_DIR, "player1_output.txt");
        assertTrue("Winner log file should exist", winnerLogFile.exists());
        
        // Read log file content
        BufferedReader reader = new BufferedReader(new FileReader(winnerLogFile));
        StringBuilder logContent = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            logContent.append(line);
        }
        reader.close();
        
        String log = logContent.toString();
        assertTrue("Log should contain 'wins'", log.contains("wins"));
        assertTrue("Log should contain 'exits'", log.contains("exits"));
        assertTrue("Log should contain final hand", log.contains("final hand"));
    }
}