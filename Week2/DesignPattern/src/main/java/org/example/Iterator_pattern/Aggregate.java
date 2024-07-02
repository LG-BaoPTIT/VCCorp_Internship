package org.example.Iterator_pattern;

interface Aggregate<T> {
    Iterator<T> createIterator();
}