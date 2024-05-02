import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> toConvert = getTraining("Training/iris_training.txt");
        List<Point> points = convertStringsToPoints(toConvert);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Put in the amount of groups");
        int amountOfGroups = scanner.nextInt();

        Grouper grouper = new Grouper(points, amountOfGroups);
        grouper.group();
        grouper.showGroupingResults();
    }

    public static List<Point> convertStringsToPoints(List<String> lines) {
        return lines.stream().map(line -> {
            String[] split = line.split("\\t");
            double[] coords = new double[split.length - 1];
            for (int i = 0; i < split.length - 1; i++) {
                coords[i] = Double.parseDouble(split[i].replaceAll(",", "."));
            }
            return new Point(coords, split[split.length - 1].strip());
        }).collect(Collectors.toList());
    }

    public static List<String> getTraining(String fileName) throws IOException {
        List<String> strings = Files.readAllLines(Path.of(fileName));
        return strings;
    }
}
