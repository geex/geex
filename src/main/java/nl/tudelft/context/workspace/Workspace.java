package nl.tudelft.context.workspace;

import nl.tudelft.context.service.LoadGraphService;
import nl.tudelft.context.service.LoadNewickService;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Gerben Oolbekkink <g.j.w.oolbekkink@gmail.com>
 * @version 1.0
 * @since 8-5-2015
 */
public class Workspace {
    private File directory;

    private List<LoadGraphService> graphList;
    private List<LoadNewickService> nwkList;

    public Workspace(File directory) {
        if (directory == null) {
            return;
        }
        this.directory = directory;
        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loop over the directory
     */
    private void load() throws IOException {
        Finder finder = new Finder("*.{edge.graph,node.graph,nwk}");

        Files.walkFileTree(this.directory.toPath(), finder);

        Stream<File> edges = finder.files().stream()
                .map(Path::toFile)
                .filter(path -> path.toString()
                        .toLowerCase()
                        .endsWith(".edge.graph"));
        List<File> nodes = finder.files().stream()
                .map(Path::toFile)
                .filter(path -> path.toString()
                        .toLowerCase()
                        .endsWith(".node.graph"))
                .collect(Collectors.toList());

        graphList = edges.map(
                edgeFile -> {
                    File nodeFile = nodes.stream()
                            .filter(file ->
                                    file.getAbsolutePath()
                                            .replaceFirst("\\.node\\.graph$", "")
                                            .equals(edgeFile.getAbsolutePath()
                                                    .replaceFirst("\\.edge\\.graph$", "")))
                            .findFirst().orElse(null);

                    if (nodeFile == null)
                        return null;

                    return new LoadGraphService(nodeFile, edgeFile);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        nwkList = finder.files().stream()
                .filter(path -> path.toString().toLowerCase().endsWith(".nwk"))
                .map(path -> new LoadNewickService(path.toFile()))
                .collect(Collectors.toList());
    }

    public LoadGraphService getActiveGraph() {
        return graphList.get(0);
    }

    public LoadNewickService getActiveTree() {
        return nwkList.get(0);
    }
}
