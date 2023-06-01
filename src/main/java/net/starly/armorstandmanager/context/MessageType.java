package net.starly.armorstandmanager.context;

import lombok.Getter;

@Getter
public enum MessageType {

    NORMAL("messages"),
    ERROR("errorMessages");

    public final String name;

    MessageType(String name) { this.name = name; }
}
