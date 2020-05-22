package edu.phystech.sbt.java.reflection.serializer;

import java.lang.reflect.Field;
import java.util.Arrays;

class SerializerSlave {
    Writer writer;

    SerializerSlave(Writer writer) {
        this.writer = writer;
    }

    void serialize(Object o, StringBuilder builder) {
        writer.writeStart(o, builder);
        serializeFields(o, builder);
        writer.writeEnd(o, builder);
    }

    void serializeFields(Object o, StringBuilder builder) {
        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object object = field.get(o);
                if (object.getClass().isPrimitive() || object instanceof String) {
                    writer.writeSingle(object, builder, field.getName());
                } else if (Iterable.class.isAssignableFrom(object.getClass()) || object.getClass().isArray()) {
                    writer.writeArrayOrIterable(object, builder, field.getName());
                }
                else {
                    writer.writeObjectStart(object, builder, field.getName());
                    serializeFields(object, builder);
                    writer.writeObjectEnd(object, builder, field.getName());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e.toString());
            }
        }
    }
}
