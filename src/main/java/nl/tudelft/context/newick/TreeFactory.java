package nl.tudelft.context.newick;

import net.sourceforge.olduvai.treejuxtaposer.TreeParser;
import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 3-5-2015
 */
public final class TreeFactory {
    public NodeFactory nodeFactory = new NodeFactory();
    final int ROW_HEIGHT = 20;
    final double WEIGHT_SCALE = 1e5;

    /**
     * Creates a new phylogenetic tree, based on the information in the Newick file.
     *
     * @param nwkFile                       the Newick file
     * @return                              a phylogenetic tree
     * @throws FileNotFoundException        when nwkFile is not found
     * @throws UnsupportedEncodingException when nwkFile has an unsupported encoding
     */
    public Tree getTree(final File nwkFile) throws FileNotFoundException, UnsupportedEncodingException {
        Tree tree = new Tree();

        parseTree(nwkFile, tree);

        return tree;
    }

    /**
     * Parses the file and creates the nodes to add to the tree.
     *
     * @param nwkFile                       the Newick file
     * @param tree                          the Tree to add the nodes to
     * @throws FileNotFoundException        when the nwkFile is not found
     * @throws UnsupportedEncodingException when the nwkFile has an unsupported encoding
     */
    public void parseTree(final File nwkFile, final Tree tree)
            throws FileNotFoundException, UnsupportedEncodingException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(nwkFile), "UTF-8"));
        TreeParser tp = new TreeParser(fileReader);
        net.sourceforge.olduvai.treejuxtaposer.drawer.Tree nwkTree = tp.tokenize(1, "", null);
        Node root = nodeFactory.getNode(nwkTree.getRoot());
        root.setTranslateX(0);
        root.setTranslateY(0);
        getOffspring(nwkTree.getRoot(), root, tree, 0);
    }

    /**
     * Recursive call that creates nodes and edges for the tree.
     *
     * @param node   the node, read by the newick parser
     * @param parent the parent node to add this node to as a child
     * @param tree   the tree to add the nodes and edges to
     * @param row    the current row (depth) of the node
     * @return       the new row (depth) of the next node
     */
    public int getOffspring(final TreeNode node, final Node parent, final Tree tree, final int row) {
        tree.addVertex(parent);

        int ret = row;

        boolean hasChildren = false;
        for (int i = 0; i < node.numberLeaves; i += 1) {
            TreeNode child = node.getChild(i);
            if (child != null) {
                hasChildren = true;
                Node n = createNode(child, parent, row);
                ret = getOffspring(child, n, tree, row);
                parent.addChild(n);
                if (i > 0) {
                    addDummy(parent, n, tree);
                } else {
                    tree.addEdge(parent, n);
                }
            }
        }

        return ret + (hasChildren ? 0 : 1);
    }

    /**
     * Creation of the node.
     *
     * @param child  the node, read by the newick parser
     * @param parent the parent node to add the child to
     * @param row    the current row (depth) of the node
     * @return       the node as a Node
     */
    public Node createNode(TreeNode child, Node parent, int row) {
        Node n = nodeFactory.getNode(child);
        n.setTranslateX(parent.translateXProperty().doubleValue() + WEIGHT_SCALE * n.getWeight());
        n.setTranslateY(row * ROW_HEIGHT);

        return n;
    }

    /**
     * Adds a dummy node, so that we can use orthogonal lines.
     *
     * @param parent the parent of the node
     * @param n      the node
     * @param tree   the tree to add the dummy to
     */
    public void addDummy(Node parent, Node n, Tree tree) {
        Node dummy = new Node("", 0);
        dummy.setTranslateX(parent.translateXProperty().doubleValue());
        dummy.setTranslateY(n.translateYProperty().doubleValue());
        tree.addVertex(dummy);
        tree.addEdge(parent, dummy);
        tree.addEdge(dummy, n);
    }
}
