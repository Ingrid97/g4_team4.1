package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

public class Map {

    private IBoardObject[][] map;

    public Map(int x, int y){
        if (x > 0) {
            if (y > 0) {
                map = new IBoardObject[x][y];
                return;
            }
        }
        throw new IllegalArgumentException("the x or y value of the map cannot be less than zero");
    }

    int getX(){
        return map.length;
    }

    int getY(){
        return map[0].length;
    }

    void add(IBoardObject c, int x, int y){
        if (x >= 0 && y >= 0) {
            if (x < getX() && y < getY()) {
                map[x][y] = c;
                return;
            }
        }
        throw new IllegalArgumentException("Cannot place object with these values: x= " + x +" y= "+y) ;
    }

    IBoardObject getBoardObject(int x, int y){
        return map[x][y];
    }

    public IBoardObject[][] getMap() {
        IBoardObject[][] copyOfMap = new IBoardObject[getX()][getY()];
        for (int x = 0; x < getX(); x++) {
            for (int y = 0; y < getY(); y++) {
                copyOfMap[x][y] = map[x][y];
            }
        }
        return map;
    }

    public boolean isEmpty(int x, int y) {
        return map[x][y] == null;
    }
}
