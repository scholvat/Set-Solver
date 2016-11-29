package uwaterloo.setgame.util;

import uwaterloo.setgame.util.cardfeatures.Color;
import uwaterloo.setgame.util.cardfeatures.Fill;
import uwaterloo.setgame.util.cardfeatures.Shape;

/**
 * Created by Stephen on 11/28/2016.
 */

public class Card {
    private Shape shape;
    private Color color;
    private Fill fill;
    private int number;

    public Card(Shape shape, Color color, Fill fill, int number){
        this.shape=shape;
        this.color=color;
        this.fill=fill;
        this.number=number;
    }


    //getters and setters, and toStrings
    @Override
    public String toString() {
        return "Card: " + Integer.toString(number) + " " + fill + " " +color + " " + shape;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Fill getFill() {
        return fill;
    }

    public void setFill(Fill fill) {
        this.fill = fill;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
