package com.example.iago.myapplicationtest;

public class Pokemon {

    private int id;
    private String nome;

    public Pokemon(){}

    public Pokemon(String nome) {
        super();
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
