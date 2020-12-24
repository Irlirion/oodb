package com.company.lab8;

public interface Entity<T extends Number> {
    T getId();

    void setId(T id);
}
