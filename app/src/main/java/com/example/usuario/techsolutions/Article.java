package com.example.usuario.techsolutions;

public class Article {

    //////////////////////////////////////
    //Variables///////////////////////////
    //////////////////////////////////////

    private String name;

    private String autor;

    private String title;

    //////////////////////////////////////
    //Contructor//////////////////////////
    //////////////////////////////////////

    public Article(String name, String autor, String title) {
        this.name = name;
        this.autor = autor;
        this.title = title;
    }

    //////////////////////////////////////
    //Getters and setters/////////////////
    //////////////////////////////////////

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
