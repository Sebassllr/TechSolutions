package com.example.usuario.techsolutions;

public class Article{

    //////////////////////////////////////
    //Variables///////////////////////////
    //////////////////////////////////////

    public final static String ARTICLE_NODE_NAME = "Article";

    private String autor;

    private String title;

    private String idOwner;

    private String content;
    //////////////////////////////////////
    //Contructor//////////////////////////
    //////////////////////////////////////

    public Article(String autor, String title, String idOwner, String content) {
        this.autor = autor;
        this.title = title;
        this.idOwner = idOwner;
        this.content = content;
    }

    //////////////////////////////////////
    //Getters and setters/////////////////
    //////////////////////////////////////

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

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
}
