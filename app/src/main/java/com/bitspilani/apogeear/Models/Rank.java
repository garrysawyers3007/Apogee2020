package com.bitspilani.apogeear.Models;

public class Rank {

    private String username,userId,charName;
    private double coins;
    private int rank;

    public Rank(String username, double coins,String userId,String charName) {
        this.username = username;
        this.coins = coins;
        this.userId=userId;
        this.charName=charName;
    }

    public String getUsername() {
        return username;
    }

    public double getCoins() {
        return coins;
    }

    public String getUserId() {
        return userId;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

}
