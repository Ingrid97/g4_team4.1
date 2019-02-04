package main.java.inf112.skeleton.app;

public class Position {
    /*This class is useful for assigning x and y positions in the map*/

        /*x-coordinate*/
        private int x;
        /*y-coordinate*/
        private int y;

        /*Constructer that object can correspond with*/
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
        /*Creat a copy of the this position and makes a copy*/

        public Position copy() {
            return new Position(x,y);
        }
        /*Gets the distance to another Position as the diference of X and Y*/
        public int distantTo(Position another){
            return Math.abs(this.x - another.x) + Math.abs(this.y - another.y);

}
        /* Gets X-coordiantes

         */
        public int getX(){
            return x;
        }
        public int getY(){
            return y; }
    /* if Y and X is bigger than zero make a move*/
    public boolean canGoHere(Directions dir){
        if(dir == Directions.RIGHT){
            return x > 0;
        }
        if(dir == Directions.DOWN){
            return y > 0;
        }
        return true;
}    /* Robot movement position*/
    public Position moveDirection(Directions dir){
        switch(dir){
            case RIGHT:
                return new Position(x + 1, y);
            case LEFT:
                return new Position(x -1, y);
            case UP:
                return new Position(x, y + 1);
            case DOWN:
                return new Position(x, y - 1);
            default:
                throw new IllegalArgumentException();




        }
    }

}