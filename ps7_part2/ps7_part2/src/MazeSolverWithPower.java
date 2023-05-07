import java.util.Arrays;
import java.util.LinkedList;

public class MazeSolverWithPower implements IMazeSolverWithPower {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	public Room2[][][] rooms;
	public Maze maze;
	public int[] distanceFreqStore;



	public class Room2 {
		public int row;
		public int col;
		public Room2 parent;
		public boolean visited;
		public int dist;
		public int noOfPower;

		public Room2(int r, int c, int p) {
			row = r;
			col = c;
			noOfPower = p;
			visited = false;
			dist = -1;
		}

		public String toString() {
			return row + " " + col + " " + noOfPower + " " + visited + " " + dist;
		}
	}

	public MazeSolverWithPower() {
		// TODO: Initialize variables.
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
		distanceFreqStore = new int[maze.getRows() + maze.getColumns() + 20];
	}

	public void clearEverything(int n) {
		// n: superpower count
		rooms = new Room2[maze.getRows()][maze.getColumns()][n + 1];
        distanceFreqStore = new int[maze.getRows() + maze.getColumns() + 20];
		for (int i = 0; i < maze.getRows(); i ++) {
			for (int j = 0; j < maze.getColumns(); j ++) {
				for (int k = 0; k <= n; k ++) {
					rooms[i][j][k] = new Room2(i, j, k);
				}
			}
		}
	}

    public void storeDistance(int n) {
		for (int i = 0; i < maze.getRows(); i ++) {
			for (int j = 0; j < maze.getColumns(); j ++) {
				int shortest = Integer.MAX_VALUE;
				boolean actuallyVisited = false;
				for (int k = 0; k <= n; k ++) {
					if (rooms[i][j][k].visited) {
                        actuallyVisited = true;
						if (rooms[i][j][k].dist < shortest) {
							shortest = rooms[i][j][k].dist;
						}
					}
				}
				if (actuallyVisited) {
					distanceFreqStore[shortest] ++;
				}
			}
		}
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		return pathSearch(startRow, startCol, endRow, endCol, 0);
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		return distanceFreqStore[k];
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow,
							  int endCol, int superpowers) throws Exception {
		this.clearEverything(superpowers);
		rooms[startRow][startCol][superpowers].dist = 0;
		LinkedList<int[]> queue = new LinkedList<>();
        queue.add(new int[] {startRow, startCol, superpowers});
		while (!queue.isEmpty()) {


			int[] coordinate = queue.poll();
			int row = coordinate[0];
			int col = coordinate[1];
			int powerCount = coordinate[2];
			Room2 currRoom = rooms[row][col][powerCount];


			int distance = currRoom.dist;
            if (!currRoom.visited) {
				currRoom.visited = true;
				for (int i = 0; i < 4; i ++) {
					if (i == 0 && row > 0) {
						if (maze.getRoom(row, col).hasNorthWall() && powerCount > 0) {
							if (!rooms[row - 1][col][powerCount - 1].visited) {
								Room2 r = rooms[row - 1][col][powerCount - 1];
								r.dist = r.dist == -1 ? distance + 1 : r.dist;
								r.parent = r.parent == null ? currRoom : r.parent;
								queue.add(new int[]{row - 1, col, powerCount - 1});
							}
						} else if (!maze.getRoom(row, col).hasNorthWall()) {
							if (!rooms[row - 1][col][powerCount].visited) {
								Room2 r = rooms[row - 1][col][powerCount];
								r.dist = r.dist == -1 ? distance + 1 : r.dist;
								r.parent = r.parent == null ? currRoom : r.parent;
								queue.add(new int[]{row - 1, col, powerCount});
							}
						}
					} else if (i == 1 && row < maze.getRows() - 1) {
						if (maze.getRoom(row, col).hasSouthWall() && powerCount > 0) {
							if (!rooms[row + 1][col][powerCount - 1].visited) {
								Room2 r = rooms[row + 1][col][powerCount - 1];
								r.dist = r.dist == -1 ? distance + 1 : r.dist;
								r.parent = r.parent == null ? currRoom : r.parent;
								queue.add(new int[] {row + 1, col, powerCount - 1});
							}
						} else if (!maze.getRoom(row, col).hasSouthWall()) {
							if (!rooms[row + 1][col][powerCount].visited) {
								Room2 r = rooms[row + 1][col][powerCount];
								r.dist = r.dist == -1 ? distance + 1 : r.dist;
								r.parent = r.parent == null ? currRoom : r.parent;
								queue.add(new int[] {row + 1, col, powerCount});
							}
						}
					} else if (i == 2 && col < maze.getColumns() - 1) {
						if (maze.getRoom(row, col).hasEastWall() && powerCount > 0) {
							if (!rooms[row][col + 1][powerCount - 1].visited) {
								Room2 r = rooms[row][col + 1][powerCount - 1];
								r.dist = r.dist == -1 ? distance + 1 : r.dist;
								r.parent = r.parent == null ? currRoom : r.parent;
								queue.add(new int[] {row, col + 1, powerCount - 1});
							}
						} else if (!maze.getRoom(row, col).hasEastWall()) {
							if (!rooms[row][col + 1][powerCount].visited) {
								Room2 r = rooms[row][col + 1][powerCount];
								r.dist = r.dist == -1 ? distance + 1 : r.dist;
								r.parent = r.parent == null ? currRoom : r.parent;
								queue.add(new int[] {row, col + 1, powerCount});
							}
						}
					} else if (i == 3 && col > 0) {
						if (maze.getRoom(row, col).hasWestWall() && powerCount > 0) {
							if (!rooms[row][col - 1][powerCount - 1].visited) {
								Room2 r = rooms[row][col - 1][powerCount - 1];
								r.dist = r.dist == -1 ? distance + 1 : r.dist;
								r.parent = r.parent == null ? currRoom : r.parent;
								queue.add(new int[] {row, col - 1, powerCount - 1});
							}
						} else if (!maze.getRoom(row, col).hasWestWall()) {
							if (!rooms[row][col - 1][powerCount].visited) {
								Room2 r = rooms[row][col - 1][powerCount];
								r.dist = r.dist == -1 ? distance + 1 : r.dist;
								r.parent = r.parent == null ? currRoom : r.parent;
								queue.add(new int[] {row, col - 1, powerCount});
							}
						}
					}
				}
			}
		}

		boolean actuallyVisited = false;
        int shortestDistance = Integer.MAX_VALUE;
		int index = -1;
		for (int k = 0; k <= superpowers; k ++) {
			if (rooms[endRow][endCol][k].visited) {
				index = rooms[endRow][endCol][k].dist < shortestDistance ? k : index;
				shortestDistance = Math.min(shortestDistance, rooms[endRow][endCol][k].dist);
				actuallyVisited = true;

			}
		}

		if (actuallyVisited) {
			Room2 curr = rooms[endRow][endCol][index];
			while (true) {
				if (curr.row == startRow && curr.col == startCol) {
					maze.getRoom(startRow, startCol).onPath = true;
					break;
				} else {
					maze.getRoom(curr.row, curr.col).onPath = true;
					curr = curr.parent;
				}
			}
		}

		this.storeDistance(superpowers);

		return actuallyVisited ? shortestDistance : null;
	}



	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 3, 3, 3));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
