package inf112.skeleton.app;

public class Main {
    public static void main(String[] args) {
        RoboRally roboRally = new RoboRally("RiskyExchange.txt");
        roboRally.playGame();
        System.out.println("The game is over");

        //TODO: finne en måte å avskutte spillet på!
        //dette krasjer spillet men ellers blir det aldri avsluttet!
        System.exit(0);
    }
}




