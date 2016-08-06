package wordsFreq;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.lang.*;
import java.io.*;

public class AnagramCluster {
	
	static Map<String, Set<String>> sorted;
	public static Map<String, Set<String>> getSorted() {
		return sorted;
	}

	public static void setSorted(Map<String, Set<String>> sorted) {
		AnagramCluster.sorted = sorted;
	}

	
	public static String[] ReadFile()
	{
		String[] lines = new String[267750];
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\GitRepo\\FindAnagrams\\sowpods.txt"))){
			String currentLine = br.readLine();
					int i = 0;
				String str = "";
					while((str=br.readLine())!=null)
					{
						lines[i] = str;
						i++;
					}				
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}
	
	public static String getGroupID(String str)
	{
        char[] charArray = str.toCharArray();
        Arrays.sort(charArray);
        String out = new String(charArray);
        return out;
	}
	
	public static Map<String,Set<String>> findAnagrams(String[] words)
	{
	    Map<String,Set<String>> map = new HashMap<>();
	    for(String word : words)
	    {
	        Set<String> tempSet = new HashSet<>();
	        if(map.containsKey(getGroupID(word))){
	             tempSet = map.get(getGroupID(word));
	            
	        }
	        tempSet.add(word);
	        map.put(getGroupID(word),tempSet);
	    }
	    return map;
	}
	public static Map<String, Set<String>> postProcess(Map<String,Set<String>> m)
{
    Map<String,Set<String>> ret_m = new HashMap<>();
    for (Map.Entry<String, Set<String>> entry : m.entrySet())
    {
        if(entry.getValue().size()!=1)
        	{
        	ret_m.put(entry.getKey(),sortSet(entry.getValue()));
        	}
    }
    return ret_m;
}
public static Set<String> sortSet (Set<String> anagramcluster)
	{
		Set<String> sortedcluster = new TreeSet<String>(anagramcluster);
		
		return sortedcluster;
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String lines[] = ReadFile();
		//System.out.println(lines.length);
		Map<String,Set<String>> cluster = findAnagrams(lines);
		sorted = postProcess(cluster);
		
		System.out.println(sorted);
		

	}

}
