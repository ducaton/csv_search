import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVData {

    private Map<Integer, String> columnData = new HashMap<>();

    private Map<Integer, String> sortedColumnData = new LinkedHashMap<>();

    private Map<Integer, String> foundData = new LinkedHashMap<>();

    BufferedReader CSVFile;

    public void FillColumnsFromFile(int columnNum) {
        try {
            CSVFile = new BufferedReader(new FileReader("airports.csv"));
            String line = CSVFile.readLine();
            while (line != null) {
                String[] columns = line.split(",(?! )");
                columnData.put(Integer.parseInt(columns[0]), columns[columnNum-1]);
                line = CSVFile.readLine();
            }
            CSVFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SortColumns() {
        columnData.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach((entry) -> {
                        sortedColumnData.put(entry.getKey(), entry.getValue());
                });
    }

    public int SearchInColumn(String data) {
        sortedColumnData.forEach((key, value) -> {
            if ((value).toLowerCase().startsWith(data)
                    || (value).toLowerCase().startsWith("\"" + data)) {
                foundData.put(key, value);
            }
        });
        return foundData.size();
    }

    public void PrintFoundResults() {
        try {
            CSVFile = new BufferedReader(new FileReader("airports.csv"));
            int c;
            int partID = 0;
            int ID = 0;
            boolean readingID = true;
            boolean puttingLine = false;
            boolean comparingKey = false;
            char character;
            while((c = CSVFile.read()) != -1) {
                character = (char) c;
                if (character == '\r' || character == '\n') {
                    readingID = true;
                    if (puttingLine) {
                        puttingLine = false;
                        foundData.replace(ID, foundData.get(ID) + "]");
                    }
                    continue;
                }
                if (character == ',' && !puttingLine) {
                    readingID = false;
                    ID = partID;
                    comparingKey = true;
                    partID = 0;
                }
                if (readingID) {
                    if (partID == 0)
                        partID = Character.getNumericValue(character);
                    else
                        partID = partID * 10 + Character.getNumericValue(character);
                }
                if (foundData.containsKey(ID) && comparingKey) {
                    foundData.replace(ID, foundData.get(ID) + "[" + ID);
                    comparingKey = false;
                    puttingLine = true;
                }
                if (puttingLine) {
                    foundData.replace(ID, foundData.get(ID) + character);
                }
            }
            CSVFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        foundData.forEach((key, value) -> System.out.println(value));
        foundData.clear();
    }
}
