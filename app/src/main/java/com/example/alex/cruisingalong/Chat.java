package com.example.alex.cruisingalong;

public class Chat {
    private String message;
    private String author;

    // Required default constructor for Firebase object mapping
    private Chat() {
    }

    Chat(String message, String author) {
        this.message = message;
        this.author = author;
    }



    public String getMessage() {

        return message;
    }

    public String getAuthor() {

        return author;
    }
}