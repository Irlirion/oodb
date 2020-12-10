package com.company.lab6.graph;

import java.util.Objects;

public class VertexAttribute {

    private String attributeType;
    private String attributeName;

    private VertexAttribute() {
    }

    public VertexAttribute(String attributeType, String attributeName) {
        this();
        this.attributeType = attributeType;
        this.attributeName = attributeName;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertexAttribute that = (VertexAttribute) o;
        return Objects.equals(attributeType, that.attributeType) &&
                Objects.equals(attributeName, that.attributeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeType, attributeName);
    }

    @Override
    public String toString() {
        return String.format("%s %s", attributeType, attributeName);
    }
}

