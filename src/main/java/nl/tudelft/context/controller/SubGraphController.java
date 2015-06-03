package nl.tudelft.context.controller;

import nl.tudelft.context.drawable.DrawableGraph;
import nl.tudelft.context.model.graph.DefaultNode;
import nl.tudelft.context.model.graph.Graph;
import nl.tudelft.context.model.graph.GraphNode;
import nl.tudelft.context.model.graph.StackGraph;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author René Vennik <renevennik@gmail.com>
 * @version 1.0
 * @since 2-6-2015
 */
public class SubGraphController extends DefaultGraphController {

    /**
     * Old graph.
     */
    StackGraph stackGraph;

    /**
     * Node containing sub graph.
     */
    GraphNode graphNode;

    /**
     * Create a sub graph controller.
     *
     * @param mainController Main controller to set views on.
     * @param stackGraph     Old graph
     * @param graphNode      Node containing sub graph
     */
    public SubGraphController(final MainController mainController,
                              final StackGraph stackGraph,
                              final GraphNode graphNode) {

        super(mainController);

        this.stackGraph = stackGraph;
        this.graphNode = graphNode;

        loadFXML("/application/graph.fxml");

    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

        super.initialize(location, resources);

        progressIndicator.setVisible(false);

        Graph subGraph = new Graph();
        stackGraph.vertexSet().stream()
                .filter(node -> graphNode.getNodes().contains(node))
                .forEach(subGraph::addVertex);

        stackGraph.edgeSet().stream()
                .forEach(edge -> {
                    DefaultNode source = stackGraph.getEdgeTarget(edge);
                    DefaultNode target = stackGraph.getEdgeSource(edge);
                    if (graphNode.getNodes().contains(source)
                            && graphNode.getNodes().contains(target)) {
                        subGraph.addEdge(source, target);
                    }
                });

        graphList.add(subGraph);

        DrawableGraph drawableGraph = new DrawableGraph(graphList.getLast());
        showGraph(drawableGraph);

    }

    @Override
    public String getBreadcrumbName() {
        return "Sub graph";
    }

}
