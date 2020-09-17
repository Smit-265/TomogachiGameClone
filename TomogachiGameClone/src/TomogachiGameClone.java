import java.util.Scanner;
import org.apache.commons.lang3.text.WordUtils;
import java.util.concurrent.ThreadLocalRandom;

class Pet {
	
	String name;
	//Attributes to track playing the game (0=10)
	int hunger = 0;
	int bored = 0;
	int tiredness = 0;
	int dirtiness = 0;
	int food = 3;					//Represents food inventory
	boolean is_sleeping = false;	//Boolean to track if pet is sleeping
	boolean is_alive = true;
	
	public void eat() 
	{
		/* 
		 * Simulate eating. Each time you eat, take one food away from the inventory
		 * and randomly take value away from hunger 
		 */
		//First make sure there is food available
		if (this.food > 0) 
		{
			this.food -= 1;
			this.hunger -= ThreadLocalRandom.current().nextInt(1, 5);
			System.out.println("Yum! " + this.name + " ate a great meal.");
		}
		else
		{
			System.out.println(this.name + " doesn't have any food!  Go and get some food.");
		}
		
		//If the hunger is less than zero set it to zero
		if (this.hunger < 0)
		{
			this.hunger = 0;
		}
	}
	
	public void play() {
		/* 
		 * Play a guessing game to lower the pet's bored.
		 * If you win the game, lower the bored even more
		 */
		int value = ThreadLocalRandom.current().nextInt(0, 3);
		System.out.println("\n" + this.name + " wants to play a game.");
		System.out.println(this.name + " is thinking of number 0, 1 or 2.");
		System.out.print("What is your guess : ");
		Scanner in = new Scanner(System.in);
		int guess = in.nextInt();
		
		//Lower the bored attribute based on the users guess
		if (guess == value)
		{
			this.bored -= 3;
			System.out.println("That is Correct!!!");
		}
		else
		{
			this.bored -= 1;
			System.out.println("Wrong! " + this.name + " was thinking of " + value + ".");
		}
		
		//If the bored is less than zero, set it to zero
		if (this.bored < 0)
		{
			this.bored = 0;
		}
	}
	
	public void sleep() {
		/*
		 * Simulate sleeping. The only thing a player can do when the pet is sleeping is try
		 * to wake up. However tiredness and bored should decrease each round when sleeping
		*/
		//Put the pet to sleep
		this.is_sleeping = true;
		this.tiredness -= 3;
		this.bored -= 2;
		System.out.println("Zzzzzzz.....Zzzzzzz.....Zzzzzzz.....");
		
		//If tiredness or bored is less than zero, set it to zero
		if (this.tiredness < 0)
		{
			this.tiredness = 0;
		}
		if (this.bored < 0)
		{
			this.bored = 0;
		}
	}
	
	public void awake() {
		/*
		 * Simulate randomly waking a pet up
		 */
		//pet has a 1/3 chance to randomly wake up
		int value = ThreadLocalRandom.current().nextInt(0, 3);
		//If pet wakes up, set tiredness to zero
		if (value == 0)
		{
			System.out.println(this.name + " just woke up!");
			this.is_sleeping = false;
			this.tiredness = 0;
		}
		else
		{
			System.out.println(this.name + " won't wake up...");
			sleep();
		}
	}
	
	public void clean() {
		/*
		 * Simulate taking a bath to complete clean the pet
		 */
		this.dirtiness = 0;
		System.out.println(this.name + " has taken bath.  All clean..!");		
	}
	
	public void get_food() {
		/*
		 * Get some food. This will increase the pet's food attribute
		 * However, it will also increase the dirtiness
		 */
		//Randomly find food from 0 to 4 pieces
		int food_found = ThreadLocalRandom.current().nextInt(0, 5);
		this.food += food_found;
		
		//Pet gets dirty
		this.dirtiness += 1;
		System.out.println(this.name + " found " + food_found + " pieces of food!");	
	}
	
	public void show_values() {
		/*
		 * Show the current information about the pet
		 */
		System.out.println("\nPet Name : " + this.name);
		System.out.println("Hunger (0-10) : " + this.hunger);
		System.out.println("Bored (0-10) : " + this.bored);
		System.out.println("Tiredness (0-10) : " + this.tiredness);
		System.out.println("Dirtiness (0-10) : " + this.dirtiness);
		System.out.println("Food Inventory : " + this.food + " pieces.");
		
		//Show current sleeping status
		if (this.is_sleeping)
		{
			System.out.println("Current Status : Sleeping");
		}
		else
		{
			System.out.println("Current Status : Awake");
		}
	}
	
