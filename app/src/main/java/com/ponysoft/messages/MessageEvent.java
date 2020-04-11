package com.ponysoft.messages;

public class MessageEvent {

    public enum MESSAGETYPE {
        MESSAGETYPE_REFRESH_SAVED_UI,
        MESSAGETYPE_REFRESH_LIST_DATA
    }

    public final String message;
    public final MESSAGETYPE messageType;

    public MessageEvent(String message, MESSAGETYPE messageType) {

        this.message = message;
        this.messageType = messageType;
    }
}
