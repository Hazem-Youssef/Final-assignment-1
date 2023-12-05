import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BlackJackGameTest {

    @Test
    public void testDrawCard() {
        // Create a deck with known cards
        List<BlackjackCard> deck = List.of(
                new BlackjackCard("Hearts", "2", 2),
                new BlackjackCard("Diamonds", "7", 7),
                new BlackjackCard("Clubs", "Ace", 11)
        );

        // Mock a Random object to return a specific index
        Random mockRandom = Mockito.mock(Random.class);
        Mockito.when(mockRandom.nextInt(Mockito.anyInt())).thenReturn(1);

        // Draw a card and check if it's the expected card
        BlackjackCard drawnCard = BlackJackGame.drawCard(deck, mockRandom);
        assertEquals("Diamonds", drawnCard.getSuit());
        assertEquals("7", drawnCard.getValue());
        assertEquals(7, drawnCard.getValue());
    }

    @Test
    public void testGetIntegerInputInvalidInput() {
        // Create a ByteArrayInputStream to simulate user input
        ByteArrayInputStream inputStream = new ByteArrayInputStream("invalid\n5".getBytes());
        System.setIn(inputStream);

        // Create a new scanner using the mocked input stream
        Scanner scanner = new Scanner(System.in);

        // Test the getIntegerInput method
        int result = BlackJackGame.getIntegerInput(scanner);

        // Ensure that the method handled invalid input and returned the valid input
        assertEquals(5, result);
        
        // Clean up: restore the original input stream
        System.setIn(System.in);
    }

    @Test
    public void testCalculateScore() {
        // Test case 1: Test calculating the score with Aces as 11
        BlackjackCard card1 = new BlackjackCard("Hearts", "Ace", 11);
        BlackjackCard card2 = new BlackjackCard("Diamonds", "8", 8);
        assertEquals(19, BlackJackGame.calculateScore(List.of(card1, card2)));

        // Test case 2: Test calculating the score with Aces as 1
        BlackjackCard card3 = new BlackjackCard("Clubs", "Ace", 11);
        BlackjackCard card4 = new BlackjackCard("Spades", "King", 10);
        assertEquals(21, BlackJackGame.calculateScore(List.of(card3, card4)));
    }

    @Test
    public void testPlayerClass() {
        // Create a new player
        BlackJackGame.Player player = new BlackJackGame.Player("John");

        // Ensure that the player's name is set correctly
        assertEquals("John", player.getName());

        // Ensure that the player's hand is initially empty
        assertTrue(player.getHand().isEmpty());
    }
}

