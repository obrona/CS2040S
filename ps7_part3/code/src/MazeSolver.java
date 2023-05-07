import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;

public class MazeSolver implements IMazeSolver {
	private static final int TRUE_WALL = Integer.MAX_VALUE;
	private static final int EMPTY_SPACE = 0;
	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(
			Room::getNorthWall,
			Room::getEastWall,
			Room::getWestWall,
			Room::getSouthWall
	);
	private static final int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 0, 1 }, // East
			{ 0, -1 }, // West
			{ 1, 0 } // South
	};

	private Maze maze;
	public Room2[][] rooms;

	public class Room2 implements Comparable<Room2> {
		int row;
		int col;
		boolean inShortestTree;
		int estimate = Integer.MAX_VALUE;

		public Room2(int r, int c) {
			row = r;
			col = c;
			inShortestTree = false;
		}

		public int compareTo(Room2 o) {
			return this.estimate - o.estimate;
		}

		public String toString() {
			return row + " " + col + " " + inShortestTree;
		}
	}


	public class Room2PriorityPair implements Comparable<Room2PriorityPair> {
		public int value;
		public Room2 room;

		public int compareTo(Room2PriorityPair o) {
			return this.value - o.value;
		}

		public Room2PriorityPair(Room2 r, int value) {
			this.room = r;
			this.value = value;
		}


	}

	public MazeSolver() {
		// TODO: Initialize variables.
	}

	@Override
	public void initialize(Maze maze) { this.maze = maze; }

	public void reset() {
		rooms = new Room2[maze.getRows()][maze.getColumns()];
		for (int i = 0; i < maze.getRows(); i ++) {
			for (int j = 0; j < maze.getColumns(); j ++) {
				rooms[i][j] = new Room2(i, j);
			}
		}
	}

	public boolean canGo(int delta, Room2 r) {
		if (delta == 0) {
			return maze.getRoom(r.row, r.col).getNorthWall() < Integer.MAX_VALUE;
		} else if (delta == 1) {
			return maze.getRoom(r.row, r.col).getEastWall() < Integer.MAX_VALUE;
		} else if (delta == 2) {
			return maze.getRoom(r.row, r.col).getWestWall() < Integer.MAX_VALUE;
		} else if (delta == 3){
			return maze.getRoom(r.row, r.col).getSouthWall() < Integer.MAX_VALUE;
		} else {
			return false;
		}
	}

    public int getWall(int delta, Room2 r) {
		if (delta == 0) {
			int v = maze.getRoom(r.row, r.col).getNorthWall();
			return v == 0 ? 1 : v;
		} else if (delta == 1) {
			int v = maze.getRoom(r.row, r.col).getEastWall();
			return v == 0 ? 1 : v;
		} else if (delta == 2) {
			int v = maze.getRoom(r.row, r.col).getWestWall();
			return v == 0 ? 1 : v;
		} else if (delta == 3) {
			int v = maze.getRoom(r.row, r.col).getSouthWall();
			return v == 0 ? 1 : v;
		} else {
			return Integer.MAX_VALUE;
		}
	}





	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		this.reset();
		PriorityQueue<Room2PriorityPair> queue = new PriorityQueue<>();
		queue.add(new Room2PriorityPair(rooms[startRow][startCol], 0));
		while(!queue.isEmpty()) {
			Room2PriorityPair currPair = queue.poll();
			Room2 curr = currPair.room;
			if (!curr.inShortestTree) {
				curr.estimate = currPair.value;
				curr.inShortestTree = true;
				for (int i = 0; i < 4; i++) {
					if (canGo(i, curr)) {
						Room2 r = rooms[curr.row + DELTAS[i][0]][curr.col + DELTAS[i][1]];
						if (!r.inShortestTree) {
							int newEstimate = Math.min(r.estimate, curr.estimate + getWall(i, curr));
							queue.add(new Room2PriorityPair(r, newEstimate));
						}
					}
				}
			}
		}
		return rooms[endRow][endCol].inShortestTree ? rooms[endRow][endCol].estimate : null;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		this.reset();
		PriorityQueue<Room2PriorityPair> queue = new PriorityQueue<>();
		queue.add(new Room2PriorityPair(rooms[startRow][startCol], 0));
		while(!queue.isEmpty()) {
			Room2PriorityPair currPair = queue.poll();
			Room2 curr = currPair.room;
			if (!curr.inShortestTree) {
				curr.estimate = currPair.value;
				curr.inShortestTree = true;
				for (int i = 0; i < 4; i++) {
					if (canGo(i, curr)) {
						Room2 r = rooms[curr.row + DELTAS[i][0]][curr.col + DELTAS[i][1]];
						if (!r.inShortestTree) {
							int newEstimate = r.estimate;
							if (getWall(i, curr) == 1) {
								newEstimate = Math.min(newEstimate, curr.estimate + 1);
							} else {
								newEstimate = Math.min(newEstimate, Math.max(curr.estimate, getWall(i,curr)));
							}
							queue.add(new Room2PriorityPair(r, newEstimate));
						}
					}
				}
			}
		}
		return rooms[endRow][endCol].inShortestTree ? rooms[endRow][endCol].estimate : null;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {
		this.reset();
		boolean isSpecialRoomConnected = false;
		PriorityQueue<Room2PriorityPair> queue = new PriorityQueue<>();
		queue.add(new Room2PriorityPair(rooms[startRow][startCol], 0));
		while(!queue.isEmpty()) {
			Room2PriorityPair currPair = queue.poll();
			Room2 curr = currPair.room;
			if (curr.row == sRow && curr.col == sCol) {
				isSpecialRoomConnected = true;
			}
			if (!curr.inShortestTree) {
				curr.estimate = currPair.value;
				curr.inShortestTree = true;
				for (int i = 0; i < 4; i++) {
					if (canGo(i, curr)) {
						Room2 r = rooms[curr.row + DELTAS[i][0]][curr.col + DELTAS[i][1]];
						if (!r.inShortestTree) {
							int newEstimate = r.estimate;
							if (getWall(i, curr) == 1) {
								newEstimate = Math.min(newEstimate, curr.estimate + 1);
							} else {
								newEstimate = Math.min(newEstimate, Math.max(curr.estimate, getWall(i,curr)));
							}
							queue.add(new Room2PriorityPair(r, newEstimate));
						}
					}
				}
			}
		}


		Integer noSpecialRoomDistance = rooms[endRow][endCol].inShortestTree ? rooms[endRow][endCol].estimate : null;
		Integer withSpecialRoom = null;
        //System.out.println(isSpecialRoomConnected);

		if (isSpecialRoomConnected) {
			this.reset();
			PriorityQueue<Room2PriorityPair> queue2 = new PriorityQueue<>();
			queue2.add(new Room2PriorityPair(rooms[sRow][sCol], -1));
			while(!queue2.isEmpty()) {
				Room2PriorityPair currPair = queue2.poll();
				Room2 curr = currPair.room;
				if (!curr.inShortestTree) {
					curr.estimate = currPair.value;
					curr.inShortestTree = true;
					for (int i = 0; i < 4; i++) {
						if (canGo(i, curr)) {
							Room2 r = rooms[curr.row + DELTAS[i][0]][curr.col + DELTAS[i][1]];
							if (!r.inShortestTree) {
								int newEstimate = r.estimate;
								if (getWall(i, curr) == 1) {
									newEstimate = Math.min(newEstimate, curr.estimate + 1);
								} else {
									newEstimate = Math.min(newEstimate, Math.max(curr.estimate, getWall(i,curr)));
								}
								queue2.add(new Room2PriorityPair(r, newEstimate));
							}
						}
					}
				}
			}
		withSpecialRoom = rooms[endRow][endCol].inShortestTree ? rooms[endRow][endCol].estimate : null;
		}
		//System.out.println(rooms[endRow][endCol].inShortestTree);
		if (noSpecialRoomDistance == null) {
			return null;
		} else {
			if (withSpecialRoom == null) {
				return noSpecialRoomDistance;
			} else {
				return Math.min(noSpecialRoomDistance, withSpecialRoom);
			}
		}
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("haunted-maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 1,5));
			System.out.println(solver.bonusSearch(0,0,1,5));
			System.out.println(solver.bonusSearch(0,0,1,5,1,3));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
