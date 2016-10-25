import java.util.Scanner;

/**
 * CECS 343 Project - Part 2 
 * Spring 2016
 * @author Holly Dinh & Jordan Le
 */
public class Menu {
	public static void main(String a[]) throws Exception {
		System.out.println("Welcome!\n");
		int choice = 0;
		Create_Repo createR = new Create_Repo();
		CheckIn ci = new CheckIn();
		CheckOut co = new CheckOut();
		Merge m = new Merge();
		// Forces user to create a repo everytime they start the program
		createR.createRepo();
		do {
			// Displays the menu
			System.out.println("Menu:\n" + 
													"1. Check in.\n" + 
													"2. Check out.\n" + 
													"3. Merge.\n" + 
													"4. Exit.");
			choice = userSelection();
			switch (choice) {
			case 1:
			ci.checkIn();
			// Checking in
			break;
			case 2:
			co.checkOut();
			// Checking out
			break;
			case 3:
			m.merge();
			// merge
			break;
			case 4:
			// Exiting the program
			System.out.println("\nGoodbye!");
			break;
			}
			System.out.println("---------------------------");
		} while (choice != 4); // Exits the program
	}

	public static int userSelection() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		boolean valid = false;
		int n = 0;
		System.out.print("\nMake a selection:  ");

		// user choice, input validation
		while (!valid) {
			while (true) {
			String user = input.next();
			try {
				// checks for right variable type
				n = Integer.parseInt(user);
				break;
			} catch (NumberFormatException ne) {
				System.out.println("Invalid input. Please try again.");
			}
			}
			if (n > 0 && n <= 4)
			// checks for a valid menu choice
			valid = true;
			else
			System.out.println("Invalid input. Please try again.");
		}
		return n;
	}
}
