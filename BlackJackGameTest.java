import java.util.List;
import java.util.Random;

public class BlackjackGame {

    public static int getIntegerInput(Scanner scanner) {
        // Placeholder implementation for demonstration purposes
        return scanner.nextInt();
    }

    public static List<BlackjackCard> createDeck() {
        // Placeholder implementation for demonstration purposes
        return null;
    }

    public static BlackjackCard drawCard(List<BlackjackCard> deck, Random random) {
        // Placeholder implementation for demonstration purposes
        return null;
    }

    public static int calculateScore(List<BlackjackCard> hand) {
        // Placeholder implementation for demonstration purposes
        return 0;
    }

    public static class Player {
        private String name;
        private List<BlackjackCard> hand;

        public Player(String name) {
            this.name = name;
            this.hand = new ArrayList<>(); // Assuming you have an implementation of BlackjackCard
        }

        public String getName() {
            return name;
        }

        public List<BlackjackCard> getHand() {
            return hand;
        }
    }
}