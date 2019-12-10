import java.io.File; 
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader; 
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

/**
 * Driver class that runs the program with try-catch for error checking
 */
public class A2 
{
	public static void main(String args[])
	{
		File file;
		String filename = args[0];
		Graph g = new Graph();
		
		Scanner scan = new Scanner(System.in);
		
		//trys to populate g
		try
		{
			file = new File(filename);
			JSONParser parser = new JSONParser();
			JSONArray jsonArr;
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String temp;
			Set<Integer> set = new HashSet<Integer>();
			int position = 0; //incrementer that assigns actor with value
			try 
			{
				reader.readLine(); //skips first line of file (categories)
				while ((temp = reader.readLine()) != null)
				{
					if(temp.contains("cast_id")) //only reads lines that contain a "cast" section since not every movie has "cast"
					{
					//excludes movie ID, movie, and "crew" portion of the line in file; this is to make it compatible with JSON parsing
						temp = temp.substring(temp.indexOf("\"[")+1, temp.lastIndexOf("[")-2).replaceAll("\"\"", "\"");
						try
						{
							jsonArr = (JSONArray)parser.parse(temp);
							for(int i = 0; i < jsonArr.size(); i++)
							{
								temp = (String)((JSONObject)jsonArr.get(i)).get("name");

								temp = temp.toLowerCase();

								if(!g.containsActor(temp))
								{
								//only adds unique actors, and adds both actor and position value to the maps respectively
									g.addToNamesMap(temp, position);
									g.addToIndexMap(position, temp);
									position++;
								}
							
								set.add(g.getIndex(temp)); //adds all the actors of a single movie into a set
							}
							g.addAllEdges(set);
							set.clear(); //clears contents so it could be used for next line in file
						}
						catch (ParseException e)
						{
							System.out.println(temp);
						}
				}
			}
		}
		finally 
		{
			reader.close();
		}
		}
		
		//catches file not found
		catch (IOException e) 
		{
			System.out.println("File '" + filename + ".csv' does not exist");
			System.exit(1);
		}



		
		//main logic
		boolean actor1Boolean = true;
		boolean actor2Boolean = true;
		String actor1 = "";
		String actor2 = "";
		while(actor1Boolean == true)
		{
			System.out.println("Actor 1 name: ");
			actor1 = scan.nextLine().toLowerCase();
			if(g.containsActor(actor1))
			{
				actor1Boolean = false;
			}
			else
			{
				System.out.println("No such actor.");
			}
		}
		while(actor2Boolean == true)
		{
			System.out.println("Actor 2 name: ");
			actor2 = scan.nextLine().toLowerCase();
			if(actor2.equals(actor1))
			{
				System.out.println("Same actor, please input a different name.");
			}
			else
			{
				if(g.containsActor(actor2))
				{
					actor2Boolean = false;
				}
				else
				{
					System.out.println("No such actor.");
				}
			}
		}
		g.search(actor1, actor2);
		scan.close();
	}
}