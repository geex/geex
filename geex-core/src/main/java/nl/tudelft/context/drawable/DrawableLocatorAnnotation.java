package nl.tudelft.context.drawable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.tudelft.context.controller.locator.LocatorController;
import nl.tudelft.context.model.annotation.Annotation;


/**
 * @author Jim
 * @since 6/14/2015
 */
public class DrawableLocatorAnnotation extends Rectangle {

    /**
     * The maxRefPosition of the nodes.
     */
    double maxRefPosition;

    /**
     * The width of the locator bar.
     */
    double width;

    /**
     * The annotation that is represented.
     */
    Annotation annotation;

    /**
     * The annotation that is drawn.
     *
     * @param annotation The annotation that is drawn.
     */
    public DrawableLocatorAnnotation(final Annotation annotation) {

        this.annotation = annotation;

        setStroke(Color.BLUE);
        setTranslateY(2);
        setHeight(LocatorController.LOCATOR_HEIGHT);

    }

    /**
     * Function that calculates the position.
     *
     * @param width Width of the locatorBar
     * @param maxRefPosition The maxRefPosition of the annotations to be drawn.
     */
    public void position(final double width, final int maxRefPosition) {
        double scale = width / maxRefPosition;
        setTranslateX(annotation.getStart() * scale);
        setWidth(Math.min(DrawableEdge.MINIMUM_LINE_WIDTH, (annotation.getEnd() - annotation.getStart()) * scale));
    }
}
