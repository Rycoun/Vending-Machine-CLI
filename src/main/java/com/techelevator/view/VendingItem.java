package com.techelevator.view;

public class VendingItem {

    private String numLocation;
    private double price;
    private String type;
    private int howMany;
    private String name;


    public VendingItem(String location, String name, double price, String type, int howMany) {

        this.numLocation = location;
        this.name = name;
        this.price = price;
        this.type= type;
        this.howMany = howMany;
    }



    public void removeHowMany() {
        if (howMany > 0) {
            howMany--;
        }
    }

    public String getNumLocation() {
        return numLocation;
    }

    public double getPrice() {
        return price;
    }

    public int getHowMany() {
        return howMany;
    }

    public String getName() {
        return name;
    }

    public void setHowMany(int value)
    {
        howMany = value;
    }

    public String getType() {
        return type;
    }
}