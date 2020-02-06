package com.bitspilani.apogeear.Models;

public class Rank {

    private String username;
    private double coins;

    public Rank(String username, double coins) {
        this.username = username;
        this.coins = coins;
    }

    public String getUsername() {
        return username;
    }

    public double getCoins() {
        return coins;
    }
}
