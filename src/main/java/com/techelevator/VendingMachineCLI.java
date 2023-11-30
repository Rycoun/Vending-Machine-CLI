package com.techelevator;

import com.techelevator.view.CashBox;
import com.techelevator.view.Menu;
import com.techelevator.view.VendingItem;
import com.techelevator.view.VendingMachine;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};

	private static final String SUBMENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String SUBMENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String SUBMENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] SUBMENU_OPTIONS = {SUBMENU_OPTION_FEED_MONEY, SUBMENU_OPTION_SELECT_PRODUCT, SUBMENU_OPTION_FINISH_TRANSACTION};

	private VendingMachine vm = null;
	private Menu menu;
	private CashBox cash = new CashBox(0.0);
	private final DecimalFormat currency = new DecimalFormat("$#,##0.00");

	private String choice = "";
	private String inventoryStatus = "";

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		vm = new VendingMachine();
		vm.createInventoryFromFile();
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				displayInventory();

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				runPurchaseMenu();

			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.out.println(System.lineSeparator() + "*** Thanks for stopping by. ***" + System.lineSeparator());
				System.exit(0);
			}
			else if (choice.equals(SUBMENU_OPTION_FINISH_TRANSACTION)) {
				runFinishMenu();
			}
		}
	}
	private void displayInventory() {
		for (String location : vm.getInventory().keySet()) {
			VendingItem item = vm.getInventory().get(location);

			if (item.getHowMany() == 0) {
				inventoryStatus = "** SOLD OUT ** ";
				System.out.println(inventoryStatus + item.getNumLocation() + " // " + item.getName() + " // " + currency.format(item.getPrice()) + " // Qty left: " + item.getHowMany());
			} else {
				System.out.println(item.getNumLocation() + " // " + item.getName() + " // " + currency.format(item.getPrice()) + " // Qty left: " + item.getHowMany());
			}
		}
	}
	public void runPurchaseMenu() {
		while (true) {

			System.out.println("Current Money Provided: " + currency.format(cash.getCurrentBalance()));

			choice = (String) menu.getChoiceFromOptions(SUBMENU_OPTIONS);
			if (choice.equals(SUBMENU_OPTION_FEED_MONEY)) {
				receiveFundsFromUser();
			} else if (choice.equals(SUBMENU_OPTION_SELECT_PRODUCT)) {
				String userSelection = getUserItemSelection();
				try {
					VendingItem itemPurchase = vm.getInventory().get(userSelection);
					if (itemPurchase.getPrice() > cash.getCurrentBalance()) {
						System.out.println(System.lineSeparator() + "*** You don't have enough money. ***" + System.lineSeparator());
					} else if (itemPurchase.getHowMany() == 0) {
						System.out.println(System.lineSeparator() + "*** That item is sold out. ***" + System.lineSeparator());
					} else if (vm.getInventory().containsKey(userSelection) && vm.getInventory().get(userSelection).getHowMany() > 0) {
						processPurchase(userSelection, itemPurchase);
					}
				} catch (NullPointerException e) {
					System.out.println(System.lineSeparator() + "*** " + userSelection + " is not a valid selection. ***" + System.lineSeparator());
				}
			} else if (choice.equals(SUBMENU_OPTION_FINISH_TRANSACTION)) {
				runFinishMenu();
				break;
			}
		}
	}
	public String printMessageByType (VendingItem itemPurchase) {
		String message = "";
		switch (itemPurchase.getType()) {
			case "Chip":
				message = "Crunch Crunch, Yum!";
				break;
			case "Candy":
				message = "Munch, Munch, Yum!";
				break;
			case "Drink":
				message = "Glug Glug, Yum!";
				break;
			case "Gum":
				message = "Chew Chew, Yum!";
				break;
		}
		return message;
	}
	public void receiveFundsFromUser(){Scanner scanner = new Scanner(System.in);
		String transactionType = "FEED MONEY";
		System.out.println("How much would you like to deposit?");
		String amountToDeposit = (scanner.nextLine());

		try {
			int amount = Integer.parseInt(amountToDeposit);
			if(amount >= 0) {
				cash.depositFunds(amount);
				logPurchase(transactionType, amount, cash.getCurrentBalance());
			} else {
				System.out.println(System.lineSeparator() + "*** Please enter a positive integer. ***" + System.lineSeparator());
			}
		} catch (NumberFormatException e) {
			System.out.println(System.lineSeparator() + "*** " + amountToDeposit + " is not a valid option. Please enter a positive integer. ***" + (System.lineSeparator()));
		}
	}
	public String getUserItemSelection(){
		displayInventory();
		System.out.println("Please enter the product location: ");
		Scanner scanner = new Scanner(System.in);
		return scanner.next().toUpperCase();
	}
	public void processPurchase(String userSelection, VendingItem itemPurchase) {
		cash.subtractFunds(vm.getInventory().get(userSelection).getPrice());
		itemPurchase.setHowMany(itemPurchase.getHowMany() - 1);

		System.out.println(System.lineSeparator());
		System.out.println("Purchased: " + itemPurchase.getName() + " \nCost: " + currency.format(itemPurchase.getPrice()) + "\nMoney Remaining: " + currency.format(cash.getCurrentBalance()));
		System.out.println(printMessageByType(itemPurchase));
		System.out.println(System.lineSeparator());

		String transactionType = itemPurchase.getType() + " " + itemPurchase.getNumLocation();
		logPurchase(transactionType, itemPurchase.getPrice(), cash.getCurrentBalance());

	}
	public void runFinishMenu() {
		double startingBalance = cash.getCurrentBalance();
		String transactionType = "GIVE CHANGE";
		int changeInCent = (int) (cash.getCurrentBalance() * 100);
		System.out.println(System.lineSeparator() + "*** Thank you for using my vending machine. ***" + System.lineSeparator());

		int quarters = changeInCent / 25;
		changeInCent %= 25;
		int dime = changeInCent / 10;
		changeInCent %= 10;
		int nickels = changeInCent / 5;

		if (quarters > 0 || dime > 0 || nickels > 0) {

			System.out.println("Your change back is given as: ");
			if (quarters > 0){
				System.out.println(quarters + " Quarters");
			}
			if (dime > 0 ){
				System.out.println(dime + " Dimes");
			}
			if (nickels > 0) {
				System.out.println(nickels + " Nickles");
			}
			cash.returnChange();
		} else {
			System.out.println(System.lineSeparator() + "Your balance is zero and you have no change. Have a good day." + System.lineSeparator());
		}

		logPurchase(transactionType, startingBalance, cash.getCurrentBalance());
	}
	public void logPurchase(String transactionType, double amountDifference, double newBalance) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
		String formattedDate = LocalDateTime.now().format(formatter);

		File outputFile = new File("Log.txt");
		try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile, true);
			 PrintWriter printWriter = new PrintWriter(fileOutputStream)) {

			printWriter.println(formattedDate + " " + transactionType + ": " + currency.format(amountDifference) + " " + currency.format(newBalance));

		} catch (IOException e) {

		}
	}
}



