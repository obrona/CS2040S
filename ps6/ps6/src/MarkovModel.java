import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;
	public HashMap<String, int[]> wordBank;
    //value is an integer array of size 257, 0 to 255 stores frequency of ACSII characters, 256 stores frequency of key
	int order;

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
        wordBank = new HashMap<>();
		this.order = order;
		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		int len = text.length();
		for (int i = 0; i < len - order; i++) {
			StringBuilder s = new StringBuilder();
			for (int j = 0; j < order; j++) {
				s.append(text.charAt(i + j));
			}
			char nextChar = text.charAt(i + order);
			int[] value = wordBank.get(s.toString());
			if (value == null) {
				value = new int[257];
			    wordBank.put(s.toString(), value);
			}
			value[256] ++;
			if ((int) nextChar != 0) { //Ignore null characters
				value[(int) nextChar] ++;
			}
		}
		// Build the Markov model here
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		int[] value = wordBank.get(kgram);
		if (value == null) {
			return 0;
		} else {
			return value[256];
		}
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		int[] value = wordBank.get(kgram);
		if (value == null) {
			return 0;
		} else {
			return value[(int) c];
		}
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		int[] value = wordBank.get(kgram);
		if (value == null) {
			return NOCHARACTER;
		} else {
			int frequency = value[256];
			int randomInt = generator.nextInt(frequency);
			for (int i = 0; i < 256; i ++) {
				randomInt = randomInt - value[i];
				if (randomInt < 0) {
					return (char) i;
				}
			}
			return NOCHARACTER;
		}

	}
}
