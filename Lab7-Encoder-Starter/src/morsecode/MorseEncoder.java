// COURSE: CSCI1620
// TERM: Fall 2019

//
//NAME: <YOUR NAME HERE>
//RESOURCES: <DISCLOSURE OF RESOURCE USE (FULL SENTENCE)>

package morsecode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/////////////////////////////////////////////////////////////
// TODO: Complete the method stubs for MorseEncoder to 
//       implement the functionality of each as designed.
//       Don't forget to add necessary instance data!
/////////////////////////////////////////////////////////////

/**
 * This class provides the ability to encode character data and files
 * into their equivalent representation in Morse Code. The International
 * Morse Code alphabet is used, and only the letters A-Z are supported. 
 *  
 * For more: https://en.wikipedia.org/wiki/Morse_code
 * 
 * @author tbeaulieu
 *
 */
public class MorseEncoder
{
	public String characterFile;
	public static char character;
	private String sentence;
	
	
	//TODO: Add any fields needed here
	
	/**
	 * Initializes a MorseEncoder object based on a character map file.
	 * The character map file is a CSV where each row represents the mapping
	 * between a single English alphabet character and the Morse Code equivalent.
	 * 
	 * For example, rows in the should look like this:
	 * A,.-
	 * B,-...
	 * C,-.-.
	 * 
	 * Further, MorseEncoder class assumes that rows in this file are in order
	 * from A-Z and that no additional data is present in the file.
	 * 
	 * All exceptions arising during attempts to read character map file will be
	 * passed back to the client code.
	 * 
	 * @param characterMapFile The name of the file containing Morse code character mappings.
	 * @throws FileNotFoundException when the characterMapFile path is invalid 
	 */
	public MorseEncoder(String characterMapFile) throws FileNotFoundException
	{
		characterFile = characterMapFile;
	}
	
	/**
	 * Encodes a single English alphabet character into Morse code based on
	 * the character map file specified at the time this MorseCode object was
	 * created.  Lowercase and uppercase characters are treated equivalent
	 * in the conversion to Morse code.
	 * 
	 * @param plainChar The alphabetic character to convert into Morse code.
	 * @return A string containing the corresponding Morse code character.
	 * @throws InvalidCharacterException when a character other than A-Z or a-z is passed as plainChar.
	 */
	public String encodeChar(char plainChar)
	{
		switch (plainChar)
		{
		case 'A': return ".-" ;
		case 'B': return "-..." ;
		case 'C': return "-.-." ;
		case 'D': return "-.." ;
		case 'E': return "." ;
		case 'F': return "..-." ;
		case 'G': return "--." ;
		case 'H': return "...." ;
		case 'I': return ".." ;
		case 'J': return ".---" ;
		case 'K': return "-.-" ;
		case 'L': return ".-.." ;
		case 'M': return "--" ;
		case 'N': return "-." ;
		case 'O': return "---" ;
		case 'P': return ".--." ;
		case 'Q': return "--.-" ;
		case 'R': return ".-." ;
		case 'S': return "..." ;
		case 'T': return "-" ;
		case 'U': return "..-" ;
		case 'V': return "...-" ;
		case 'W': return ".--" ;
		case 'X': return "-..-" ;
		case 'Y': return "-.--" ;
		case 'Z': return "--.." ;
		}
		return " ";
	}
	
	/**
	 * Encodes a whole English word into Morse code based on the character map
	 * file specified at the time this MorseCode object was created.  Lowercase and 
	 * uppercase characters are treated equivalent in the conversion to Morse code.
	 * 
	 * Consecutive characters in the Morse code equivalent string will be separated
	 * by a single space character.  For example: "DOG" ==> "-.. --- --."
	 * 
	 * @param plainWord The word to convert into Morse code.
	 * @return A string containing the corresponding Morse code characters.
	 * @throws InvalidCharacterException when plainWord contains one or more non-alphabetic characters.
	 */
	public String encodeWord(String plainWord)
	{
		sentence = plainWord;
		for (int i = 0; i < sentence.length(); i++)
		{
			sentence += encodeChar(sentence.charAt(i));
		}
		return sentence;
	}
	
	/**
	 * Processes a text input file and outputs its Morse code equivalent
	 * in a separate file.  Line breaks are preserved between the input
	 * and output files in identical locations.  Consecutive words on a 
	 * single line will be rendered in the output file with the sequence 
	 * " | " so that they can be easily distinguished in Morse code.  
	 * 
	 * Thus, if the input file contains the phrase:
	 *  GO SPOT GO
	 *  
	 * The output file will contain:
	 *  --. --- | ... .--. --- - | --. ---
	 * 
	 * Any exceptions that occur during file read/write will be passed back
	 * to the calling code.
	 * 
	 * @param inputFile The filename of the English based file to be processed.
	 * @param outputFile The filename where the output will be written.  Output files are
	 * 					 always written in "write" mode and any existing contents will be
	 *                   deleted.
	 * @throws IOException 
	 * @throws InvalidCharacterException when one or more invalid characters are detected 
	 *                   while processing the input file.
	 */
	public void encodeFile(String inputFile, String outputFile) throws IOException
	{
		try
		{
			//read the file contents here...}
			Scanner in = new Scanner(new File(inputFile));
			String record = in.nextLine();
			FileOutputStream fileOut= new FileOutputStream(outputFile, false);
			PrintWriter writer = new PrintWriter(fileOut);
			writer.println(record);

			while(in.hasNextLine())
			{
				String line = " ";
				String cvsSplitBy = " ";
				String[] entry = line.split(cvsSplitBy);
					
				for (int i = 0; i < entry.length; i++)
				{
					if (i < entry.length - 1)
					{
						line = line + encodeWord(entry[i]) + " | ";
					}
					else
					{
						line = line + encodeWord(entry[i]);
					}
				}
				if (!in.hasNextLine())
				{
					writer.print(line);
				}
				else
				{
					writer.println(line);
				}
			}
			writer.close();
			in.close();
		
		}//end try block
		catch(FileNotFoundException fnfe)
		{
			System.out.println("File not found");
		}
	}
	
	/**
	 * A helper method that quickly converts between a character letter and an integer ordinal position
	 * suitable for use as an array index.
	 * 
	 * @param c	The uppercase character to convert.
	 * @return	An ordinal value 0-25 corresponding to the letter c's alphabetic position (A is 0, B is 1, and so on).
	 *          The behavior for non-uppercase characters is unspecified.
	 */
	private int charToInt(char c)
	{
		return Character.toUpperCase(c) - 65;
	}
	
}
