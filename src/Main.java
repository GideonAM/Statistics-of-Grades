import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter the scores separated by single spaces only: ");
        Scanner userInput = new Scanner(System.in);

        try {
            int[] scores = getGrades(userInput.nextLine());
            int[] stats = new int[5];

            for (int score: scores) {
                if (score <= 20)
                    stats[0] += 1;
                else if (score <= 40)
                    stats[1] += 1;
                else if (score <= 60)
                    stats[2] += 1;
                else if (score <= 80)
                    stats[3] += 1;
                else if (score <= 100)
                    stats[4] += 1;
            }

            System.out.println(Arrays.toString(stats));

            printBarGraph(scores, stats);

        } catch (BadInput exception) {
            System.out.println(exception.getMessage());
        } catch (NumberFormatException exception) {
            System.out.println("Invalid input, numbers only separated by single space");
        }
    }

    private static void printBarGraph(int[] scores, int[] stats) {
        String values = String.format("""
                values:
                
                The maximum grade is %d
                The minimum grade is %d
                The average grade is %f
                
                Graph:
                """, maximumGrade(scores), minimumGrade(scores), averageGrade(scores));

        System.out.println(values);

        var maxStatRange = Arrays.stream(stats).max().orElse(0);

        // for printing the bar graph of various score ranges
        for (int i = maxStatRange; i > 0; i--) {
            System.out.print(i + " >  ");

            for (int stat : stats)
                if (i <= stat) {
                    System.out.print(" #######  ");
                } else {
                    System.out.print("          ");
                }

            System.out.println();
        }

        System.out.println("""
  +-----------+---------+---------+---------+---------+
  I    0-20   I  21-40  I  41-60  I  61-80  I  81-100 I
""");

    }

    private static int[] getGrades(String grades) {
        String[] splitStringArray = grades.split(" ");
        int[] intArray = new int[splitStringArray.length];

        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = Integer.parseInt(splitStringArray[i]);

            if (intArray[i] < 0 || intArray[i] > 100)
                throw new BadInput("Number should be between 0 and 100");
        }

        return intArray;
    }

    private static class BadInput extends RuntimeException{
        public BadInput(String message) {
            super(message);
        }
    }

    private static int maximumGrade(int[] grades) {
        if (grades.length < 1)
            return 0;

        int max = grades[0];
        for (int score : grades)
            max = Math.max(max, score);

        return max;
    }

    private static int minimumGrade(int[] grades) {
        if (grades.length < 1)
            return 0;

        int min = grades[0];
        for (int score : grades)
            min = Math.min(min, score);

        return min;
    }

    private static double averageGrade(int[] grades) {
        double totalGrades = 0;

        for (int score : grades)
            totalGrades += score;

        return totalGrades / grades.length;
    }

}