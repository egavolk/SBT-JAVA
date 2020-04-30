package edu.phystech.sbt.java.reflection.serializer;

import java.lang.reflect.Array;

class WriterXML implements Writer {
    private String spaces = "";

    private void addSpaces() {
        spaces = spaces.concat("    ");
    }

    private void delSpaces() {
        spaces = spaces.substring(4);
    }

    private void addWithBrackets(StringBuilder builder, Object value, boolean open) {
        builder.append('<');
        if (!open) {
            builder.append('/');
        }
        builder.append(value);
        builder.append('>');
    }

    @Override
    public void writeStart(Object o, StringBuilder builder) {
        addWithBrackets(builder, o.getClass().getName(), true);
        addSpaces();
        builder.append("\n");
    }

    @Override
    public void writeEnd(Object o, StringBuilder builder) {
        addWithBrackets(builder, o.getClass().getName(), false);
        addSpaces();
        builder.append("\n");
    }

    @Override
    public void writeObjectStart(Object o, StringBuilder builder, String name) {
        builder.append(spaces);
        addWithBrackets(builder, name, true);
        builder.append("\n");
        addSpaces();
    }

    @Override
    public void writeObjectEnd(Object o, StringBuilder builder, String name) {
        delSpaces();
        builder.append(spaces);
        addWithBrackets(builder, name, false);
        builder.append("\n");
    }

    @Override
    public void writeSingle(Object o, StringBuilder builder, String name) {
        builder.append(spaces);
        addWithBrackets(builder, name, true);
        builder.append(o);
        addWithBrackets(builder, name, false);
        builder.append("\n");
    }

    private void addElementOfArrayOrIterable(Object value, StringBuilder builder, int i) {
        builder.append(spaces);
        addWithBrackets(builder, i, true);
        builder.append(value);
        addWithBrackets(builder, i, false);
        builder.append("\n");
    }

    @Override
    public void writeArrayOrIterable(Object o, StringBuilder builder, String name) {
        builder.append(spaces);
        addWithBrackets(builder, name, true);
        builder.append("\n");
        addSpaces();
        if (Iterable.class.isAssignableFrom(o.getClass())) {
            int j = 1;
            for (Object value : (Iterable) o) {
                addElementOfArrayOrIterable(value, builder, j++);
            }
        } else if (o.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(o); ++i) {
                addElementOfArrayOrIterable(Array.get(o, i), builder, i + 1);
            }
        }
        delSpaces();
        builder.append(spaces);
        addWithBrackets(builder, name, false);
        builder.append('\n');
    }
}
