package com.techelevator.view;

public class CashBox {

    private double currentBalance = 0.00;



    // constructor
    public CashBox (double currentBalance) {
        this.currentBalance = currentBalance;
    }

    // getters & setters
    public double getCurrentBalance() {
        return currentBalance;
    }

    // methods

    public double depositFunds(int depositAmount) {
        double newBalance = currentBalance + depositAmount;
        currentBalance = newBalance;
        return currentBalance;
    }

    public double subtractFunds(double purchaseAmount) {
        double newBalance = currentBalance - purchaseAmount;
        currentBalance = newBalance;
        return currentBalance;
    }

    public void returnChange() {
        currentBalance = 0.0;
    }

}
