package com.techelevator.view;

import org.junit.Test;

import java.util.SortedMap;

import com.techelevator.VendingMachineCLI;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.security.auth.callback.CallbackHandler;
import java.io.*;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {

    @Test
    public void test_create_inventory_from_file() {
        VendingMachine vendingMachine = new VendingMachine();
        SortedMap<String, VendingItem> inventory = vendingMachine.createInventoryFromFile();

      assertNotNull(inventory);
      assertTrue(inventory.containsKey("A1"));
      assertEquals("Potato Crisps", inventory.get("A1").getName());
      assertEquals(3.05, inventory.get("A1").getPrice(), 0.01);
      assertEquals("Chip", inventory.get("A1").getType());
      assertEquals(5,inventory.get("A1").getHowMany());

    }

}
