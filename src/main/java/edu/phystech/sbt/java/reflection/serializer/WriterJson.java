package edu.phystech.sbt.java.reflection.serializer;

import java.lang.reflect.Array;

class WriterJson implements Writer {
    private String spaces = "";

    private void addSpaces() {
        spaces = spaces.concat("    ");
    }

    private void delSpaces() {
        spaces = spaces.substring(4);
    }

    private void appendWithBrackets(StringBuilder builder, Object str) {
        builder.append('\"');
        builder.append(str);
        builder.append('\"');
    }

    @Override
    public void writeStart(Object o, StringBuilder builder) {
        builder.append("{\n");
        addSpaces();
    }

    @Override
    public void writeEnd(Object o, StringBuilder builder) {
        builder.append("}\n");
        addSpaces();
    }

    @Override
    public void writeObjectStart(Object o, StringBuilder builder, String name) {
        builder.append(spaces);
        appendWithBrackets(builder, name);
        builder.append(": {\n");
        addSpaces();
    }

    @Override
    public void writeObjectEnd(Object o, StringBuilder builder, String name) {
        delSpaces();
        builder.append(spaces);
        builder.append("},\n");
    }

    @Override
    public void writeSingle(Object o, StringBuilder builder, String name) {
        builder.append(spaces);
        appendWithBrackets(builder, name);
        builder.append(": ");
        appendWithBrackets(builder, o);
        builder.append(",\n");
    }

    private void addElementOfArrayOrIterable(Object value, StringBuilder builder) {
        builder.append(spaces);
        appendWithBrackets(builder, value);
        builder.append(",\n");
    }

    @Override
    public void writeArrayOrIterable(Object o, StringBuilder builder, String name) {
        builder.append(spaces);
        appendWithBrackets(builder, name);
        builder.append(": [\n");
        addSpaces();
        if (Iterable.class.isAssignableFrom(o.getClass())) {
            for (Object value : (Iterable) o) {
                addElementOfArrayOrIterable(value, builder);
            }
        } else if (o.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(o); ++i) {
                addElementOfArrayOrIterable(Array.get(o, i), builder);
            }
        }
        delSpaces();
        builder.append(spaces);
        builder.append("],\n");
    }
}
