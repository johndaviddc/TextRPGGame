import java.util.Random;
import java.util.Scanner;

class Character {
	String name;
	int health;
	int attack;
	
	public Character(String name, int health, int attack) {
		this.name = name;
		this.health = health;
		this.attack = attack;
	}
	
	public void takeDamage(int damage) {
		health -= damage;
		if (health < 0) {
			health = 0;
		}
	}
	
	public boolean isAlive() {
		return health > 0;
	}
	
	public int attack() {
		return attack;
	}
}

class Player extends Character {
	int experience;
	
	public Player(String name) {
		super(name, 100, 10);
		experience = 0;
	}
	
	public void gainExperience(int amount) {
		experience += amount;
	}
}

class Enemy extends Character {
	public Enemy(String name, int health, int attack) {
		super(name, health, attack);
	}
}

class Quest {
	String description;
	int experienceReward;
	
	public Quest(String description, int experienceReward) {
		this.description = description;
		this.experienceReward = experienceReward;
	}
	
	public void startQuest(Player player) {
		System.out.println("Quest: " + description);
		System.out.println("You embark on a quest...");
		// Simulate quest completion
		player.gainExperience(experienceReward);
		System.out.println("Quest complete! You gained " + experienceReward + " experience.");
	}
}

public class TextRPGGame {
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Enter your character's name: ");
			String playerName = scanner.nextLine();
			
			Player player = new Player(playerName);
			
			Quest[] quests = {
					new Quest("Defeat the goblins", 20),
					new Quest("Retrieve the lost artifact", 30)
			};
			
			System.out.println("Welcome to the Text RPG Game, " + player.name + "!");
			while (player.isAlive()) {
				System.out.println("\nSelect an action:");
				System.out.println("1. Fight enemies");
				System.out.println("2. Take in quests");
				System.out.println("3. Quit");
				
				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume newline
				
				switch (choice) {
					case 1:
						// Simulate a battle
						Enemy enemy = new Enemy("Goblin", 30, 5);
						battle(player, enemy);
						break;
					case 2:
						// Display available quests and allow the player to choose
						System.out.println("Availble quests:");
						for (int i = 0; i < quests.length; i++) {
							System.out.println((i + 1) + ": " + quests[i].description);
						}
						int questChoice = scanner.nextInt();
						scanner.nextLine(); // Consume newline
						if (questChoice >= 1 && questChoice <= quests.length) {
							quests[questChoice - 1].startQuest(player);
						} else {
							System.out.println("Invalid choice.");
						}
						break;
					case 3:
						System.out.println("Thanks for playing!");
						return;
					default:
						System.out.println("Invalid choice.");
				}
			}
			System.out.println("Game over! " + player.name + " has been defeated.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void battle(Player player, Enemy enemy) {
		System.out.println("Battle start!" + player.name + " vs " + enemy.name);
		
		Random random = new Random();
		while (player.isAlive() && enemy.isAlive()) {
			int playerAttack = player.attack() + random.nextInt(6);
			int enemyAttack = enemy.attack() + random.nextInt(4);
			
			enemy.takeDamage(playerAttack);
			player.takeDamage(enemyAttack);
			
			System.out.println(player.name + " attacks " + enemy.name + " for " + playerAttack + " damage.");
			System.out.println(enemy.name + " attacks " + player.name + " for " + enemyAttack + " damage.");
		}
		
		if (player.isAlive()) {
			player.gainExperience(10);
			System.out.println("You defeated " + enemy.name + " and gained 10 experience.");
		} else {
			System.out.println(player.name + " has been defeated by " + enemy.name + ".");
		}
	}
}
