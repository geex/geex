package nl.tudelft.context.controller.locator;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nl.tudelft.context.drawable.DrawableLocatorAnnotation;
import nl.tudelft.context.drawable.graph.AbstractDrawableNode;
import nl.tudelft.context.model.graph.DefaultNode;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author René Vennik
 * @version 1.0
 * @since 10-6-2015
 */
public class LocatorController {

    /**
     * The locator of the graph.
     */
    Pane locator;

    /**
     * The locator indicator of the graph.
     */
    Rectangle locatorIndicator;

    /**
     * Height of the locator.
     */
    public static final int LOCATOR_HEIGHT = 43;

    /**
     * Min and max references map by column.
     */
    Optional<Map<Integer, List<Integer>>> optionalTotalMap = Optional.empty();

    /**
     * The annotations that are drawn.
     */
    List<DrawableLocatorAnnotation> annotations;

    /**
     * Location currently shown (columns).
     */
    ObjectProperty<List<Integer>> positionProperty = new SimpleObjectProperty<>();

    /**
     * Minimum and maximum of ref positions.
     */
    int minRefPosition, maxRefPosition;

    /**
     * Init the locator controller that shows the current position on the reference genome.
     *
     * @param locator          The locator pane
     * @param labelMapProperty Currently active nodes
     * @param positionProperty Location currently shown (columns)
     */
    public LocatorController(final Pane locator,
                             final ObjectProperty<Map<Integer, List<AbstractDrawableNode>>> labelMapProperty,
                             final ObjectProperty<List<Integer>> positionProperty) {

        this.locator = locator;
        this.positionProperty = positionProperty;

        labelMapProperty.addListener((observable, oldValue, newValue) -> {
            addMutations(newValue);
            initLabelMap(newValue);
            setPosition();
        });

        positionProperty.addListener(event -> setPosition());
        locator.widthProperty().addListener(event -> {
            setPosition();
        });

    }

    /**
     * Init indicator.
     */
    private void initIndicator() {

        locatorIndicator = new Rectangle();
        locatorIndicator.setHeight(LOCATOR_HEIGHT);
        locatorIndicator.setTranslateY(2);
        locator.getChildren().add(locatorIndicator);

    }

    /**
     * Init label map.
     *
     * @param labelMap Map to locate
     */
    private void initLabelMap(final Map<Integer, List<AbstractDrawableNode>> labelMap) {

        minRefPosition = Integer.MAX_VALUE;
        maxRefPosition = Integer.MIN_VALUE;

        HashMap<Integer, List<Integer>> totalMap = new HashMap<>();
        labelMap.forEach((column, labels) -> {
            int min = labels.stream()
                    .map(AbstractDrawableNode::getNode)
                    .mapToInt(DefaultNode::getRefStartPosition)
                    .min().getAsInt();
            int max = labels.stream()
                    .map(AbstractDrawableNode::getNode)
                    .mapToInt(DefaultNode::getRefEndPosition)
                    .max().getAsInt();
            totalMap.put(column, Arrays.asList(min, max));
            minRefPosition = Math.min(minRefPosition, min);
            maxRefPosition = Math.max(maxRefPosition, max);
        });
        optionalTotalMap = Optional.of(totalMap);

    }

    /**
     * Sets the position of the indicator.
     */
    private void setPosition() {

        optionalTotalMap.ifPresent(totalMap -> {
            double width = locator.getWidth();

            List<List<Integer>> list = positionProperty.get().stream()
                    .map(totalMap::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            int min = list.stream()
                    .mapToInt(x -> x.get(0))
                    .min().getAsInt() - minRefPosition;

            int max = list.stream()
                    .mapToInt(x -> x.get(1))
                    .max().getAsInt();

            double scale = width / maxRefPosition;

            locatorIndicator.setTranslateX(min * scale);
            locatorIndicator.setWidth((max - min) * scale);

            annotations.stream().forEach(annotation -> annotation.position(width, maxRefPosition));
        });

    }

    /**
     * The function that draws the mutations in the positionbar.
     * @param labelMap The LabelMap that everything is derived from.
     */
    public void addMutations(final Map<Integer, List<AbstractDrawableNode>> labelMap) {

        annotations = labelMap.values().stream()
                .flatMap(Collection::stream)
                .map(AbstractDrawableNode::getNode)
                .map(DefaultNode::getAnnotations)
                .flatMap(Collection::stream)
                .map(DrawableLocatorAnnotation::new)
                .collect(Collectors.toList());

        locator.getChildren().setAll(annotations);
        initIndicator();

    }

}
