import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ParserTreeNode {

    public Object symbol;
    public int state;
    private List<ParserTreeNode> children = new LinkedList<>();

    public ParserTreeNode(Object symbol, int state) {
        this.symbol = symbol;
        this.state = state;
    }

    public ParserTreeNode(int state) {
        this.state = state;
    }

    public ParserTreeNode(Object symbol) {
        this.symbol = symbol;
    }

    public ParserTreeNode() {
    }

    public void addChildren(Collection<ParserTreeNode> parserTreeNodes) {
        children.addAll(parserTreeNodes);
    }

    @Override
    public String toString() {
        return this.toString("");
    }

    public List<ParserTreeNode> getChildren() {
        return children;
    }

    private String toString(String padding) {
        StringBuilder builder = new StringBuilder();
        builder.append(padding);
        builder.append(MessageFormat.format("{0} ({1})", symbol, state));
        builder.append(System.lineSeparator());

        for (ParserTreeNode node : this.children) {
            builder.append(padding);
            builder.append(node.toString("\t"));
            builder.append(System.lineSeparator());
        }
        return builder.toString();
    }
}
