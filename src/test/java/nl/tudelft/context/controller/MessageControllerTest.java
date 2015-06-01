package nl.tudelft.context.controller;

import de.saxsys.javafx.test.JfxRunner;
import javafx.scene.text.Text;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * @author Jim Hommes
 * @version 1.0
 * @since 19-05-2015
 */
@RunWith(JfxRunner.class)
public class MessageControllerTest {

    /**
     * Test for displaying the text.
     */
    @Test
    public void testDisplayText() {
        MessageController mc = new MessageController();
        String text = "Display Test";

        mc.displayMessage(text);

        assertEquals(mc.message.getText(), text);
    }


}