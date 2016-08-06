package wordsFreq;

import java.io.*; 
import java.util.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Player {
	
	private String secretWord;

	public Player(String secretWord) {
		this.secretWord = secretWord;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Enter level: ");
        Scanner sc = new Scanner(System.in);
		int level = sc.nextInt();
		String lines[] = AnagramCluster.ReadFile();
    	Map<String,Set<String>> cluster = AnagramCluster.findAnagrams(lines);
    	Map<String,Set<String>> sorted = AnagramCluster.postProcess(cluster);
    	boolean validity = false;
    	String secret_word_init = "";
    	
    	while(!validity){
    		secret_word_init = randomWordGenerator(mapToList(wordsByLevel(level,sorted)),level);		
    		if(isValid(secret_word_init)){ 
    			validity = true;
    		}
    	}
    	Player computer = new Player(secret_word_init);
		System.out.println("Computer Guess :"+ computer.secretWord);
		
		boolean endOfGame = false;
		int count = 0;
		String comp_guess = randomWordGenerator(mapToList(wordsByLevel(level,sorted)),level) ;
		sorted = findWords(sorted,comp_guess,0);
		while(!endOfGame){
			System.out.println("Enter your guess word: ");
			String  userguess = sc.next();
			System.out.println("User Guess "+ userguess);
			if(userguess.compareTo(computer.secretWord)==0)
			{
				endOfGame = true;
				System.out.println("You won");
				break;
			}
			
			System.out.println("number of matching char in your guess and comp secret word : "+ noOfSimilarChar(computer.secretWord,userguess));
			
			if(sorted.size()>1){
				 comp_guess = randomWordGenerator(mapToList(sorted),level) ;
				 System.out.println("Computer Guess: "+ comp_guess);
			}			 
			else
			{	
				Object[] comp_guess_arr = sorted.get(comp_guess).toArray();
				System.out.println("Computer Guess:  "+ (String) comp_guess_arr[count]);
				count++;
			}
			System.out.println("Is this your secret word (true/false)");
			boolean end = sc.nextBoolean();
			if(end)
			{
				System.out.println("Computer won, secret word was" + computer.secretWord);
				break;
			}
			else
			{
				System.out.println("Enter no. of matching characters");
				int match_length = sc.nextInt();
				sorted = findWords(sorted,comp_guess,match_length);
			}			
		}
	}


	// returns Map of words depending upon the level
	public static Map<String, Set<String>> wordsByLevel(int level, Map<String, Set<String>> dict){
		int len;
		if (level == 1) len = 4;
		else if (level == 2 ) len = 5;
		else len = 6;
		Map<String, Set<String>> words = new HashMap<String, Set<String>>();
		for (Map.Entry<String, Set<String>> entry : dict.entrySet()) {
			if (entry.getKey().length() == len){
				words.put(entry.getKey(),entry.getValue());
			}
		}
		return words;
	  }
    
	public static boolean isValid (String input){
		input = input.toUpperCase();
		int [] charCount = new int[26];
		for (int i=0; i<input.length(); i++){
			if(charCount[input.charAt(i) - 'A'] == 0 ){
				charCount[input.charAt(i) - 'A'] = 1;
			}
			else return false;
		}
		return true;
	}
    
    public static ArrayList<String> mapToList (Map<String, Set<String>> wordsByLevel) {
		ArrayList<String> words = new ArrayList<String>();
		for (Map.Entry<String, Set<String>> entry : wordsByLevel.entrySet()) {
				for (String s : entry.getValue()){
					words.add(s);
				}
		}
		return words;
	  }
    
    public static Map<String,Set<String>> findWords(Map<String,Set<String>> map_prev,String guessed, int match)
    {
   	 Map<String,Set<String>> map = new HashMap<>();
   	 if(guessed.length()!= match)
   	 {
   		 Iterator it = map_prev.entrySet().iterator();
   		    while (it.hasNext()) {
   		    	Map.Entry pair = (Map.Entry)it.next();
   		    	String key = (String) pair.getKey();
   		    	if(key.length()==guessed.length() && noOfSimilarChar(key,guessed)>=match ) 
   		    	{
   		    		
   		    		map.put(key, (Set<String>)pair.getValue());
   		    	}  		    	
   		    	
   		    }   			 
   		 
   	 }
   	 else {
   		 String s = guessed;
   		 Character[] chars = new Character[s.length()]; 
   		 for (int i = 0; i < chars.length; i++)
   			    chars[i] = s.charAt(i);
   		 Arrays.sort(chars);
   		 String sorted = "";
   		 for (int i = 0; i < chars.length; i++)
	   		 {
	   		   sorted += chars[i];
	   		 }
   		 map.put(guessed, map_prev.get(guessed)); 
   	 	}
   	 		return map;  	 
    }

	public static String randomWordGenerator(ArrayList<String> words, int level) {
		// TODO Auto-generated method stub
		
			Random rand = new Random();
			String randomWord = words.get(rand.nextInt(words.size()));		
		
			return randomWord;
	}
	
	public static int noOfSimilarChar(String word1,String word2) {
	    int commonChars = 0;
	    word1 = word1.toUpperCase();
	    word2 = word2.toUpperCase();
	    int [] charMap = new int [26];
		    for (int i = 0; i < word1.length() ; i++){
		    	if( word2.indexOf(word1.charAt(i)) >= 0 && charMap[word1.charAt(i) - 'A'] == 0){
		    		commonChars++;
		    		charMap[word1.charAt(i) - 'A'] = 1;
		    	}
		    }
		    return commonChars;
		}
	

}
