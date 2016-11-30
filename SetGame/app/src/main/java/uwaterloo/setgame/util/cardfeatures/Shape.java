package uwaterloo.setgame.util.cardfeatures;

public class Shape extends Feature {
    public static final int DIAMOND = 0;
    public static final int OVAL = 1;
    public static final int SQUIGGLE = 2;

    public Shape(int ID) {
        super(ID,new String[]{"Diamond", "Oval", "Squiggle"});
    }
}

