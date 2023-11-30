package com.techelevator.view;

import com.techelevator.VendingMachineCLI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import javax.security.auth.callback.CallbackHandler;
import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineCLITest {

    private VendingMachineCLI vendingMachineCLI;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;


    @Before
    public void setUp() {
        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        vendingMachineCLI = new VendingMachineCLI(new Menu(in, System.out));
    }


    @Test
    public void test_print_message_by_type() {

        VendingItem chip = new VendingItem("A1", "Chips", 1.50, "Chip", 5);
        String message = vendingMachineCLI.printMessageByType(chip);

        assertEquals("Crunch Crunch, Yum!", message);


    }

}






