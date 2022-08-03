package com.depauw.repairshop.database;

public class Repair {

    private int Rid;
    private String Vehicle;
    private String Date;
    private float Cost;
    private String Description;
    private int Vid;

    public Repair(int rid, String vehicle, String date, float cost, String description, int Vid) {
        Rid = rid;
        Vehicle = vehicle;
        Date = date;
        Cost = cost;
        Description = description;
        this.Vid = Vid;
    }

    public Repair(String vehicle, String date, float cost, String description, int Vid) {
        Vehicle = vehicle;
        Date = date;
        Cost = cost;
        Description = description;
        this.Vid = Vid;
    }

    public int getVid() {
        return Vid;
    }

    public int getRid() {
        return Rid;
    }

    public String getVehicle() {
        return Vehicle;
    }

    public String getDate() {
        return Date;
    }

    public float getCost() {
        return Cost;
    }

    public String getDescription() {
        return Description;
    }
}
