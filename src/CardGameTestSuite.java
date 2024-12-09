import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({PlayerTest.class,
        CardTest.class, DeckTest.class,
        LoggerTest.class, CardGameTest.class})
public class CardGameTestSuite {

}
