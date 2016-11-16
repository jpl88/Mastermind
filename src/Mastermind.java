import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * A class that contains everything to run the Mastermind Game.
 * Running the main method will start the game. 
 * @author Justin Lee
 */
public class Mastermind {
	
//	private final String SECRETCODE;
//	private final int NUMCHANCES;
	
	//Fields that are used to avoid "Magic Number's" throughout code. If implementation were to change for and acceptable secret code.
	private final int MIN = 1;
	private final int MAX = 6;
	private final int CODELENGTH = 4;
	
	
	public Mastermind(){
		printInstructions();
	}
	
	private static void printInstructions(){
		System.out.println("\nWelcome to Mastermind!\n\n"
				+ "This application will create a secret code of 4 digits that range from 1 to 6.\n"
				+ "You the user will define a number of chances to figure out the code.\n"
				+ "Then when prompted, you will enter a guess and the application will generate and output based on your response.\n"
				+ "A Plus signs indicates a correct number in the correct spot.\n"
				+ "A Negative signs indicate a correct number in the incorrect spot.\n"
				+ "Each position in the secret code can only be matched once.\n"
				+ "For example, a guess of 1134 against a secret code of 1234 would get three plus signs:\n"
				+ "One for each of the exact matches in the first, third and fourth positions.\n"
				+ "The number match in the second position would be ignored.\n");
	}
	
	
	
	public static void main(String[] args){
		Mastermind m = new Mastermind();
	}
	
}
