import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LRParser {

    private List<ParserListener> listeners = new ArrayList<>();

    private LinkedList<ParserTreeNode> forest = new LinkedList<>();
    private int ptr;

    public boolean parse(String input) throws ParseException {
        String inputWithEnding = input + "$";

        initialize();
        int currentState = 0;
        while (ptr < inputWithEnding.length()) {
            char currentChar = inputWithEnding.charAt(ptr);
            ParserAction action = parserAction(currentChar, currentState);
            switch(action) {
                case SHIFT: currentState = shift(currentState, currentChar);
                    break;
                case REDUCE_1: currentState = reduce(NonTerminal.LIST, 3);
                    break;
                case REDUCE_2: currentState = reduce(NonTerminal.LIST, 1);
                    break;
                case REDUCE_3: currentState = reduce(NonTerminal.ELEMENT, 1);
                    break;
                case REDUCE_4: currentState = reduce(NonTerminal.ELEMENT, 1);
                    break;
                case ACCEPT: return true;
                case ERROR: throw new ParseException(input, ptr);
            }
        }
        return false;
    }

    private int reduce(Object newSymbol, int nMembers) {
        ParserTreeNode newRoot = new ParserTreeNode(newSymbol);
        newRoot.addChildren(forest.subList(forest.size()-nMembers, forest.size()));
        for (int i = 0; i < nMembers; i++)
            forest.removeLast();

        int newState = goTo(forest.getLast().state, newRoot.symbol);
        newRoot.state = newState;
        forest.addLast(newRoot);

        notifyListeners(newRoot);

        return newState;
    }

    private void notifyListeners(ParserTreeNode newRoot) {
        for (ParserListener l : listeners) {
            if (NonTerminal.LIST.equals(newRoot.symbol)) {
                l.processList(newRoot);
            }
            else if (NonTerminal.ELEMENT.equals(newRoot.symbol)) {
                l.processElement(newRoot);
            }
        }
    }

    private int shift(int currentState, Object currentSymbol) {
        int newState = goTo(currentState, currentSymbol);
        this.forest.add(new ParserTreeNode(currentSymbol, newState));
        this.ptr++;
        return newState;
    }

    private void initialize() {
        ptr = 0;
        forest.add(new ParserTreeNode(0));
    }

    public void addListener(ParserListener listener) {
        this.listeners.add(listener);
    }

    private int goTo(int currentState, Object symbol) {
        switch(currentState) {
            case 0: if (symbol == NonTerminal.LIST) return 1;
                    if (symbol == NonTerminal.ELEMENT) return 2;
                    if (symbol.equals(Character.valueOf('a'))) return 3;
                    if (symbol.equals(Character.valueOf('b'))) return 4;
                    return currentState;
            case 1: if (symbol.equals(Character.valueOf(','))) return 5;
                return currentState;
            case 5: if (symbol == NonTerminal.ELEMENT) return 6;
                    if (symbol.equals(Character.valueOf('a'))) return 3;
                    if (symbol.equals(Character.valueOf('b'))) return 4;
                return currentState;
            default: return currentState;
        }
    }

    private ParserAction parserAction(char nextSymbol, int currentState) {
        switch(currentState) {
            case 0: if (nextSymbol == 'a') return ParserAction.SHIFT;
                    if (nextSymbol == 'b') return ParserAction.SHIFT;
                    return ParserAction.ERROR;
            case 1: if (nextSymbol == ',') return ParserAction.SHIFT;
                if (nextSymbol == '$') return ParserAction.ACCEPT;
                return ParserAction.ERROR;
            case 2: if (nextSymbol == ',') return ParserAction.REDUCE_2;
                if (nextSymbol == '$') return ParserAction.REDUCE_2;
                return ParserAction.ERROR;
            case 3: if (nextSymbol == ',') return ParserAction.REDUCE_3;
                if (nextSymbol == '$') return ParserAction.REDUCE_3;
                return ParserAction.ERROR;
            case 4: if (nextSymbol == ',') return ParserAction.REDUCE_4;
                if (nextSymbol == '$') return ParserAction.REDUCE_4;
                return ParserAction.ERROR;
            case 5: if (nextSymbol == 'a') return ParserAction.SHIFT;
                if (nextSymbol == 'b') return ParserAction.SHIFT;
                return ParserAction.ERROR;
            case 6: if (nextSymbol == ',') return ParserAction.REDUCE_1;
                if (nextSymbol == '$') return ParserAction.REDUCE_1;
                return ParserAction.ERROR;
            default: return ParserAction.ERROR;
        }
    }
}
