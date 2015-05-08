package nl.tudelft.context.newick;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 06-05-2015
 */
public class TreeTest {
    protected static File file;
    protected static TreeFactory treeFactory;
    protected static Tree tree;
    protected static Node root;

    /**
     * Set up TreeFactory
     */
    @BeforeClass
    public static void beforeClass() {
        file = new File(TreeTest.class.getResource("/newick/10strains.nwk").getPath());
        treeFactory = new TreeFactory();
    }

    /**
     * Helper class for loading the file.
     */
    public static void loadFile() {
        try {
            tree = treeFactory.getTree(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        root = tree.getFirstNode();
    }

    /**
     * Test if it can get the first node and returns
     * null if it doesn't have any.
     */
    @Test
    public void testFirstNode() {
        tree = new Tree();
        assertNull(tree.getFirstNode());
        root = new Node("A", 1.39);
        tree.addVertex(root);
        root.addChild(new Node("B", .123));
        assertEquals(root, tree.getFirstNode());
    }

    /**
     * Test if the tree contains 10 leaves.
     */
    @Test
    public void testNumLeaves() {
        loadFile();
        assertEquals(10, countLeaves(root));
    }

    /**
     * Test if the tree contains 9 ancestors
     */
    @Test
    public void testNumAncestors() {
        loadFile();
        assertEquals(9, countAncestors(root));
    }

    /**
     * Test the output of toString
     */
    @Test
    public void testToString() {
        loadFile();
        assertEquals("Node<,0.0>\n" +
                "Node<,4.752532404381782E-4>\n" +
                "Node<,0.001130109652876854>\n" +
                "Node<,0.0010106657864525914>\n" +
                "Node<TKK_04_0031,0.0019066613167524338>\n" +
                "Node<,0.001823665457777679>\n" +
                "Node<,6.628296105191112E-4>\n" +
                "Node<TKK_02_0068,0.0020415722392499447>\n" +
                "Node<TKK_02_0018,6.695450283586979E-4>\n" +
                "Node<TKK-01-0026,7.12309149093926E-4>\n" +
                "Node<TKK-01-0058,0.0028818827122449875>\n" +
                "Node<,0.004314309451729059>\n" +
                "Node<TKK_REF,5.660219176206738E-5>\n" +
                "Node<TKK-01-0066,1.1321144120302051E-4>\n" +
                "Node<,0.003898313269019127>\n" +
                "Node<,0.0011305224616080523>\n" +
                "Node<TKK-01-0015,0.0021152603439986706>\n" +
                "Node<TKK-01-0029,0.0015547169605270028>\n" +
                "Node<TKK_04_0002,0.0010063934605568647>\n",
                tree.toString()
        );
    }

    /**
     * Counts the number of leaves.
     * @param node the node
     * @return the number of leaves from node
     */
    public int countLeaves(Node node) {
        if (node.getChildren().size() == 0) {
            return 1;
        }

        int sum = 0;

        for (Node child : node.getChildren()) {
            sum += countLeaves(child);
        }

        return sum;
    }

    /**
     * Counts the number of ancestors in the tree.
     * @param node the node
     * @return the number of ancestors of the children of the node
     */
    public int countAncestors(Node node) {
        if (node.getChildren().size() == 0) {
            return 0;
        }

        int sum = 0;

        if (node.getChildren().size() > 0) {
            for (Node child : node.getChildren()) {
                sum += countAncestors(child);
            }
            sum += 1;
        }

        return sum;
    }
}
