package inf112.skeleton.app;//Created by ingridjohansen on 04/02/2019.

public class Map {

    IBoardObject[][] map;

    public Map(int x, int y){
        map = new IBoardObject[x][y];
    }

    int getX(){
        return map.length;
    }

    int getY(){
        return map[0].length;
    }

    void add(IBoardObject c, int x, int y){
        map[x][y] = c;
    }

    IBoardObject getBoardObject(int x, int y){
        return map[x][y];
    }

    boolean isInstance(IBoardObject c, int x, int y){
        return map[x][y] == c;
    }

    public IBoardObject[][] getMap() {
        return map;
    }
}
