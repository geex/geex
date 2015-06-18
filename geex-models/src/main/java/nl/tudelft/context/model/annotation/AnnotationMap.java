package nl.tudelft.context.model.annotation;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Jasper Nieuwdorp
 * @version 1.0
 * @since 21-5-2015
 */
public class AnnotationMap {

    TreeMap<Integer, List<Annotation>> annotationsByStart;

    /**
     * Create annotation map based on the annotations indexed by ref start and ref end.
     *
     * @param annotations List containing annotations.
     */
    public AnnotationMap(List<Annotation> annotations) {

        TreeMap<Integer, Annotation> test = annotations.stream().collect(Collectors.toMap(
                Annotation::getStart,
                Function.identity(),
                (v1, v2) -> v1,
                TreeMap::new
        ));

    }

    /**
     * Get annotations between ref start and end position.
     *
     * @param refStart From which ref start (inclusive)
     * @param refEnd   To which ref end (inclusive)
     * @return Annotations between ref start and end position
     */
    public NavigableMap<Integer, List<Annotation>> annotationsBetween(final Integer refStart, final Integer refEnd) {
        return subMap(refStart, true, refEnd, true);
    }


    /**
     * To string method for the AnnotationMap.
     *
     * @return String representing the values in the AnnotationMap, each annotation on a new line
     */
    @Override
    public final String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry a : entrySet()) {
            result.append(a.getValue().toString());
            result.append(System.getProperty("line.separator"));
        }
        return result.toString();

    }

}


