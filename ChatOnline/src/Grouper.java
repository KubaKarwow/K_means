import java.util.*;

public class Grouper {
    private int amountOfGroups;
    private List<Point> pointsToGroup;
    private List<Centroid> centroids;

    public Grouper(List<Point> training, int amountOfGroups) {
        this.pointsToGroup = training;
        this.amountOfGroups = amountOfGroups;
        this.centroids = new ArrayList<>();
    }

    public void group() {
        boolean isRunning = true;
        initializePoints();
        initializeCentroids();
        System.out.println("Iteration nr:1");
        showDistanceBetweenPointsAndCentroids();
        int iterationNumber=2;
        while (isRunning) {
            List<Point> before = pointsToGroup;
            assingPointsToClosestCentroids();
            repositionCentroids();
            System.out.println("Iteration nr:"+iterationNumber);
            iterationNumber++;
            showDistanceBetweenPointsAndCentroids();
            List<Point> after = pointsToGroup;
            if (checkIfDone(before, after)) {
                isRunning = false;
            }
        }
    }
    public void showGroupingResults(){
        for (int i = 0; i < centroids.size(); i++) {
            Map<String,Integer> classWithAmountOfOccurrences = new HashMap<>();
            for (Point point : pointsToGroup) {
                if(point.getGroup()==centroids.get(i).getGroupID()){
                    classWithAmountOfOccurrences.putIfAbsent(point.getPointClass(),0);
                    classWithAmountOfOccurrences.put(point.getPointClass(),
                            classWithAmountOfOccurrences.get(point.getPointClass())+1);
                }
            }
            System.out.println("Cluster nr:"+centroids.get(i).getGroupID() + " contains:");
            System.out.println(classWithAmountOfOccurrences);
            calculateEntropyForCluster(classWithAmountOfOccurrences);
        }
    }
    private void calculateEntropyForCluster(Map<String,Integer> occurrences){
        int count=0;
        Set<String> keys = occurrences.keySet();
        for (String key : keys) {
            count+=occurrences.get(key);
        }
        double result=0;
        for (String key : keys) {
            result+= (occurrences.get(key)*1.0)/count * (Math.log((occurrences.get(key)*1.0)/count)/Math.log(2));
        }
        result*=-1;
        System.out.println("Entropy:"+result);
    }
    private void showDistanceBetweenPointsAndCentroids(){
        for (int i = 0; i < centroids.size(); i++) {
            double distancesSum=0;
            for (Point point : pointsToGroup) {
                if(point.getGroup()==centroids.get(i).getGroupID()){
                    distancesSum+=getDistanceBetweenPoints(point.getCoordinates(),centroids.get(i).getCoordinates());
                }
            }
            System.out.println("Group number:"+centroids.get(i).getGroupID() + " distances sum is:"+distancesSum);
        }
    }

    private void repositionCentroids() {
        for (Centroid centroid : centroids) {
            setNewCoordinatesForCentroid(centroid, pointsToGroup);
        }
    }

    private boolean checkIfDone(List<Point> before, List<Point> after) {
        for (int i = 0; i < before.size(); i++) {
            if (before.get(i).getGroup() != after.get(i).getGroup()) {
                return false;
            }
        }
        return true;
    }

    private void assingPointsToClosestCentroids() {
        for (int i = 0; i < pointsToGroup.size(); i++) {
            int groupAssigned = 1;
            double minDistance =
                    getDistanceBetweenPoints(pointsToGroup.get(i).getCoordinates(),
                            centroids.get(0).getCoordinates());
            for (int j = 1; j < centroids.size(); j++) {
                double distance = getDistanceBetweenPoints(pointsToGroup.get(i).getCoordinates(),
                        centroids.get(j).getCoordinates());
                if (distance < minDistance) {
                    minDistance = distance;
                    groupAssigned = centroids.get(j).getGroupID();
                }
            }
            pointsToGroup.get(i).setGroup(groupAssigned);
        }
    }

    private double getDistanceBetweenPoints(double[] coordinatesFirst, double[] coordinatesSecond) {
        double sum = 0;
        for (int i = 0; i < coordinatesFirst.length; i++) {
            sum += Math.pow((coordinatesFirst[i] - coordinatesSecond[i]), 2);
        }
        return sum;
    }

    private void initializePoints() {
        int currentGroup = 1;
        for (Point point : pointsToGroup) {
            point.setGroup(currentGroup);
            if (currentGroup == amountOfGroups) {
                currentGroup = 1;
            } else {
                currentGroup++;
            }
        }
    }

    private void initializeCentroids() {
        for (int i = 0; i < amountOfGroups; i++) {
            int amountOfAttributes = pointsToGroup.get(0).getCoordinates().length;
            centroids.add(new Centroid(new double[amountOfAttributes], i + 1));
        }
        for (Centroid centroid : centroids) {
            setNewCoordinatesForCentroid(centroid, pointsToGroup);
        }

    }


    private void setNewCoordinatesForCentroid(Centroid centroid, List<Point> points) {
        int length = centroid.getCoordinates().length;
        double[] sums = new double[length];
        int count = 0;
        for (Point point : points) {
            if (point.getGroup() == centroid.getGroupID()) {
                count++;
                for (int i = 0; i < sums.length; i++) {
                    sums[i] += point.getCoordinates()[i];
                }

            }
        }
        for (int i = 0; i < centroid.getCoordinates().length; i++) {
            centroid.getCoordinates()[i] = sums[i] / count;
        }

    }

    public int getAmountOfGroups() {
        return amountOfGroups;
    }

    public void setAmountOfGroups(int amountOfGroups) {
        this.amountOfGroups = amountOfGroups;
    }

    public List<Point> getPointsToGroup() {
        return pointsToGroup;
    }

    public void setPointsToGroup(List<Point> pointsToGroup) {
        this.pointsToGroup = pointsToGroup;
    }

    @Override
    public String toString() {
        return "Grouper{" +
                "training=" + pointsToGroup +
                '}';
    }
}
