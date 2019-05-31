package friends;

import structures.Queue;
import structures.Stack;

import java.util.*;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		if(p1.equals(null) || p2.equals(null)) {
			return null;
		}
		String name1 = p1.toLowerCase();
		String name2 = p2.toLowerCase();
		
		Queue<Person> people = new Queue<>();
		int graphSize = g.members.length;
		
		//List of all the possible paths
		Queue<ArrayList<String>> connections = new Queue<>();
		ArrayList<String> list1 = new ArrayList<>();
		//Checklist for if that node has already been visited
		boolean[] visit = new boolean[graphSize];
		Person person1 = g.members[g.map.get(name1)];
		Person person2 = g.members[g.map.get(name2)];
		people.enqueue(person1);
		//Initially set all visited values to false
		int x = 0;
		while(x < visit.length) {
			visit[x] = false;
			x++;
		}
		int index1 = g.map.get(person1.name);
		list1.add(g.members[index1].name);
		connections.enqueue(list1);
		//Create multiple lists for each path of friends
		//Adds one entry to each list every loop
		//First one to finish will be the shortest path
		while(!people.isEmpty()) {
			Person current = people.dequeue();
			int index = g.map.get(current.name);
			visit[index] = true;
			ArrayList<String> temp = connections.dequeue();
			Friend next = g.members[index].first;
			while(next != null) {
				if(visit[next.fnum] == false) {
					ArrayList<String> list = new ArrayList<>(temp);
					list.add(g.members[next.fnum].name);
					if(g.members[next.fnum].name.equals(name2)) {
						return list;
					}
					visit[next.fnum] = true;
					people.enqueue(g.members[next.fnum]);
					connections.enqueue(list);
				}
				next = next.next;
			}
		}
		//If no connection between the two entries is ever found then the result will be null
		return null;
	}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		//Initialize results list and list of whether or not node has been visited
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		boolean[] visit = new boolean[g.members.length];
		//Set all values in the boolean array to false
		for(int i = 0; i < visit.length; i++) {
			visit[i] = false;
		}
		int x = 0;
		while(x < g.members.length) {
			Person temp = g.members[x];
			if(temp.school == null) {
				x++;
				continue;
			}
			int index = g.map.get(temp.name);
			if(temp.school.equals(school) && visit[index] == false) {
				ArrayList<String> clique = new ArrayList<String>();
				cliqueSearch(g, visit, temp, school, clique);
				result.add(clique);
			}
			x++;
			
		}
		return result;
	}
	private static void cliqueSearch(Graph g, boolean[] visit, Person temp, String school, ArrayList<String> clique) {
		//BFS Traversal
		//Create queue of people in the clique
		Queue<Person> people = new Queue<>();
		people.enqueue(temp);
		//Begin the BFS Search
		while(!people.isEmpty()) {
			Person current = people.dequeue();
			clique.add(current.name);
			visit[g.map.get(current.name)] = true;
			Friend friend = current.first;
			//Go through each friend and if they share the same school then they get added to the queue
			while(friend != null) {
				if(g.members[friend.fnum].school == null ||!g.members[friend.fnum].school.equals(school)){
					friend = friend.next;
					continue;
				}
				if(visit[friend.fnum] == false && g.members[friend.fnum].school.equals(school)) {
					people.enqueue(g.members[friend.fnum]);
					visit[friend.fnum] = true;
				}
				friend = friend.next;
			}
		}
	}
	
	
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		ArrayList<String> result = new ArrayList<String>();
		boolean[] visit = new boolean[g.members.length];
		boolean[] backOut = new boolean[g.members.length];
		int[] nums = new int[g.members.length];
		int[] back = new int[g.members.length];
		for(int x = 0; x< g.members.length; x++) {
			if(visit[x] == false) {
				DFS(g, x, visit, backOut, nums, back, result, x, x);
			}
		}
		return result;
	}
	private static void DFS(Graph g, int y, boolean[] visit, boolean[] backOut, int[] nums, int[] back, ArrayList<String> result, int previous, int first) {
		if(visit[y] == true) {
			return;
		}
		visit[y] = true;
		nums[y] = nums[previous]+1;
		back[y] = nums[y];
		Friend friend = g.members[y].first;
		while(friend != null) {
			int friendNumber = friend.fnum;
			if(visit[friendNumber] == false) {
				DFS(g, friendNumber, visit, backOut, nums, back, result, y, first);
				if(!result.contains(g.members[y].name)) {
					if(nums[y] <= back[friendNumber]) {
						if(y != first || backOut[y] == true) {
							result.add(g.members[y].name);
						}
					}
				}
				if(back[friend.fnum] < nums[y]) {
					back[y] = Math.min(back[y], back[friendNumber]);
					}
				backOut[y] = true;
				}
			else {
				back[y] = Math.min(back[y], nums[friendNumber]);
			}
			friend = friend.next;
		}
	}
	
	
}

