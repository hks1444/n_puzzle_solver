
package project;

import java.util.Stack;


public class Puzzle {
	private final int[][] tiles;
	/**
	 * Constructor of the Puzzle class. Fills up the int[][] tile field according to the given 2D array.
	 * @param tiles
	 */
	public Puzzle(int[][] tiles) {
		this.tiles = copy(tiles);
	}
	/**
	 * Overrides the toString() method. Prints out the Puzzle objects to the output file.
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(tiles.length + "\n");
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				str.append(" "+tiles[i][j]);
			}
			str.append("\n");
		}
		return str.toString();

	}

	public int dimension() {
		return tiles.length;
	}
	
	/**
	 * checks whether this puzzle's board and puzzle a's board is same. 
	 * @param a
	 * @return true/false
	 */
	public boolean equals(Puzzle a) {
		for(int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				if(this.tiles[i][j]!=a.tiles[i][j]) {
					return false;
				}
			}
			
			
		}
		return true;
	}


	// sum of Manhattan distances between tiles and goal
	// The Manhattan distance between a board and the goal board is the sum
	// of the Manhattan distances (sum of the vertical and horizontal distance)
	// from the tiles to their goal positions.
	/**
	 * : This function is used to calculate Heuristic value. Calculates the vertical and horizontal distances between each tile and goal positions. 
	 * Returns the summation of distances.
	 * @return summation of distances
	 */
	public int h() {
		int dist = 0;
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				if(tiles[i][j]!=0) {
				dist += Math.abs(i-(tiles[i][j]-1)/tiles.length)+Math.abs(j-(tiles[i][j]-1)%tiles.length);
			}}
			
			
		}
		return dist;
	}
	/**
	 *  Return true if current state is the same as the goal state, otherwise false
	 * @return true/false
	 */
	public boolean isCompleted() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				if(tiles[i][j]!=0) {
				if(tiles[i][j]!=tiles.length*i+j+1) {
					return false;
				}
				}else {
					if(i!=tiles.length-1||j!=tiles.length-1) {
						return false;
					}
				}
			}
			
			
		}
		return true;
	}


	/**
	 * This function returns all adjacent states to the current state of the puzzle. 
	 * Examines the current int[][] tile, and creates new Puzzle objects that are adjacent to the current state. 
	 * @return adjacent states
	 */
	public Iterable<Puzzle> getAdjacents() {
		Stack <Puzzle> adj = new Stack<Puzzle>();
		int a=0,b=0;
		for (int i = 0; i < tiles.length; i++) {//finds 0
			for (int j = 0; j < tiles[i].length; j++) {
				if(tiles[i][j]==0) {
					a = i;
					b= j;
					break;
				}
			}		
		}
		//checks all four possibilities
		if(a>0) {
			int[][] temp = this.copy(tiles);
			int tmp= temp[a][b];
			temp[a][b]= temp[a-1][b];
			temp[a-1][b]= tmp;
			adj.push(new Puzzle(temp));
		}
		if(a<tiles.length-1) {
			int[][] temp = this.copy(tiles);
			int tmp= temp[a][b];
			temp[a][b]= temp[a+1][b];
			temp[a+1][b]= tmp;
			adj.push(new Puzzle(temp));
			
		}
		if(b>0) {
			int[][] temp = this.copy(tiles);
			int tmp= temp[a][b];
			temp[a][b]= temp[a][b-1];
			temp[a][b-1]= tmp;
			adj.push(new Puzzle(temp));
		}
		if(b<tiles.length-1) {
			int[][] temp = this.copy(tiles);
			int tmp= temp[a][b];
			temp[a][b]= temp[a][b+1];
			temp[a][b+1]= tmp;
			adj.push(new Puzzle(temp));
		}
		return adj;
	}
	/**
	 * Copies the content of the given array to int[][] tile.
	 * @param source
	 * @return copy of source
	 */
	private int[][] copy(int[][] source) {
		int[][] lst = new int[source.length][source.length];
		for(int i =0;i<source.length;i++) {
			for(int j =0;j<source.length;j++) {
				lst[i][j]=source[i][j];
			}
		}
		return lst;
	}


	// You can use this main method to see your Puzzle structure.
	// Actual solving operations will be conducted in Solver.main method
//	public static void main(String[] args) {
//		int[][] array = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
//		Puzzle board = new Puzzle(array);
//		System.out.println(board);
//		System.out.println(board.dimension());
//		System.out.println(board.h());
//		System.out.println(board.isCompleted());
//		Iterable<Puzzle> itr = board.getAdjacents();
//		for (Puzzle neighbor : itr) {
//			System.out.println(neighbor);
//			System.out.println(neighbor.equals(board));
//		}
//	}
}

