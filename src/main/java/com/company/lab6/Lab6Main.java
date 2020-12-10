package com.company.lab6;

import com.company.lab6.analyze.ClassAnalyzer;
import com.company.lab6.analyze.ClassScanner;
import com.company.lab6.graph.Edge;
import com.company.lab6.graph.GraphModel;
import com.company.lab6.graph.Vertex;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.util.mxCellRenderer;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxPerimeter;
import com.mxgraph.view.mxStylesheet;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.nio.graphml.GraphMLExporter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.*;
import java.util.function.Consumer;


public class Lab6Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Class<?>> classes = new ClassScanner().get(URI.create("com/company/lab6/entities"));
        classAnalyse(classes);
        Graph<Vertex, Edge> graph = new GraphModel(classes);
        createPNGFile(graph);
        saveInGraphML(graph);
    }

    private static void classAnalyse(List<Class<?>> classes) throws IOException {
        ClassAnalyzer classAnalyzer = new ClassAnalyzer(classes)
                .file(Path.of("./src/main/resources/class_analysis.txt"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)
                .printInConsole(false);

        classAnalyzer.analyse();
    }

    private static void saveInGraphML(Graph<Vertex, Edge> graph) throws IOException {
        GraphMLExporter<Vertex, Edge> exporter = new GraphMLExporter<>();
        OutputStream stream = Files.newOutputStream(Path.of("./src/main/resources/lab_6.graph.graphml"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        exporter.exportGraph(graph, stream);
    }

    private static void createPNGFile(Graph<Vertex, Edge> graph) throws IOException {
        File imgFile = new File("./src/main/resources/lab_6.graph.png");
        imgFile.createNewFile();

        JGraphXAdapter<Vertex, Edge> graphAdapter =
                preparedGraphXAdapter(graph);
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image =
                mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        ImageIO.write(image, "PNG", imgFile);
    }

    private static JGraphXAdapter<Vertex, Edge> preparedGraphXAdapter(Graph<Vertex, Edge> graph) {
        JGraphXAdapter<Vertex, Edge> graphAdapter = new JGraphXAdapter<>(graph);
        mxStylesheet stylesheet = graphAdapter.getStylesheet();
        stylesheet.setDefaultVertexStyle(getDefaultVertexStyle());
        stylesheet.setDefaultEdgeStyle(getDefaultEdgeStyle());

        HashMap<Edge, mxICell> edgeToCellMap = graphAdapter.getEdgeToCellMap();
        HashMap<Vertex, mxICell> vertexToCellMap = graphAdapter.getVertexToCellMap();

        Set<Vertex> vertices = graph.vertexSet();
        ColorIterator iterator = new ColorIterator(false);
        for (Vertex vertex : vertices) {
            Set<Edge> edges = graph.outgoingEdgesOf(vertex);
            mxICell mxICellVertex = vertexToCellMap.get(vertex);
            mxICellVertex.setValue(vertex.getVertexClass().getSimpleName());
            mxICellVertex.setStyle("fontSize=18");
            mxGeometry geometry = mxICellVertex.getGeometry();
            geometry.setWidth(80);
            geometry.setHeight(40);
            String hexFormat = getHEXFormat(iterator.next());
            for (Edge edge : edges) {
                mxICell mxICellEdge = edgeToCellMap.get(edge);
                mxICellEdge.setStyle(String.format("strokeColor=%s;fontColor=%s", hexFormat, hexFormat));
                mxICellEdge.setValue(edge.getType());
            }
        }

        return graphAdapter;
    }

    private static String getHEXFormat(Color color) {
        return String.format("#%s", Integer.toHexString(color.getRGB()).substring(2));
    }

    private static Map<String, Object> getDefaultEdgeStyle() {
        Map<String, Object> style = new Hashtable<>();

        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        style.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        style.put(mxConstants.STYLE_HORIZONTAL, mxConstants.ALIGN_BOTTOM);
        style.put(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_BOTTOM);
        style.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
        style.put(mxConstants.STYLE_FONTCOLOR, "#446299");

        return style;
    }

    private static Map<String, Object> getDefaultVertexStyle() {
        Map<String, Object> style = new Hashtable<>();

        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_PERIMETER, mxPerimeter.RectanglePerimeter);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_FILLCOLOR, "#C3D9FF");
        style.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
        style.put(mxConstants.STYLE_FONTCOLOR, "#774400");

        return style;
    }

    private static class ColorIterator implements Iterator<Color> {

        private final Color[] colors = {Color.BLACK, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.RED, Color.ORANGE};
        private final boolean isRandom;
        private int index;

        public ColorIterator() {
            this(false);
        }

        public ColorIterator(boolean isRandom) {
            this.isRandom = isRandom;
            this.index = nextIndex(-1);
        }

        private int nextIndex(int prevIndex) {
            return isRandom ? randomIndex(prevIndex) : (prevIndex + 1) % colors.length;
        }

        private int randomIndex(int exclusiveIndex) {
            int index;
            do {
                index = (int) (Math.random() * colors.length);
            } while (index == exclusiveIndex);

            return index;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Color next() {
            Color color = colors[index];
            index = nextIndex(index);
            return color;
        }

        @Override
        public void forEachRemaining(Consumer<? super Color> action) {
            throw new RuntimeException("It's infinity iterator!");
        }
    }
}
