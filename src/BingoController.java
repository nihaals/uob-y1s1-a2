import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class BingoController {

    private final String[] mainMenuItems = {"Exit",
            "Play bingo",
            "Set number separator",
            "Create a bingo card",
            "List existing cards",
            "Set bingo card size"};

    private final String OPTION_EXIT = "0";
    private final String OPTION_PLAY = "1";
    private final String OPTION_SEPARATOR = "2";
    private final String OPTION_CREATE_CARD = "3";
    private final String OPTION_LIST_CARDS = "4";
    private final String OPTION_SIZE = "5";

    private int currentRowSize = Defaults.DEFAULT_NUMBER_OF_ROWS;
    private int currentColumnSize = Defaults.DEFAULT_NUMBER_OF_COLUMNS;

    private final ArrayList<BingoCard> cards = new ArrayList<>();

    public int getCurrentRowSize() {
        return currentRowSize;
    }

    public void setCurrentRowSize(int currentRowSize) {
        this.currentRowSize = currentRowSize;
    }

    public int getCurrentColumnSize() {
        return currentColumnSize;
    }

    public void setCurrentColumnSize(int currentColumnSize) {
        this.currentColumnSize = currentColumnSize;
    }

    public void addNewCard(BingoCard card) {
        cards.add(card);
    }

    public void setSize() {
        setCurrentRowSize(parseInt(Toolkit.getInputForMessage(
                "Enter the number of rows for the card").trim()));
        setCurrentColumnSize(parseInt(Toolkit.getInputForMessage(
                "Enter the number of columns for the card").trim()));
        System.out.printf("The bingo card size is set to %d rows X %d columns%n",
                getCurrentRowSize(),
                getCurrentColumnSize());
    }

    public void createCard() {
        int numbersRequired = currentRowSize * currentColumnSize;

        String[] numbers;

        boolean correctAmountOfNumbersEntered;

        do {
            numbers = Toolkit.getInputForMessage(
                            String.format(
                                    "Enter %d numbers for your card (separated by " +
                                            "'%s')",
                                    numbersRequired,
                                    Defaults.getNumberSeparator()))
                    .trim()
                    .split(Defaults.getNumberSeparator());

            correctAmountOfNumbersEntered = numbers.length == numbersRequired;
            if (!correctAmountOfNumbersEntered) {
                System.out.printf("Try again: you entered %d numbers instead of %d%n", numbers.length, numbersRequired);
            }
        } while (!correctAmountOfNumbersEntered);

        System.out.println("You entered");
        System.out.println(Toolkit.printArray(numbers));

        BingoCard card = new BingoCard(currentRowSize, currentColumnSize);
        card.setCardNumbers(numbers);
        addNewCard(card);
    }

    public void listCards() {
        for (int i = 0; i < cards.size(); i++) {
            System.out.printf("Card %2d numbers:%n", i);
            printCardAsGrid(cards.get(i).getCardNumbers());
        }
    }

    public void printCardAsGrid(String numbers) {
        String[] numberArray = numbers.split(Defaults.getNumberSeparator());

        int rowSize = currentRowSize;
        int columnSize = currentColumnSize;
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnSize; j++) {
                int number = Integer.parseInt(numberArray[i * columnSize + j]);
                System.out.printf("%2d", number);
                if (j < columnSize - 1) {
                    System.out.print(Defaults.getNumberSeparator());
                }
            }
            System.out.println();
        }
    }

    public void setSeparator() {
        String sep = Toolkit.getInputForMessage("Enter the new separator");
        Defaults.setNumberSeparator(sep);
        System.out.printf("Separator is '%s'%n", Defaults.getNumberSeparator());
    }

    public void resetAllCards() {
        for (BingoCard card : cards) {
            card.resetMarked();
        }
    }

    public void markNumbers(int number) {
        // Check every card to see if the number is on it
        for (int i = 0; i < cards.size(); i++) {
            System.out.printf("Checking card %d for %d%n", i, number);
            cards.get(i).markNumber(number);
        }
    }

    public int getWinnerId() {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isWinner()) {
                return i;
            }
        }
        return Defaults.NO_WINNER;
    }

    public void play() {
        System.out.println("Eyes down, look in!");
        resetAllCards();

        boolean weHaveAWinner;
        do {
            markNumbers(parseInt(
                    Toolkit.getInputForMessage("Enter the next number")
                            .trim()));

            int winnerID = getWinnerId();
            weHaveAWinner = winnerID != Defaults.NO_WINNER;
            if (weHaveAWinner)
                System.out.printf("And the winner is card %d%n", winnerID);
        } while (!weHaveAWinner);
    }

    public String getMenu(String[] menuItems) {
        StringBuilder menuText = new StringBuilder();
        for (int i = 0; i < menuItems.length; i++) {
            menuText.append(" ").append(i).append(": ").append(menuItems[i]).append("\n");
        }
        return menuText.toString();
    }

    public void run() {
        boolean finished = false;
        do {
            switch (Toolkit.getInputForMessage(getMenu(mainMenuItems)).trim()) {
                case OPTION_EXIT:
                    finished = true;
                    break;
                case OPTION_PLAY:
                    play();
                    break;
                case OPTION_SEPARATOR:
                    setSeparator();
                    break;
                case OPTION_CREATE_CARD:
                    createCard();
                    break;
                case OPTION_LIST_CARDS:
                    listCards();
                    break;
                case OPTION_SIZE:
                    setSize();
                    break;
            }
        } while (!finished);
    }
}
