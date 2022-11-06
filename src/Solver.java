
package project;
   
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solver {
	private PriorityObject winner;


	// priority = moves + manhattan
	// if priority is low, it's good.
	// find a solution to the initial board
	/**
	 * This constructs the Solver class. Throws an IllegalArgumentException if root is null, otherwise calls the Solve(root) function to solve the puzzle.
	 * @param root
	 */
	public Solver(Puzzle root) {
		//System.out.println("Starting the solver...");
		if (root == null)
			throw new IllegalArgumentException();
		solve(root);
		//System.out.println("Solving is finished...");
	}


	/**
	 * This function is where we solve the problem.
Stores each state in a priority queue and
takes states from priority queue with minimum f = g + h heuristic values.
If the recently popped state is final state, finishes the execution.
	 * @param root
	 */
	private void solve(Puzzle root) {
		PriorityQueue<PriorityObject> abc = new PriorityQueue<>(new CustomComparator());
		PriorityObject xyz = new PriorityObject(root, 0, null);
		abc.add(xyz);
		while(!(xyz.getBoard().isCompleted()))
		{ 	
			for(Puzzle x:xyz.getBoard().getAdjacents()) {
				if(xyz.getPrev() != null) {

				if(!xyz.getPrev().getBoard().equals(x)) {
			abc.add(new PriorityObject(x, xyz.g+1, xyz));
				}
				}else {
					abc.add(new PriorityObject(x, xyz.g+1, xyz));
				}
				}
			xyz = abc.poll();
		}
		this.winner=xyz;
		
	}
/**
 * returns minimum number of moves for the solution.
 * @return number of moves
 */
	public int getMoves() {
		return this.winner.getG();
	}
/**
 * This function will be called from the main after Solve(root) function is called. Returns an Iterable that consists of all Puzzle objects from initial state to final state. Collects all
 * states by back traversing from final state to the initial state using
 * private PriorityObject prev field. First element of the Iterable is the the Puzzle object that corresponds to the initial state.
 * @return
 */
	public Iterable<Puzzle> getSolution() {
		ArrayList<Puzzle> sol = new ArrayList<>();
		PriorityObject sol2 = this.winner;
		while(sol2.getPrev()!=null) {
			sol.add(0,sol2.getBoard());
			sol2 = sol2.getPrev();
		}
		sol.add(0,sol2.getBoard());
		return sol;
	}
/**
 * Stores each state in a priority queue and
takes states from priority queue with minimum f = g + h heuristic values.
If the recently popped state is final state, finishes the execution. Stores final state (final PriorityObject) in a private field to use it in
other functions. If an adjacent state of an Object is its previous state, that state is not pushed to the Priority Queue.
 * @author Hasan Kerem Seker
 *
 */
	private class PriorityObject {
		/**
		 * Each PriorityObject actually corresponds to a state (Puzzle).
		 */
		private Puzzle board;
		protected Puzzle getBoard() {
			return board;
		}
		protected int getF() {
			return f;
		}
		protected int getG() {
			return g;
		}
		protected PriorityObject getPrev() {
			return prev;
		}
		/**
		 * f will be used to order the puzzles.
		 */
		private int f;
		/**
		 * stores the previous PriorityObject in this field. Whenever a PriorityObject is created, it stores its previous PriorityObject.
		 */
		private PriorityObject prev;
		/**
		 * denotes the cost from initial state to this state, that is,
number of transitions between the states.
		 */
		private int g;
		/**
		 * Constructor assigns the fields with the given parameters. Calculates f as f = g + Taxicab Distance.
		 * @param board
		 * @param g
		 * @param prev
		 */
		PriorityObject(Puzzle board, int g, PriorityObject prev){
			this.board = board;
			this.prev = prev;
			this.g = g;
			this.f = this.getBoard().h()+this.getG();
		}
		
		

		
	}
	/**
	 * This class is for overriding comparator of Priority Queue.
	 * @author Hasan Kerem Seker
	 *
	 */
	private class CustomComparator implements Comparator<PriorityObject>{
		/**
		 * Overrides the compare method of Comparator<PriorityObject>.
		 */
		public int compare(PriorityObject o1, PriorityObject o2) {
			if(o1.getF()>o2.getF()) {
				return 1;
			}else if(o1.getF()<o2.getF()) {
				return -1;
			}else {
				if(o1.getG()<o2.getG()) {
					return 1;
				}else if(o1.getG()>o2.getG()){
					return -1;
				}else {
					return 0;
				}
			}
		}
	}
	/**
	 * This is the part where input is read and puzzle is solved. Then the solution is outputted to a file.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		File input = new File("input.txt");
		Scanner aa = new Scanner(input);
		int num = aa.nextInt();
		int[][]tiles = new int[num][num];
		
		aa.nextLine();
		for(int i =0;i<num;i++) {
			String[] line = aa.nextLine().split(" ");
			for(int j =0;j<num;j++) {
				tiles[i][j]=Integer.parseInt(line[j]);
			}
		}
		Puzzle initial = new Puzzle(tiles);
		
		// Read this file int by int to create 
		// the initial board (the Puzzle object) from the file
		
		
		// solve the puzzle here. Note that the constructor of the Solver class already calls the 
		// solve method. So just create a solver object with the Puzzle Object you created above 
		// is given as argument, as follows:
		
		Solver solver = new Solver(initial);  // where initial is the Puzzle object created from input file

		// You can use the following part as it is. It creates the output file and fills it accordingly.
		File output = new File("output.txt");
		output.createNewFile();
		PrintStream write = new PrintStream(output);
		write.println("Minimum number of moves = " + solver.getMoves());
		for (Puzzle board : solver.getSolution()) {
			write.println(board);}
		write.close();
		aa.close();
		
		
		
	}
}

