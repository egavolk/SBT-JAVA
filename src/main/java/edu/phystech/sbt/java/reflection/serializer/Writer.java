package edu.phystech.sbt.java.reflection.serializer;

public interface Writer {
    void writeStart(Object o, StringBuilder builder);

    void writeEnd(Object o, StringBuilder builder);

    void writeObjectStart(Object o, StringBuilder builder, String name);

    void writeObjectEnd(Object o, StringBuilder builder, String name);

    void writeSingle(Object o, StringBuilder builder, String name);

    void writeArrayOrIterable(Object o, StringBuilder builder, String name);
}
