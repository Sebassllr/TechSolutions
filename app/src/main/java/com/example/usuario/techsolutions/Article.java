package com.example.usuario.techsolutions;

import java.io.Serializable;

public class Article implements Serializable{

    //////////////////////////////////////
    //Variables///////////////////////////
    //////////////////////////////////////

    public final static String ARTICLE_NODE_NAME = "Article";

    private String title;

    private String idOwner;

    private String content;

    //////////////////////////////////////
    //Contructor//////////////////////////
    //////////////////////////////////////

    public Article() {}

    public Article(String title, String idOwner, String content) {
        this.title = title;
        this.idOwner = idOwner;
        this.content = content;
    }

    //////////////////////////////////////
    //Getters and setters/////////////////
    //////////////////////////////////////

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(String idOwner) {
        this.idOwner = idOwner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
