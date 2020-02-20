package com.bitspilani.apogeear.Models;

public class Rank {

    private String username,userId;
    private double coins;
    private int rank;

    public Rank(String username, double coins,String userId,int rank) {
        this.username = username;
        this.coins = coins;
        this.userId=userId;
        this.rank=rank;
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

    public int getRank() {
        return rank;
    }
}
