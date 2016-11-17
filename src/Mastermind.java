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
	
	private final String SECRETCODE;
	private final int NUMCHANCES;
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	//Fields that are used to avoid "Magic Number's" throughout code. If implementation were to change for and acceptable secret code.
	private final int MIN = 1;
	private final int MAX = 6;
	private final int CODELENGTH = 4;
	
	
	public Mastermind(){
		SECRETCODE = generateSecretCode();
		NUMCHANCES = generateNumChances();
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
	
	private String generateSecretCode(){
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		for(int i = 0; i < CODELENGTH; i++) sb.append(Character.forDigit((r.nextInt(MAX-MIN) + MIN), 10));
		return sb.toString();
	}
	
	private int generateNumChances(){
		int numChances;
		while(true){
			System.out.println("Enter the number of chances allowed to figure out the secret code.");
			try{
				numChances = Integer.parseInt(br.readLine());
				numChancesIsValid(numChances);
				return numChances;
			}
			catch(IOException e){
				System.out.println("Error reading your inout please try again.");
			}
			catch(NumberFormatException e){
				System.out.println("Your input of was not an Integer.");
			}
			catch(IllegalStateException e){
				System.out.println("Youe input wasn't greater than 0.");
			}
		}
	}
	
	/*********************************************
	 *****generateNumChances() Helper Methods*****
	 *********************************************/
	
	private boolean numChancesIsValid(int numChances) throws IllegalStateException{
		if(numChances < Integer.MAX_VALUE && numChances > 0) return true;
		else throw new IllegalStateException();
	}
	
	private void playGame(){
		try{
			String guess = "";
			String secretCode = new String(SECRETCODE);
			int guessCounter = 0;
			do {
				guess = promptGuess();
				if (guess.equals("exit")) exitGame();
				else generateGuessResponse(guess, secretCode);
			} while (guessCounter < NUMCHANCES && !hasWon(guess));
			gameOverText(guess);
			br.close();
		}
		catch(IOException e){
			System.out.println("Error closing reader.");
		}
	}
	
	/***********************************
	 *****playGame() Helper Methods*****
	 ***********************************/
	
	private void gameOverText(String guess){
		if (hasWon(guess)) System.out.println("You solved it!");
		else System.out.println("You loose :(");
	}
	
	private void exitGame(){
		System.exit(0);
	}
	
	private boolean hasWon(String guess){
		return guess.equals(SECRETCODE);
	}
	
	private void generateGuessResponse(String guess, String secretCode){
		System.out.println("");
		checkForPlus(guess, secretCode);
		checkForMinus(guess, secretCode);
		System.out.println("");
	}
	
	private void checkForPlus(String guess, String secretCode){
		for(int i = 0; i < secretCode.length(); i++){
			if(guess.charAt(i) == secretCode.charAt(i)){
				deleteIndexFromString(guess, i);
				deleteIndexFromString(secretCode, i);
				System.out.print("+ ");
				i--;
			}
		}
	}
	
	private void checkForMinus(String guess, String secretCode){
		for(int i = 0; i < secretCode.length(); i++){
			if(secretCode.contains(String.valueOf(guess.charAt(i)))){
				deleteIndexFromString(guess, i);
				deleteIndexFromString(secretCode, i);
				System.out.print("- ");
				i--;
			}
		}
	}
	
	private String deleteIndexFromString(String s, int i){
		return new StringBuilder(s).deleteCharAt(i).toString();
	}
	
	private String promptGuess(){
		String guess = "";
		do{
			System.out.println("Enter your guess of 4 numbers whose values range from 1 to 6 or enter \"exit\".");
			try{
				guess = br.readLine();
			}
			catch(IOException e){
				System.out.println("Error reading your inout please try again.");
			}
		}while(!isGuessValid(guess));
		return guess;
	}
	
	private boolean isGuessValid(String guess){
		if(!guess.matches("^[1-6]{4}$")){
			System.out.println("Your input must be 4 numbers whose values range from 1 to 6");
			return false;
		}
		return true;
	}
	
	public static void main(String[] args){
		Mastermind.printInstructions();
		Mastermind m = new Mastermind();
		m.promptGuess();
		m.playGame();
	}
	
}
