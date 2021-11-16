import java.util.Arrays;

public class BingoCard {
    /*
      The two arrays are private and their structure is NEVER exposed to another
      class, which is why the getCardNumbers returns a String that needs
      further processing.

      While this is not computationally efficient, it is good programming
      practice to hide data structures (information hiding).
    */
    private int[][] numbers;
    private boolean[][] markedOff;

    private int numberOfRows;
    private int numberOfColumns;

    public BingoCard(int numberOfRows, int numberOfColumns) {
        setNumberOfRows(numberOfRows);
        setNumberOfColumns(numberOfColumns);

        numbers = new int[numberOfRows][numberOfColumns];
        markedOff = new boolean[numberOfRows][numberOfColumns];
        resetMarked();
    }

    public void resetMarked() {
        markedOff = new boolean[numberOfRows][numberOfColumns];
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public String getCardNumbers() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getNumberOfRows(); i++) {
            for (int j = 0; j < getNumberOfColumns(); j++) {
                sb.append(numbers[i][j]).append(Defaults.getNumberSeparator());
            }
        }
        return sb.substring(0, sb.toString().length() - Defaults.getNumberSeparator().length());
    }

    public void setCardNumbers(String[] numbersAsStrings) {
        int[] numbersList =
                Arrays.stream(numbersAsStrings).mapToInt(Integer::parseInt).toArray();

        int index = 0;
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                numbers[i][j] = numbersList[index];
                index++;
            }
        }
    }

    public void markNumber(int number) {
        boolean found = false;
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                if (numbers[i][j] == number) {
                    markedOff[i][j] = true;
                    found = true;
                }
            }
        }
        if (found) {
            System.out.printf("Marked off %d%n", number);
        } else {
            System.out.printf("Number %d not on this card%n", number);
        }
    }

    public boolean isWinner() {
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                if (!markedOff[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}