package uwaterloo.setgame.util.cardfeatures.shapes;

/**
 * Created by Stephen on 11/28/2016.
 */


public class Shape {
    public static final int DIAMOND = 0;
    public static final int OVAL = 1;
    public static final int SQUIGGLE = 2;

    private int shapeID;

    public Shape(int shapeID) {
        this.shapeID=shapeID;
    }

    public int getShapeID() {
        return shapeID;
    }

    public void setShapeID(int shapeID) {
        this.shapeID = shapeID;
    }

}

