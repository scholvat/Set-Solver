package uwaterloo.setgame.util.cardfeatures;


public class Feature {

    protected String[] NAME = {"","",""};
    protected int ID;

    public Feature(int ID, String[] name) {
        this.ID=ID;
        NAME = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int shapeID) {
        this.ID = shapeID;
    }
    public String getName(){
        return NAME[ID];
    }
    public boolean setName(String name){
        if(name.compareTo(NAME[0])==0){
            ID=0;
            return true;
        }
        else if(name.compareTo(NAME[1])==0){
            ID=1;
            return true;
        }
        else if(name.compareTo(NAME[2])==0){
            ID=2;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getName();
    }
}
