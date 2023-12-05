/**
 * BlackJackGame created using the base code provided in Card.java, changes implemented in BlackjackCard.java that extends the blackjackcard.java class.
 *
 * Authors: Group 9 (Monika Skiba, Hazem Youssef, Aarushi Sharma)
 * Date: December 4th, 2023
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BlackJackGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        boolean playAgain = true;

        while (playAgain) {
            System.out.println("Welcome to Blackjack!");

            System.out.print("Enter the number of players (1-3): ");
            int numPlayers = getIntegerInput(scanner);
            scanner.nextLine(); // consume the newline character

            // Validate the number of players
            if (numPlayers < 1 || numPlayers > 3) {
                System.out.println("Invalid number of players. Exiting the game.");
                return;
            }

            List<Player> players = new ArrayList<>();
            for (int i = 1; i <= numPlayers; i++) {
                System.out.print("Enter the name for Player " + i + ": ");
                String playerName = scanner.nextLine();

                players.add(new Player(playerName));
            }

            List<BlackjackCard> deck = createDeck();
            List<BlackjackCard> dealerHand = new ArrayList<>();

            for (Player player : players) {
                player.getHand().add(drawCard(deck, random));
                player.getHand().add(drawCard(deck, random));
            }

            dealerHand.add(drawCard(deck, random));
            dealerHand.add(drawCard(deck, random));

            int currentPlayerIndex = 0;

            while (true) {
                Player currentPlayer = players.get(currentPlayerIndex);
                System.out.println(currentPlayer.getName() + "'s turn:");
                System.out.println("Your hand: " + currentPlayer.getHand());
                System.out.println("Your score: " + calculateScore(currentPlayer.getHand()));

                if (calculateScore(currentPlayer.getHand()) > 21) {
                    System.out.println("Bust! " + currentPlayer.getName() + " loses!");
                    currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers; // move to the next player
                    if (currentPlayerIndex == 0) {
                        break; // exit the loop if all players have played
                    }
                } else {
                    System.out.print("Do you want to Hit (H) or stand (S)? ");
                    String choice = scanner.nextLine();

                    if (choice.equalsIgnoreCase("H")) {
                        currentPlayer.getHand().add(drawCard(deck, random));
                    } else if (choice.equalsIgnoreCase("S")) {
                        currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers; // move to the next player
                        if (currentPlayerIndex == 0) {
                            break; // exit the loop if all players have played
                        }
                    }
                }
            }

            // Dealer's turn
            while (calculateScore(dealerHand) < 17) {
                dealerHand.add(drawCard(deck, random));
            }

            System.out.println("Dealer's hand: " + dealerHand);
            System.out.println("Dealer's score: " + calculateScore(dealerHand));

            // Determine the winner
            int dealerScore = calculateScore(dealerHand);

            for (Player player : players) {
                String playerName = player.getName();
                int playerScore = calculateScore(player.getHand());

                if (playerScore > 21 || (dealerScore <= 21 && dealerScore >= playerScore)) {
                    System.out.println(playerName + " loses!");
                } else {
                    System.out.println(playerName + " wins!");
                }
            }

            System.out.print("Play again? (Y/N): ");
            String playAgainChoice = scanner.nextLine();
            playAgain = playAgainChoice.equalsIgnoreCase("Y");
        }

        scanner.close();
    }

    public static int getIntegerInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a valid number: ");
            scanner.next(); // consume the invalid input
        }
        return scanner.nextInt();
    }

    public static List<BlackjackCard> createDeck() {
        List<BlackjackCard> deck = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits) {
            for (String value : values) {
                int cardValue = value.equals("Ace") ? 11 : (value.equals("Jack") || value.equals("Queen") || value.equals("King")) ? 10 : Integer.parseInt(value);
                deck.add(new BlackjackCard(suit, value, cardValue));
            }
        }

        return deck;
    }

    public static BlackjackCard drawCard(List<BlackjackCard> deck, Random random) {
        int index = random.nextInt(deck.size());
        BlackjackCard card = deck.get(index);
        deck.remove(index);
        return card;
    }

    public static int calculateScore(List<BlackjackCard> hand) {
        int score = 0;
        int numAces = 0;

        for (BlackjackCard card : hand) {
            int cardValue = card.getValue();
            if (cardValue == 1) {
                score += 11;
                numAces++;
            } else {
                score += cardValue;
            }
        }

        // Handle Aces
        while (score > 21 && numAces > 0) {
            score -= 10;
            numAces--;
        }

        return score;
    }

    public static class Player {
        private String name;
        private ArrayList<BlackjackCard> hand;

        public Player(String name) {
            this.name = name;
            this.hand = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public ArrayList<BlackjackCard> getHand() {
            return hand;
        }
    }

    public static class BlackjackCard {
        private String suit;
        private String value;
        private int cardValue;

        public BlackjackCard(String suit, String value, int cardValue) {
            this.suit = suit;
            this.value = value;
            this.cardValue = cardValue;
        }

        public int getValue() {
            return cardValue;
        }

        @Override
        public String toString() {
            return value + " of " + suit;
        }
    }
}
