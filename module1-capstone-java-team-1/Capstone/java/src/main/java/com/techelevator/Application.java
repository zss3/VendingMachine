package com.techelevator;


import com.techelevator.view.MenuDrivenCLI;
import com.techelevator.vendingmachine.VendingMachine;

import java.io.IOException;

public class Application {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_SALES_REPORT = "";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_SALES_REPORT};

	private static final String PURCHASE_MENU_OPTION_DEPOSIT_MONEY = "Feed money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";

	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_DEPOSIT_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};
	private static final String INVENTORY = "inventory.txt";

	private final MenuDrivenCLI ui = new MenuDrivenCLI();

	public static void main(String[] args) {
		Application application = new Application();
		application.run();
	}

	public void run() {

		try {
			VendingMachine vendingMachine = new VendingMachine(INVENTORY);
			while (true) {
				String selection = ui.promptForSelection(MAIN_MENU_OPTIONS);
				if (selection.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
					displayVendingMachineItems(vendingMachine);
				} else if (selection.equals(MAIN_MENU_OPTION_PURCHASE)) {
					handlePurchaseMenu(vendingMachine);
				} else if (selection.equals(MAIN_MENU_OPTION_EXIT)) {
					break;
				} else if (selection.equals(MAIN_MENU_OPTION_SALES_REPORT)) {
					vendingMachine.generateSalesReport();
					System.out.println("\nYour sales report has been successfully generated.");
				}
			}
		} catch (IOException e) {
			System.out.printf("Unable to open inventory file: %s%n", INVENTORY);
		}
	}

	public void displayVendingMachineItems(VendingMachine vendingMachine) {
		String items = vendingMachine.displayItems();
		System.out.println(items);
	}

	public void handlePurchaseMenu(VendingMachine vendingMachine) {
		boolean finished = false;
		while (!finished) {
			String purchaseSelection = ui.promptForSelection(PURCHASE_MENU_OPTIONS);

			if (purchaseSelection.equals(PURCHASE_MENU_OPTION_DEPOSIT_MONEY)) {
				String depositAmount = ui.promptForInput("\nEnter deposit amount: ");
				try {
					int deposit = Integer.parseInt(depositAmount);
					if(deposit >= 0 ) {
						vendingMachine.depositMoney(deposit);
						System.out.println("\nYour current balance is: $" + vendingMachine.getBalance());
					}
					else {
						throw new NumberFormatException();
					}
				} catch (NumberFormatException i) {
					System.out.println("\nInvalid deposit amount. Enter a positive whole number.");
				}
			} else if (purchaseSelection.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {

				System.out.println(vendingMachine.displayItems());
				System.out.printf("Your balance is %s%n", vendingMachine.getBalance());
				String getSelection = ui.promptForInput("\nEnter product ID number: ");
				String result = vendingMachine.makePurchase(getSelection);
				System.out.printf("%n%s%n", result);
				System.out.println("\nYour current balance is: $" + vendingMachine.getBalance());

			} else if (purchaseSelection.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
				System.out.println("\nYour change is: $" + vendingMachine.getBalance());
				System.out.println(vendingMachine.makeChange());
				finished = true;
			}
		}
	}
}
