package com.techelevator.view;

import java.io.*;
import java.util.*;

public class VendingMachine {
    private SortedMap<String, VendingItem> inventory = new TreeMap<>();
    private double currentBalance;
    private BufferedWriter currentLog;

    public VendingMachine() {
        this.inventory = inventory;
        this.currentBalance = 0;

        try {
            currentLog = new BufferedWriter(new FileWriter("Log.txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public SortedMap<String, VendingItem> createInventoryFromFile() {
        try (Scanner scanner = new Scanner(new File("vendingmachine.csv"))) {
            while (scanner.hasNextLine()) {
                String itemLine = scanner.nextLine();
                String[] itemList = itemLine.split("\\|");

                String location = itemList[0];
                String name = itemList[1];
                double price = Double.parseDouble(itemList[2]);
                String type = itemList[3];
                int quantity = 5;

                VendingItem item = new VendingItem(location, name, price, type, quantity);
                inventory.put(location, item);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public SortedMap<String, VendingItem> getInventory() {
        return inventory;
    }
}
