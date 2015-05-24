package nl.tudelft.context.drawable;

import de.saxsys.javafx.test.JfxRunner;
import nl.tudelft.context.model.newick.Node;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * @author Jasper Boot <mrjasperboot@gmail.com>
 * @version 1.0
 * @since 18-05-2015
 */
@RunWith(JfxRunner.class)
public class NewickLabelTest {
    public Node node;

    /**
     * Gets a label based on a leaf node.
     *
     * @return a newick label.
     */
    public NewickLabel getLabel() {
        node = new Node("Test node", 1.23);
        node.setTranslateX(2.34);
        node.setTranslateY(3.45);
        return new NewickLabel(node);
    }

    /**
     * Gets a label based on a parent node.
     *
     * @return a newick label.
     */
    public NewickLabel getLabelUnknown() {
        node = new Node("", 2.34);
        node.setTranslateX(3.45);
        node.setTranslateY(4.56);
        return new NewickLabel(node);
    }

    /**
     * Check if the newick label set according to the given node.
     */
    @Test
    public void testName() {
        NewickLabel newickLabel = getLabel();
        assertEquals("Test node", newickLabel.getText());
    }

    /**
     * Checks if the location is properly set.
     */
    @Test
    public void testLocation() {
        NewickLabel newickLabel = getLabel();
        assertEquals(node.translateXProperty().floatValue(),
                newickLabel.translateXProperty().floatValue(), 1e-6);
        assertEquals(node.translateYProperty().floatValue(),
                newickLabel.translateYProperty().floatValue(), 1e-6);
    }

    /**
     * The cache should be initialized.
     */
    @Test
    public void testCache() {
        NewickLabel newickLabel = getLabel();
        assertTrue(newickLabel.isCache());
    }

    /**
     * Leaf nodes should not have the ancestor style class.
     */
    @Test
    public void testAncestor() {
        NewickLabel newickLabel = getLabel();
        assertFalse(newickLabel.getStyleClass().contains("ancestor"));
    }

    /**
     * Parent nodes should have the ancestor style class.
     */
    @Test
    public void testAncestorUnknown() {
        NewickLabel newickLabel = getLabelUnknown();
        assertTrue(newickLabel.getStyleClass().contains("ancestor"));
    }

    /**
     * The style classes shouldn't change when a the selection of the node doesn't change.
     */
    @Test
    public void testSelectionNotChanged() {
        NewickLabel newickLabel = getLabel();
        int numClasses = newickLabel.getStyleClass().size();
        newickLabel.setSelectedClass(Node.Selection.NONE, Node.Selection.NONE);
        assertEquals(numClasses, newickLabel.getStyleClass().size());
    }

    /**
     * A new selection of ALL should result in only the style class "selected".
     */
    @Test
    public void testSelectionAll() {
        NewickLabel newickLabel = getLabel();
        newickLabel.setSelectedClass(Node.Selection.NONE, Node.Selection.ALL);
        assertFalse(newickLabel.getStyleClass().contains("partial"));
        assertTrue(newickLabel.getStyleClass().contains("selected"));
    }

    /**
     * A new selection of PARTIAL should result in only the style class "partial".
     */
    @Test
    public void testSelectionPartial() {
        NewickLabel newickLabel = getLabel();
        newickLabel.setSelectedClass(Node.Selection.NONE, Node.Selection.PARTIAL);
        assertTrue(newickLabel.getStyleClass().contains("partial"));
        assertFalse(newickLabel.getStyleClass().contains("selected"));
    }

    /**
     * A new selection of NONE should not result in style class "partial", nor "selected".
     */
    @Test
    public void testSelectionNone() {
        NewickLabel newickLabel = getLabel();
        newickLabel.setSelectedClass(Node.Selection.ALL, Node.Selection.NONE);
        assertFalse(newickLabel.getStyleClass().contains("partial"));
        assertFalse(newickLabel.getStyleClass().contains("selected"));
    }
}