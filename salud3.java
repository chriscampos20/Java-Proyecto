import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class salud3 {

  static Scanner scan = new Scanner(System.in);
  static Map<Integer, Exercise> exerciseMap = new HashMap<>();
  static int exerciseCounter = 1;
  static boolean isAdmin = false;

  public static void main(String[] args) {
    authenticate();
    
    // Add sample exercises
    exerciseMap.put(exerciseCounter++, new Exercise(1, "Running", "Eat less carbs", "Stretch before running")); 
    exerciseMap.put(exerciseCounter++, new Exercise(2, "Walking", "Stay hydrated", "Use proper posture"));
    
    showMainMenu();
    scan.close();
  }

  public static void authenticate() {
    System.out.println("Select your mode:");
    System.out.println("1. Admin");
    System.out.println("2. User");
    System.out.println("3. Guest");
    System.out.println("4. Exit");
    
    int modeChoice = scan.nextInt();
    
    if (modeChoice == 4) {
      System.out.println("Exiting...");
      System.exit(0);
    }
    
    switch (modeChoice) {
      case 1:
        authenticateAdmin();
        break;
      case 2:
        authenticateUser();
        break;
      case 3:
        // Guest mode, no auth needed
        break;
      default:
        System.out.println("Invalid choice. Exiting...");
        System.exit(0);  
    }
  }

  private static void authenticateAdmin() {
    System.out.print("Enter admin password: ");
    String password = scan.next();
    
    if (password.equals("admin123")) {
      isAdmin = true;
      System.out.println("Admin login successful!");
    } else {
      System.out.println("Invalid password. Exiting...");
      System.exit(0);
    }
  }
  
  private static void authenticateUser() {
    System.out.print("Enter username: ");
    String username = scan.next();

    System.out.print("Enter password: ");
    String password = scan.next();

    if (username.equals("user") && password.equals("user123")) {
      isAdmin = false;
      System.out.println("User login successful!");
    } else {
      System.out.println("Invalid credentials. Exiting...");
      System.exit(0);
    }
  }

  public static void showMainMenu() {
    
    System.out.println("--- MAIN MENU ---");
    
    if (isAdmin) {
      System.out.println("1. Create Exercise");
      System.out.println("2. Update Exercise");
      System.out.println("3. Delete Exercise"); 
      System.out.println("4. List Exercises");
      System.out.println("5. Exit");
      System.out.println("6. Switch to User Mode");
    } else {
      System.out.println("1. List Exercises");
      System.out.println("2. Exit");
    }
    
    System.out.print("Enter choice: ");
    int option = scan.nextInt();
    
    if (option == 5) {
      System.out.println("Exiting...");
      System.exit(0);
    }
    
    if (isAdmin) {
      handleAdminOption(option); 
    } else {
      handleUserOption(option);
    }
  }

  private static void handleAdminOption(int option) {
    switch(option) {
      case 1:
        createExercise();
        break;
      case 2: 
        updateExercise();
        break;
      case 3:
        deleteExercise();
        break;
      case 4:
        listExercises();
        break;
      case 5: 
        System.out.println("Exiting...");
        System.exit(0);
      case 6:
        isAdmin = false;
        System.out.println("Switched to User Mode"); 
        break;
      default:
        System.out.println("Invalid option!");
    }
    
    showMainMenu();
  }

  private static void handleUserOption(int option) {
    switch(option) {
      case 1:
        listExercises();
        break;
      case 2:
        System.out.println("Exiting...");
        System.exit(0);
      default:
        System.out.println("Invalid option!");
    }
    
    showMainMenu();
  }

  private static void createExercise() {
    System.out.print("Enter exercise name: ");
    String name = scan.next();
    
    System.out.print("Enter diet tips: ");
    String dietTips = scan.next();

    System.out.print("Enter health tips: ");
    String healthTips = scan.next();

    Exercise newExercise = new Exercise(exerciseCounter++, name, dietTips, healthTips);
    exerciseMap.put(newExercise.getNumber(), newExercise);

    System.out.println("Exercise created successfully!");
  }

  private static void updateExercise() {
    System.out.println("--- Exercise List ---");
    
    for (Exercise exercise : exerciseMap.values()) {
      System.out.println(exercise.getNumber() + ". " + exercise.getName());
    }
    System.out.print("Enter exercise number to update (0 to go back): ");
    int number = scan.nextInt();

    if (number == 0) {
        return;  // Go back to the previous menu
    }

    Exercise exercise = exerciseMap.get(number);

    if (exercise == null) {
        System.out.println("Invalid exercise number!");
        return;
    }

    System.out.print("Enter new name (or type '0' to go back): ");
    String name = scan.next();

    if (name.equalsIgnoreCase("0")) {
        System.out.println("Update canceled.");
        return;
    }

    exercise.setName(name);

    System.out.print("Enter new diet tips: ");
    String dietTips = scan.next();
    exercise.setDietTips(dietTips);

    System.out.print("Enter new health tips: ");
    String healthTips = scan.next();
    exercise.setHealthTips(healthTips);

    System.out.println("Exercise updated successfully!");
}


  private static void deleteExercise() {
    System.out.print("Enter exercise number to delete: ");
    int number = scan.nextInt();

    System.out.print("Are you sure you want to delete exercise " + number + "? (Y/N): ");
    String confirmation = scan.next();

    if (confirmation.equalsIgnoreCase("Y")) {
        if (exerciseMap.remove(number) == null) {
            System.out.println("Invalid exercise number!");
        } else {
            System.out.println("Exercise deleted successfully!");
        }
    } else {
        System.out.println("Deletion canceled.");
    }
}


  private static void listExercises() {
    System.out.println("--- Exercise List ---");
    
    for (Exercise exercise : exerciseMap.values()) {
      System.out.println(exercise.getNumber() + ". " + exercise.getName());
    }

    System.out.print("Enter exercise number to review (0 to go back): ");
    int number = scan.nextInt();

    if (number == 0) {
      showMainMenu();
    } else {
      reviewExercise(number); 
    }
  }

  private static void reviewExercise(int number) {
    Exercise exercise = exerciseMap.get(number);

    if (exercise == null) {
      System.out.println("Invalid exercise number!");
      return;
    }

    System.out.println("--- Exercise Details ---");
    System.out.println("Number: " + exercise.getNumber());
    System.out.println("Name: " + exercise.getName());  
    System.out.println("Diet Tips: " + exercise.getDietTips());
    System.out.println("Health Tips: " + exercise.getHealthTips());
    
    System.out.println("1. Go back");
    int choice = scan.nextInt();

    if (choice == 1) {
      listExercises();
    }
  }
  
}

class Exercise {

  private int number;
  private String name;
  private String dietTips;
  private String healthTips;

  public Exercise(int number, String name, String dietTips, String healthTips) {
    this.number = number;
    this.name = name;
    this.dietTips = dietTips;
    this.healthTips = healthTips;
  }

  public int getNumber() {
    return number;
  }

  public String getName() {
    return name; 
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDietTips() {
    return dietTips;
  }

  public void setDietTips(String dietTips) {
    this.dietTips = dietTips;
  }

  public String getHealthTips() {
    return healthTips;
  }

  public void setHealthTips(String healthTips) {
    this.healthTips = healthTips;
  }

}