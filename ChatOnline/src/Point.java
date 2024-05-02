import java.util.Arrays;

public class Point {
    private double[] coordinates;
    private int group;
    private String pointClass;

    public Point(double[] coordinates, String pointClass) {
        this.coordinates = coordinates;
        this.pointClass=pointClass;
    }

    public String getPointClass() {
        return pointClass;
    }


    public void setPointClass(String pointClass) {
        this.pointClass = pointClass;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Points{" +
                "coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
