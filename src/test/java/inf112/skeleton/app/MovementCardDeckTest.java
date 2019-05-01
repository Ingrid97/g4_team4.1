package inf112.skeleton.app;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MovementCardDeckTest {
    @Test
    public void numberOfCardsInFullDeck() {
        MovementCardDeck.setUpTheFullDeckOfCards();
        assertEquals(MovementCardDeck.fullDeck.size(), 84);
    }

    @Test
    public void numberOfDeltCard() {
        MovementCardDeck.setUpTheFullDeckOfCards();
        ArrayList<Player> arr = new ArrayList<Player>();
        for (int i = 0; i < 3; i++) {
            arr.add(new Player(0, new Robot(5, 5, Directions.UP)));
        }
        MovementCardDeck.dealOutMovementCards(arr);
        for (int i = 0; i < 3; i++) {
            assertEquals(9, arr.get(i).theCardsToChooseYourProgramFrom.size());
        }
    }
}
