package edu.phystech.sbt.java.reflection.serializer;

public class JsonSerializer implements Serializer {
    SerializerSlave slave = new SerializerSlave(new WriterJson());

    @Override
    public String serialize(Object o) {
        StringBuilder builder = new StringBuilder();
        slave.serialize(o, builder);
        return builder.toString();
    }
}
