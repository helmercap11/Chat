package com.helmercapassola.chats;

public class Message {


    private  String id;
    private  String text;
    private String name;
    private String photo;
    private  String imgage;


    public Message (){

    }

    public Message(String text, String name, String photo, String imgage) {
        this.text = text;
        this.name = name;
        this.photo = photo;
        this.imgage = imgage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getImgage() {
        return imgage;
    }

    public void setImgage(String imgage) {
        this.imgage = imgage;
    }
}
