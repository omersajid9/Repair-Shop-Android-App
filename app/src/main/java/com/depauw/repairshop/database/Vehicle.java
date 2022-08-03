package com.depauw.repairshop.database;

public class Vehicle {

    private int Vid;
    private String Year;
    private String Model;
    private float Price;
    private boolean New;

    public Vehicle(int vid, String year, String model, float price, boolean aNew) {
        Vid = vid;
        Year = year;
        Model = model;
        Price = price;
        New = aNew;
    }

    public Vehicle(String year, String model, float price, boolean aNew) {
        Year = year;
        Model = model;
        Price = price;
        New = aNew;
    }

    public String toString()
    {
        return Year + " " + Model;
    }

    public int getVid() {
        return Vid;
    }

    public String getYear() {
        return Year;
    }

    public String getModel() {
        return Model;
    }

    public float getPrice() {
        return Price;
    }

    public boolean isNew() {
        return New;
    }
}
