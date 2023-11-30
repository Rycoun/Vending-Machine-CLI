package com.techelevator.view;

import com.techelevator.VendingMachineCLI;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.security.auth.callback.CallbackHandler;
import java.io.*;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;
public class VendingItemTest {

    @Test
    public void test_create_item() {
        VendingItem item = new VendingItem("A1", "Chips", 1.50, "Chip", 5);
        assertEquals("A1", item.getNumLocation());
        assertEquals("Chips", item.getName());
        assertEquals(1.50, item.getPrice(), 0.01);
        assertEquals("Chip", item.getType());
        assertEquals(5, item.getHowMany());
    }

    @Test
    public void test_remove_how_many() {
        VendingItem item = new VendingItem("A2", "Candy Bar", 1.25, "Candy", 3);
        assertEquals(3, item.getHowMany());
        item.removeHowMany();

        assertEquals(2, item.getHowMany());
        item.removeHowMany();
        item.removeHowMany();

        assertEquals(0, item.getHowMany());

        assertEquals(0, item.getHowMany());
        assertEquals(0, item.getHowMany());
    }
    @Test
    public void test_set_how_many() {
        VendingItem item = new VendingItem("A2", "Candy Bar", 1.25, "Candy", 0);
        assertEquals(0, item.getHowMany());
        item.setHowMany(3);

        assertEquals(3, item.getHowMany());

        item.setHowMany(1);
        assertEquals(1, item.getHowMany());

        item.setHowMany(0);
        assertEquals(0, item.getHowMany());


    }



}


