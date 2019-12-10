import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map;
import java.util.Queue;

/**
 * Class that contains the adjacency list index and all functions associated with it.

 */
public class Graph
{
	private final HashMap<String, Integer> actorNamesMap;
	private final HashMap<Integer, String> actorIndexMap;
	private final LinkedList<Integer>[] adjList;
	
	/**
	 * Initialize constructor
	 */
	public Graph()
	{
		actorNamesMap = new HashMap<String, Integer>();
		actorIndexMap = new HashMap<Integer, String>();
		adjList = new LinkedList[54202];
		for(int i = 0; i < adjList.length; i++)
		{
			adjList[i] = new LinkedList<>();
		}
	}
	
	/**
	 * Creates edge between actor in position with v2; actors are represented by position and v2
	 * Makes sure no duplicates are added
	 */
	public void addEdge(int position, int v2) throws Exception
	{
		if(!actorIndexMap.containsKey(position) || position < 0)
		{
			throw new Exception("Map does not contain index and/or position out of bounds");
		}
		
		if(position > adjList.length)
		{
			LinkedList<Integer>[] newArr = new LinkedList[adjList.length * 2];
		    System.arraycopy(adjList, 0, newArr, 0, adjList.length);
		    newArr = adjList;
		}
		
		if(!adjList[position].contains(v2))
		{
			adjList[position].add(v2);
		}
			
		if(!adjList[v2].contains(position))
		{
			adjList[v2].add(position);
		}	
	}
	
	/**
	 * Adds all actors from list to certain actor position in adjacency list
	 * Removes itself so that each actor won't contain itself as a neighbor
	 */
	public void addAllEdges(Set<Integer> set)
	{
		for(int index : set)
		{
			adjList[index].addAll(set);
			Iterator<Integer> it = getNeighbors(index).iterator();
			while(it.hasNext())
			{
				int temp = it.next();
				if(temp == index)
				{
					removeEdge(index, temp);
				}
			}
		}
	}
	
	/**
	 * Removes edge from adjacency list
	 */
	public boolean removeEdge(int pos, Object index)
	{
		if(pos < 0 || pos > adjList.length)
		{
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		
		if(!adjList[pos].contains(index))
		{
			throw new NullPointerException("Does not contain index " + index);
		}
		return adjList[pos].remove(index);
	}
	
	/**
	 * Prints all the neighboring vertices from inputed position in adjaency list.
	 */
	public String printAllNeighborsAtPosition(int pos)
	{
		String result = "";
		Iterator<Integer> it = adjList[pos].iterator();
		result += "All neighboring vertices at index: " + pos + "\n";
		while(it.hasNext())
		{
			result += it.next();
			if(it.hasNext())
			{
				result +=" --> ";
			}
		}
		return result;
	}
	
	/**
	 * Returns boolean of whether there actor1 shares an edge with actor2
	 */
	public boolean containsNeighborOfIndex(int actor1, int actor2)
	{
		return adjList[actor1].contains(actor2);
	}

	
	/**
	 * Prints to console the entire adjacency list
	 */
	public void printAdjacencyList()
	{
		for(int i = 0; i < actorIndexMap.size(); i++)
		{
			System.out.println("Index " + i + ": ");
			Iterator<Integer> it = adjList[i].iterator();
			while(it.hasNext())
			{
				System.out.print(it.next());
				if(it.hasNext())
				{
					System.out.print(" --> ");
				}
			}
			System.out.println();
		}
	}

	/**
	 * Method that gets all the neighboring vertices from position 'v' of the adjacency list
	 */
	public LinkedList<Integer> getNeighbors(int v)
	{
		LinkedList<Integer> linkedList = new LinkedList<Integer>();
		Iterator<Integer> it = adjList[v].iterator();
		while(it.hasNext())
		{
			linkedList.add(it.next());
		}
		return linkedList;
	}
		
	/**
	 * Map method that puts actor name as key and index as value
	 */
	public void addToNamesMap(String name, int position)
	{
		actorNamesMap.put(name, position);
	}
	
	/**
	 * Map method that puts actor index
	 */
	public void addToIndexMap(int position, String name)
	{
		actorIndexMap.put(position, name);
	}
	
	/**
	 * Gets index of the actor
	 */
	public int getIndex(String name)
	{
		if(!actorNamesMap.containsKey(name))
		{
			throw new NullPointerException(name + " not found in index.");
		}
		return actorNamesMap.get(name);
	}
	
	/**
	 * Gets actor name given the index
	 */
	public String getName(int position)
	{
		if(!actorIndexMap.containsKey(position))
		{
			throw new NullPointerException(position + " not found.");
		}
		return actorIndexMap.get(position);
	}
	
	/**
	 * Map fuction that checks to see if map contains String "name" in the key set
	 */
	public boolean containsActor(String name)
	{
		return actorNamesMap.containsKey(name);
	} 
	
	
	
	/**
	 * Returns string of all actors
	 */
	public String printAllActors()
	{
		String result = "";
		for(String key : actorNamesMap.keySet())
		{
			result = result + key + "\n";
		}
		return result;
	}

	

	public void search(String actor1, String actor2)
	{

		int indexOfActor1 = getIndex(actor1);
		int indexOfactor2 = getIndex(actor2);

		boolean[] checked = new boolean[actorNamesMap.size()];


		String nameOfActor1 = getName(indexOfActor1);
		String nameOfActor2 = getName(indexOfactor2);
		Queue<Integer> queue = new LinkedList<Integer>();
		Map<Integer, Integer> path = new HashMap<>();

		if(containsNeighborOfIndex(indexOfActor1, indexOfactor2)) //check if both actors are in the same movie
		{
			sameMovie(nameOfActor1, nameOfActor2);
			return;
		}

		queue.add(indexOfActor1);
		checked[indexOfActor1] = true;
		while(!queue.isEmpty())
		{
			int index = queue.poll();
			Iterator<Integer> it = getNeighbors(index).iterator(); //get all the neighboring vertices from the value dequeued
			while(it.hasNext())
			{
				int current = it.next();
				if(!checked[current])
				{
					checked[current] = true;
					queue.add(current);
					path.put(current, index); //stores the "neighbor" index with the index that was dequeued. This is to keep track of the path
				}

				if (current == indexOfactor2){
					differentNamedMovie(path, current, nameOfActor1, nameOfActor2);
					return;
				}
			}
		}
		System.out.println("path Not found");
	}

	public void sameMovie(String nameOfActor1, String nameOfActor2){

		System.out.println("path between " + nameOfActor1 + " and " + nameOfActor2 + ": ");
		System.out.println(nameOfActor1 + " --> " + nameOfActor2);

	}

	public void differentNamedMovie(Map<Integer, Integer> path, int current, String nameOfActor1, String nameOfActor2){

		String output = "";
		int x = current;
		while(path.get(x) != null) //backtracks through map until it reaches the root actor that was previously dequeued
		{
			output = " --> " + getName(x) + output;
			x = path.get(x);
		}
		System.out.println("Path between " + nameOfActor1 + " and " + nameOfActor2 + ": ");
		System.out.println(nameOfActor1 + output);
	}
}
