public class Centroid {
    private double[] coordinates;
    private int groupID;

    public Centroid(double[] coordinates, int groupID) {
        this.coordinates = coordinates;
        this.groupID = groupID;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
}
