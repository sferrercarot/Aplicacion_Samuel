package com.example.aplicacion_samuel;

public class Jugador {
    private int id;
    private String name;
    private String team;
    private int age;
    private String nationality;
    private double marketValue;
    private String position;
    private String image;

    public Jugador(){}

    public Jugador(int id, String name, String team, int age, String nationality, double marketValue, String position, String image) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.age = age;
        this.nationality = nationality;
        this.marketValue = marketValue;
        this.position = position;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(double marketValue) {
        this.marketValue = marketValue;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
