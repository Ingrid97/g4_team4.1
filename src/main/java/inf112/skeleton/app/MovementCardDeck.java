package inf112.skeleton.app;

import java.util.ArrayList;
import java.util.Collections;

public class MovementCardDeck {

    //The full deck of all movement cards
    public static ArrayList<MovementCard> fullDeck;

    /**
     * Making the movement card deck
     */
    public static void setUpTheFullDeckOfCards() {

        fullDeck = new ArrayList<>();

        // Rotate cards
        // Left rotation, right rotation, forward movement 1
        int priorityForLeft = 80;
        int priorityForRight = 70;
        int priorityForMovementForward = 490;
        for (int i = 0; i < 18; i++) {
            fullDeck.add(new MovementCard(Directions.LEFT, 0, priorityForLeft));
            fullDeck.add(new MovementCard(Directions.RIGHT, 0, priorityForRight));
            fullDeck.add(new MovementCard(Directions.NODIRECTION, 1, priorityForMovementForward));
            priorityForLeft += 20;
            priorityForRight += 20;
            priorityForMovementForward += 20;
        }

        // Movement forward 2
        priorityForMovementForward = 670;
        for (int i = 0; i < 12; i++) {
            fullDeck.add(new MovementCard(Directions.NODIRECTION, 2, priorityForMovementForward));
            priorityForMovementForward += 20;
        }

        // Movement forward 3, movement backwards 1 & 180 turn
        priorityForMovementForward = 790;
        int priorityForMovementBackwards = 430;
        int priorityFor180Turn = 10;
        for (int i = 0; i < 6; i++) {
            fullDeck.add(new MovementCard(Directions.NODIRECTION, 3, priorityForMovementForward));
            fullDeck.add(new MovementCard(Directions.DOWN, 0, priorityFor180Turn));
            fullDeck.add(new MovementCard(Directions.DOWN, 1, priorityForMovementBackwards));
            priorityForMovementForward += 10;
            priorityFor180Turn += 10;
            priorityForMovementBackwards += 10;
        }
    }

    /**
     * Dealing out random movement cards to players
     *
     * @param players list of player to deal cards to
     */
    public static void dealOutMovementCards(ArrayList<Player> players) {
        ArrayList<MovementCard> copy = new ArrayList<>(fullDeck);
        Collections.shuffle(copy);

        for (int i = 0; i < players.size(); i++) {

            for (int j = 0; j < players.get(i).memoryCapacityForThisPlayer(); j++) {
                players.get(i).giveMovementCards(copy.get(j));
                copy.remove(j);
            }
        }
    }
}
