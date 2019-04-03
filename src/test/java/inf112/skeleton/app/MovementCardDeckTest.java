package inf112.skeleton.app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MovementCardDeckTest {
    @Test
    public void numberOfCardsInFullDeck() {
        MovementCardDeck.setUpTheFullDeckOfCards();
        assertEquals(MovementCardDeck.theFullDeckOfAllMovementCards.size(), 84);
    }
}
