package com.techelevator.vendingmachine;

import com.techelevator.Log.SalesReport;
import com.techelevator.Log.VendingLog;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine {

    private Map<String, ItemSlot> vendingMachineItems = new LinkedHashMap<>();
    private BigDecimal balance = new BigDecimal("0.00");


    public VendingMachine(String inventoryFileToLoad) throws IOException{

        Path myPath = Path.of(inventoryFileToLoad);  // Path to file

        try(Scanner fileScanner = new Scanner(myPath)) {
            while(fileScanner.hasNextLine()) {
                String fileLine = fileScanner.nextLine();
                String[] item = fileLine.split("\\|");
                String itemSlot = item[0];
                BigDecimal price = new BigDecimal(item[2]);
                String name = item[1];
                ItemTypes type = ItemTypes.valueOf(item[3].toUpperCase());
                vendingMachineItems.put( itemSlot, new ItemSlot( price ,name , type ) );
            }
        }
    }


    public String displayItems() {
        String result = "\n";
        for (Map.Entry<String, ItemSlot> item : vendingMachineItems.entrySet()) {
            if (item.getValue().getCount() == 0) {
                result += "SOLD OUT ";
            }
            result += item.getValue().getName() + " " + item.getKey() + " " + item.getValue().getPrice() + "\n";
        }
        return result;
    }

    public BigDecimal depositMoney(int depositAmount) {
        balance = balance.add(BigDecimal.valueOf(depositAmount));

        VendingLog.log("FEED MONEY: $" + BigDecimal.valueOf(depositAmount)  + " $" + balance);

        return balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getItemCount(String slotNumber) {
        if (!vendingMachineItems.containsKey(slotNumber)){///Similar to String.indexOf(x), returns -1 if invalid slot.
            return -1;
        }
        ItemSlot item = vendingMachineItems.get(slotNumber);
        return item.getCount();
    }


    public String makePurchase(String slotNumber) {
        slotNumber = slotNumber.toUpperCase();

        String result = "No such item slot";

        if (vendingMachineItems.containsKey(slotNumber)) {

            ItemSlot item = vendingMachineItems.get(slotNumber);

            if (item.getCount() == 0) {
                result = "Transaction failed. This item is sold out.";
            } else if (balance.compareTo(item.getPrice()) < 0) {
                result = "Transaction failed. Balance is less than item price.";
            } else  {
                balance = balance.subtract(item.getPrice());
                item.setCount(item.getCount() - 1);
                result = item.getType().toString();
                VendingLog.log(item.getName() + " " + slotNumber + " $" + item.getPrice() + " $" + balance);
            }
        }
        return result;

    }

    public String makeChange(){
        int quarters = 0;
        int nickels = 0;
        int dimes = 0;
        BigDecimal oldBalance = balance;
        Map <String, Integer> change = new LinkedHashMap<>();

        while (this.getBalance().compareTo(BigDecimal.ZERO) > 0){
            if (this.getBalance().compareTo(BigDecimal.valueOf(.25)) >= 0){
                quarters++;
                balance = balance.subtract(BigDecimal.valueOf(.25));
            }
            else if (this.getBalance().compareTo(BigDecimal.valueOf(.10)) >= 0){
                dimes++;
                balance = balance.subtract(BigDecimal.valueOf(.10));
            }
            else if (this.getBalance().compareTo(BigDecimal.valueOf(.05)) >= 0){
                nickels++;
                balance = balance.subtract(BigDecimal.valueOf(.05));
            }
        }
        change.put("Quarters", quarters);
        change.put("Dimes", dimes);
        change.put("Nickels", nickels);

        String result = "";

        VendingLog.log("GIVE CHANGE: $" + oldBalance + " $" + balance);

        for (Map.Entry<String, Integer> c : change.entrySet()) {
            if(c.getValue()>0)
                result += c.getKey() + ": " + c.getValue() + '\n';
        }

        return result;
    }


    public void generateSalesReport(){
        SalesReport.delete();
        BigDecimal totalSales = BigDecimal.ZERO;

        for(Map.Entry<String, ItemSlot> i : vendingMachineItems.entrySet()){
            SalesReport.log(i.getValue().getName() + "|" + Integer.toString(ItemSlot.STARTING_ITEM_COUNT - i.getValue().getCount()));
            BigDecimal itemSales = i.getValue().getPrice();
            itemSales = itemSales.multiply(BigDecimal.valueOf(ItemSlot.STARTING_ITEM_COUNT - i.getValue().getCount()));
            totalSales = totalSales.add(itemSales);
        }

        SalesReport.log("\n**TOTAL SALES** $" + totalSales.toString());

    }

}
