package com.company.lab6.graph;

import com.company.lab6.annotations.Column;
import com.company.lab6.annotations.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Vertex {

    private transient final Set<VertexAttribute> vertexAttributes;
    private Class<?> vertexClass;

    private Vertex() {
        this.vertexAttributes = new HashSet<>();
    }

    public Vertex(Class<?> vertexClass) {
        this();
        this.vertexClass = vertexClass;
        fillVertexAttributesSet();
    }

    private void fillVertexAttributesSet() {
        for (Field field : vertexClass.getDeclaredFields()) {
            if (ReflectionUtils.hasAnnotation(field, Column.class)) {
                vertexAttributes.add(new VertexAttribute(field.getType().getName(), field.getName()));
            }
        }
    }

    public Class<?> getVertexClass() {
        return vertexClass;
    }

    public void setVertexClass(Class<?> vertexClass) {
        this.vertexClass = vertexClass;
    }

    public Set<VertexAttribute> getVertexAttributes() {
        return Collections.unmodifiableSet(vertexAttributes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(vertexClass, vertex.vertexClass);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vertexClass);
    }

    @Override
    public String toString() {
        return String.format("{constraintName: %s, attributes: %s}", vertexClass.getSimpleName(), vertexAttributes);
    }
}
