import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deck {
    private final int id;
    private final List<Card> cards = new ArrayList();
    private Lock lock = new ReentrantLock();

    public Deck(int id) {
        this.id = id;
    }

    public int size() {
        return cards.size();
    }
    
    public Boolean isEmpty() {
        return cards.isEmpty();
    }

    public int getId() {
        return id;
    }
    
     /**
     * Adds given card to the bottom of the deck.
     * 
     * @param card to add to the deck
     */
    public void addCard(Card card) {
        try {
            lock.lock();
            cards.add(card);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes and returns the top card of the deck.
     * 
     * @return Card that is on top of the deck
     */
    public Card removeCard() {
        try {
            lock.lock();
            return cards.remove(0);
        } finally {
            lock.unlock();
        }

    }

    public void logDeckContents() {
        Logger logger = new Logger("gameOutput", "player" + id + "_output.txt", false);
        logger.logDeckContents(id, cards);
    }
}
