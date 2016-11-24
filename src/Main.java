import java.text.ParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException {
        String input = args.length > 0 ? args[0] : "";

        Scanner scanner = new Scanner(System.in);
        if (input.equals("")) {
            input = scanner.nextLine();
        }

        LRParser parser = new LRParser();

        parser.addListener(new ParserListener() {
            @Override
            public void processList(ParserTreeNode newRoot) {
                System.out.println("--> List element found!");
            }

            @Override
            public void processElement(ParserTreeNode newRoot) {
                System.out.println("--> Element found:" + newRoot.getChildren().stream().findFirst().get().symbol);
            }
        });

        do {
            try {
                if (parser.parse(input)) {
                    System.out.println("Input ACCEPTED!");
                } else {
                    System.out.println("Input REJECTED!");
                }
            } catch (ParseException ex) {
                System.out.println("Input REJECTED: " + ex.getMessage());
            }
            input = scanner.nextLine();
        } while (!input.equals(""));
    }
}
