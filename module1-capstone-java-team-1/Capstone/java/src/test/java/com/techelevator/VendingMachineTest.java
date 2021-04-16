package com.techelevator;

import com.techelevator.vendingmachine.VendingMachine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

public class VendingMachineTest {
    VendingMachine sut;
    @Before
    public void setup_vending_machine_for_tests() {
        try {
            sut = new VendingMachine("inventory.txt");
        }
        catch (IOException e)
        {
            Assert.assertEquals("Unable to open file",0, 1);
        }
    }



    @Test
    public void displayItems_correctly_displays_the_items() {
        String expected = "\n" +
                "Potato Crisps A1 3.05\n" +
                "Stackers A2 1.45\n" +
                "Grain Waves A3 2.75\n" +
                "Cloud Popcorn A4 3.65\n" +
                "Moonpie B1 1.80\n" +
                "Cowtales B2 1.50\n" +
                "Wonka Bar B3 1.50\n" +
                "Crunchie B4 1.75\n" +
                "Cola C1 1.25\n" +
                "Dr. Salt C2 1.50\n" +
                "Mountain Melter C3 1.50\n" +
                "Heavy C4 1.50\n" +
                "U-Chews D1 0.85\n" +
                "Little League Chew D2 0.95\n" +
                "Chiclets D3 0.75\n" +
                "Triplemint D4 0.75\n";
        Assert.assertEquals(expected, sut.displayItems());

    }

    @Test
    public void depositMoney_adds_money_to_the_balance() {

        BigDecimal result = sut.depositMoney(5);
        Assert.assertEquals(BigDecimal.valueOf(5.0), result);
    }

    @Test
    public void getBalance_returns_the_correct_balance() {

        sut.depositMoney(5);
        sut.depositMoney(6);
        sut.depositMoney(2);
        Assert.assertEquals(BigDecimal.valueOf(5.0+6.0+2.0), sut.getBalance());

    }

    @Test
    public void makePurchase_makes_the_correct_purchase() {

        sut.depositMoney(50);
        String result = "Crunch Crunch, Yum!";
        Assert.assertEquals(result, sut.makePurchase("A4"));
        Assert.assertEquals(4, sut.getItemCount("A4"));

    }

    @Test
    public void makeChange_makes_the_correct_change() {
        sut.depositMoney(10);
        sut.makePurchase("D3");
        sut.makePurchase("B2");
        sut.makePurchase("C1");
        sut.makePurchase("D1");
        String result = sut.makeChange();
        Assert.assertEquals("Nickels: 1\nDimes: 1\nQuarters: 22\n",result);
    }
}
