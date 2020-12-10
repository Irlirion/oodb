package com.company.lab6.graph;

import com.company.lab6.annotations.ReflectionUtils;
import com.company.lab6.annotations.Relation;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.lang.reflect.Field;
import java.util.*;

public class GraphModel extends DefaultDirectedGraph<Vertex, Edge> {

    private Set<Field> fields;

    public GraphModel(Collection<Class<?>> classes) {
        this(Edge.class);
        this.fields = new HashSet<>();
        fillGraph(classes);
    }

    public GraphModel(Class<? extends Edge> edgeClass) {
        super(edgeClass);
    }

    private void fillGraph(Collection<Class<?>> classes) {
        for (Class<?> searchClass : classes) {
            Vertex v = new Vertex(searchClass);
            addVertex(v);
            VertexFactory.add(v);
            fillMap(searchClass);
        }

        fillEdges();
    }

    private void fillMap(Class<?> searchClass) {
        for (Field field : searchClass.getDeclaredFields()) {
            if (ReflectionUtils.hasAnnotation(field, Relation.class)) {
                fields.add(field);
            }
        }
    }

    private void fillEdges() {
        for (Field field : fields) {
            Relation annotation = field.getAnnotation(Relation.class);
            Class<?> targetClass = annotation.target();
            Class<?> fieldTargetClass = (targetClass == Object.class) ?
                    field.getType() : targetClass;
            addEdge(VertexFactory.get(field.getDeclaringClass()), VertexFactory.get(fieldTargetClass), new Edge(annotation.type()));
        }
    }

    private static class VertexFactory {
        private final static Map<Class<?>, Vertex> verticesMap;

        static {
            verticesMap = new HashMap<>();
        }

        public static Vertex add(Vertex vertex) {
            return verticesMap.put(vertex.getVertexClass(), vertex);
        }

        public static Vertex get(Class<?> vertexClass) {
            return getOrDefault(vertexClass, null);
        }

        public static Vertex getOrDefault(Class<?> vertexClass, Vertex defaultVertex) {
            return verticesMap.getOrDefault(vertexClass, defaultVertex);
        }

    }
}