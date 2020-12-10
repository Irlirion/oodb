package com.company.lab6.graph;

import com.company.lab6.annotations.RelationType;
import org.jgrapht.graph.DefaultEdge;

import java.util.Objects;

public class Edge extends DefaultEdge {

    private RelationType type;

    public Edge(RelationType type) {
        this.type = type;
    }

    public RelationType getType() {
        return type;
    }

    public void setType(RelationType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return type == edge.type && Objects.equals(edge.getSource(), getSource())
                && Objects.equals(getTarget(), edge.getTarget());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, getSource(), getTarget());
    }

    @Override
    public String toString() {
        return String.format(String.format("(%s : %s, relation type = %s)", getSource(), getTarget(), type));
    }

}