	public void increment_values(int diff) {
		/*
		 * User must set an arbitrary difficulty, This will control how much damage you take
		 * each round. Update the current values of the pet based of this difficulty 
		 */
		//Increase the hunger and dirtiness regardless if the pet is awake of sleeping
		this.hunger += ThreadLocalRandom.current().nextInt(0, diff+1);
		this.dirtiness += ThreadLocalRandom.current().nextInt(0, diff+1);
		
		//If the pet is awake, he should be growing tired and growing bored
		if (this.is_sleeping == false)
		{
			this.bored += ThreadLocalRandom.current().nextInt(0, diff+1);
			this.tiredness += ThreadLocalRandom.current().nextInt(0, diff+1);
		}
	}
	
	public void kill() {
		/*
		 * Check for all conditions to kill or sleep the pet
		 */
		//First two checks will kill the pet
		if (this.hunger >= 10)
		{
			System.out.println("\n" + this.name + " has starved to death...");
			this.is_alive = false;
		}
		else if (this.dirtiness >= 10)
		{
			System.out.println("\n" + this.name + " has suffered an infection and died...");
			this.is_alive = false;
		}
		//Next two checks will put the pet to sleep
		else if (this.bored >= 10)
		{
			this.bored = 10;
			System.out.println("\n" + this.name + " is bored.  Falling asleep...");
			this.is_sleeping = true;
		}
		else if (this.tiredness >= 10)
		{
			this.tiredness = 10;
			System.out.println("\n" + this.name + " is sleepy.  Falling asleep...");
		}
	}
}

public class TomogachiGameClone {

	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		//Set difficulty
		System.out.println("Choose Difficulty: ");
		System.out.println("\t-(1) Easy\n\t-(2) Medium\n\t-(3) Hard\n\t-(4) Expert");
		System.out.print("Choose Difficulty (1),(2),(3),(4) : ");
		int difficulty = scan.nextInt();
		if (difficulty > 4)
		{
			difficulty = 4;
		}
		if (difficulty < 1)
		{
			difficulty = 1;
		}
		
		//The overall main game loop
		boolean running = true;
		while (running)
		{
			//Get user input for pet name
			System.out.print("\nWhat name would you like to give your pet : ");
			String pet_name = WordUtils.capitalizeFully(scan.next());
			Pet player = new Pet();
			player.name = pet_name;
			
			int rounds = 1;
			//The game loop that simulates an individual round
			//This loop should run as long as the pet is alive
			while (player.is_alive)
			{
				System.out.println("\n-----------------------------------------------------------");
				System.out.println("Round #" + rounds);
				
				//An individual round should show values. get a players move, and call the appropriate method
				player.show_values();
				String round_move = show_menu(player);
				call_action(player, round_move);
				
				System.out.print("\n(-----Round #" + rounds + " Summary-----)");
				
				//Summarize the effects of the current round
				player.show_values();
				System.out.print("\nEnter any key to continue : ");
				scan.next();
				
				//Increment values and check for death
				player.increment_values(difficulty);
				player.kill();
					
				//Round is over
				rounds += 1;
			}
			
			//Pet has died. Game over
			System.out.print("\n(-----Last Summary-----)");
			player.show_values();
			System.out.println("\nR.I.P");
			System.out.println(player.name + " has survived a total of " + (rounds-1) + " rounds.");
			
			//Ask the user to play again
			System.out.print("\nWould you like to play again (y/n) : ");
			String choice = scan.next().toLowerCase();
			if (!choice.equals("y"))
			{
				running = false;
				System.out.println("\nGoodbye..Thank You for playing the game!");
			}
		}
	}
	
	public static String show_menu(Pet p)
	{
		/*
		 * Show the menu options for the player. If pet is sleeping, the player can
		 * only try to wake the pet up by default
		 */
		//If pet is sleeping. only allow the user to wake the pet
		Scanner in = new Scanner(System.in);
		String choice;
		if (p.is_sleeping)
		{
			System.out.print("Enter (6) to try and wake up : ");
			choice = in.nextLine();
			choice = "6";
	    }
		//Pet is awake, give full functionality to user
		else
		{
			System.out.println("\nEnter (1) to eat.");
			System.out.println("Enter (2) to play.");
			System.out.println("Enter (3) to sleep.");
			System.out.println("Enter (4) to take a bath.");
			System.out.println("Enter (5) to search for food.");
			System.out.print("What is your choice : ");
			choice = in.nextLine();
		}

		return choice;
	}
	
	public static void call_action(Pet p, String choice)
	{
		/*
		 * Given the players choice, call the appropriate class method
		 */
		//Call the appropriate method
		if (choice.equals("1"))
		{
			p.eat();
		}
		else if (choice.equals("2"))
		{
			p.play();
		}
		else if (choice.equals("3"))
		{
			p.sleep();
		}
		else if (choice.equals("4"))
		{
			p.clean();
		}
		else if (choice.equals("5"))
		{
			p.get_food();
		}
		else if (choice.equals("6"))
		{
			p.awake();
		}
		//User entered in invalid input. Do not call any methods
		else
		{
			System.out.println("Sorry, that is not a valid move.");
		}
	}
}