package net.starly.armorstandmanager.context;

public enum MessageType {

    NORMAL("messages"),
    ERROR("errorMessages");

    public final String name;

    MessageType(String name) { this.name = name; }
}
