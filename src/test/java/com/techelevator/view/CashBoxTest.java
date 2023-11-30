package com.techelevator.view;

import com.techelevator.VendingMachineCLI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.security.auth.callback.CallbackHandler;
import java.io.*;
import java.nio.charset.StandardCharsets;




public class CashBoxTest {


    @Test
    public void test_subtract_funds() {
        CashBox cashBox = new CashBox(10.0);

        double balance = cashBox.subtractFunds(3.5);

        assertEquals(6.5, balance, 0.001);

    }

    @Test
    public void test_deposit_funds() {
        CashBox cashBox = new CashBox(0);

        double balance = cashBox.depositFunds(5);

        assertEquals(5, balance, 0.001);


    }

    @Test
    public void test_return_funds() {
        CashBox cashBox = new CashBox(7.25);

        cashBox.returnChange();

        assertEquals(0.0, cashBox.getCurrentBalance(), 0.001);

    }
}