package com.example.usuario.techsolutions;

public class Usuario {

    public final static String USER_NODE_NAME = "Users";

    private String id;
    private String mail;

    public Usuario(String id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
