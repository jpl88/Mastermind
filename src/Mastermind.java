import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A class that contains everything to run the Mastermind Game.
 * Running the main method will start the game. 
 * @author Justin Lee.
 */
public class Mastermind {
	
	//Field that represents the secret code that must be guessed.
	private final String SECRETCODE;
	//Field that represents the number of chances that are allowed.
	private final int NUMCHANCES;
	//The input reader for the command line interface.
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	//Fields that are used to avoid "Magic Number's" throughout code. If implementation were to change for and acceptable secret code.
	//Min number in secret code.
	private final int MIN = 1;
	//Max number in secret code.
	private final int MAX = 6;
	//Length of secret code.
	private final int CODELENGTH = 4;
	
	/**
	 * Constructor method that will create a generate a secret code, and prompt for a number of chances until a valid one is given.
	 */
	public Mastermind(){
		SECRETCODE = generateSecretCode();
		NUMCHANCES = generateNumChances();
	}
	
	/**
	 * A method that will print the initial instructions for the game.
	 */
	private static void printInstructions(){
		System.out.println("\nWelcome to Mastermind!\n\n"
				+ "This application will create a secret code of 4 digits that range from 1 to 6.\n"
				+ "Then when prompted, you will enter a guess and the application will generate and output based on your response.\n"
				+ "A Plus signs indicates a correct number in the correct spot.\n"
				+ "A Negative signs indicate a correct number in the incorrect spot.\n"
				+ "Each position in the secret code can only be matched once.\n");
	}
	
	/**
	 * A method that creates a secret code, with a length of CODELENGTH each variable ranging from MIN to MAX.
	 * @return The randomly generated secret code.
	 */
	private String generateSecretCode(){
		StringBuilder sb = new StringBuilder();
		//Randomly generates a string of length CODELENGTH each variable ranging from 1 to 6.
		for(int i = 0; i < CODELENGTH; i++) sb.append(Character.forDigit((int)(Math.random()*MAX) + MIN,10));
		return sb.toString();
	}
	
	/**
	 * A method that will return a valid number of chances.
	 * @return A valid number of chances allowed to guess the secret code.
	 */
	private int generateNumChances(){
		int numChances;
		//Will continue to ask until a valid number of chances is gotten.
		while(true){
			System.out.println("Enter the number of chances allowed to figure out the secret code.");
			try{
				//Gets integer from the input line. Throws NumberFormatException if not a valid integer.
				numChances = Integer.parseInt(br.readLine());
				//Throws IllegalStateException if not valid numChances.
				numChancesIsValid(numChances);
				//Returns numChances only when valid.
				return numChances;
			}
			//Problem with reading from Buffered Reader.
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
	
	/**
	 * A method that checks if a number is greater than 0 and less than the max value of integer.
	 * @param numChances The number to be checked.
	 * @return True if greater than 0 and less than the integers max value
	 * @throws IllegalStateException When not a valid number.
	 */
	private boolean numChancesIsValid(int numChances) throws IllegalStateException{
		//Checks if between 0 and integer's max value.
		if(numChances <= Integer.MAX_VALUE && numChances > 0) return true;
		//If not throw IllegalStateException.
		else throw new IllegalStateException();
	}
	
	/**
	 * A method that will check guesses, generate responses to those guesses, and generate win loss text.
	 */
	private void playGame(){
		try{
			String guess = "";
			String secretCode;
			//Asks for new guess for a the correct number of chances or until guess is correct.
			for(int guessCounter = 0; guessCounter < NUMCHANCES && !hasWon(guess); guessCounter++){
				secretCode = new String(SECRETCODE);
				//Ask for guess.
				guess = promptGuess();
				//Generate response to the guess.
				generateGuessResponse(guess, secretCode);
			} 
			//Print win or loss text.
			gameOverText(guess);
			//Close buffered reader because game is over.
			br.close();
		}
		//Problem with reading from or closing Buffered Reader.
		catch(IOException e){
			System.out.println("Error closing reader.");
		}
	}
	
	/**
	 * A method that will print win or loss text.
	 * @param guess Guess to check for correctness.
	 */
	private void gameOverText(String guess){
		//If guess matches secret code.
		if (hasWon(guess)) System.out.println("You solved it!");
		//If guess doesn't match secret code.
		else System.out.println("You lose :(");
	}
	
	/**
	 * A method that checks if a guess matches the SECRETCODE.
	 * @param guess Guess that will be checked for correctness.
	 * @return True if matches SECRETCODE, false if it doesn't.
	 */
	private boolean hasWon(String guess){
		return guess.equals(SECRETCODE);
	}
	
	/**
	 * A method that generates the response to the users guess.
	 * @param guess Guess that will be analyzed.
	 * @param secretCode The copy of the final SECRETCODE.
	 */
	private void generateGuessResponse(String guess, String secretCode){
		//Checks for +'s which occurs only when guess(i) = secretCode(i)
		for(int i = 0; i < secretCode.length(); i++){
			//The two characters match.
			if(guess.charAt(i) == secretCode.charAt(i)){
				//Delete correct char from the guess's string.
				guess = deleteIndexFromString(guess, i);
				//Delete correct char from the secretCode string.
				secretCode = deleteIndexFromString(secretCode, i);
				System.out.print("+ ");
				//Decremented because string was edited.
				i--;
			}
		}
		//Checks for -'s which occurs only when guess(i) is contained in the secret code.
		for(int i = 0; i < secretCode.length(); i++){
			int index = secretCode.indexOf(guess.charAt(i));
			//Guess(i) is present in the secret code.
			if(index != -1){
				//Delete correct char from the secretCode string. 
				secretCode = deleteIndexFromString(secretCode, index);
				//Delete correct char from the guess's string.
				guess = deleteIndexFromString(guess, i);
				System.out.print("- ");
				//Decrement because string was edited.
				i--;
			}
		}

		System.out.println("");
	}
	
	/**
	 * A method that deletes the character in the String at a certain index.
	 * @param s A string that is to be edited.
	 * @param i The index of the string that is to be deleted.
	 * @return The edited string.
	 */
	private String deleteIndexFromString(String s, int i){
		return new StringBuilder(s).deleteCharAt(i).toString();
	}
	
	/**
	 * A method that will prompt the user for a guess.
	 * @return A valid guess to be checked against the SECRET CODE.
	 */
	private String promptGuess(){
		String guess = "";
		do{
			System.out.println("Enter your guess of 4 numbers whose values range from 1 to 6 or quit with ctrl + C.");
			try{
				//Gets user input.
				guess = br.readLine();
			}
			//Problem with reading from Buffered Reader.
			catch(IOException e){
				System.out.println("Error reading your inout please try again.");
			}
		//Continue asking until the guess is valid.
		}while(!isGuessValid(guess));
		return guess;
	}
	
	/**
	 * A method that checks if a guess is valid against a regex expression.
	 * @param guess String to be checked for validity.
	 * @return True if valid, false if not.
	 */
	private boolean isGuessValid(String guess){
		//Regex expression of char's between MIN and MAX repeated CODELENGTH times.
		if(!guess.matches("^[MIN-MAX]{CODELENGTH}$")){
			System.out.println("Your input must be 4 numbers whose values range from 1 to 6");
			return false;
		}
		return true;
	}
	
	/**
	 * A main method that runs the game.
	 * @param args String of arguemtns that aren't used.
	 */
	public static void main(String[] args){
		//Print instructions.
		Mastermind.printInstructions();
		//Create Mastermind will generate SECRETCODE and ask for NUMCHANCES.
		Mastermind m = new Mastermind();
		//Start guessing game.
		m.playGame();
	}
	
}
