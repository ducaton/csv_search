import java.util.Scanner;

import static java.lang.System.exit;

public class UserInput {
    public static void main (String[] args) {
        CSVData airports = new CSVData();
        airports.FillColumnsFromFile(Integer.parseInt(args[0]));
        airports.SortColumns();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Введите строку: ");
            String input = sc.nextLine().toLowerCase();
            if (input.equals("!quit"))
                exit(1);
            int resultSize;
            long searchTime;
            long start = System.currentTimeMillis();
            resultSize = airports.SearchInColumn(input);
            airports.PrintFoundResults();
            long stop = System.currentTimeMillis();
            searchTime = stop-start;
            System.out.println("Количество найденных строк: " + resultSize
                    + ". Время, затраченное на поиск: " + searchTime + "ms");
        }
    }
}
