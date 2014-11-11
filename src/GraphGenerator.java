import java.util.ArrayList;
import java.util.LinkedList;


public class GraphGenerator {
	private Tiles[][] tile;
	private ArrayList<LinkedList<Tiles>> graph;

	public GraphGenerator(Map map) {
		tile = map.getTiles();
		graph = new ArrayList<LinkedList<Tiles>>();
	}

	public ArrayList<LinkedList<Tiles>> createGraph() {	//unfinished
		int rows = tile.length;
		int col = tile[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < col; j++) {
				// if tile[i][j] is not an obstacle
				if (i == 0) {

				}
				else if (i == rows - 1) {
					
				}
				else if (j == 0) {
					
				}
				else if (j == col - 1) {
					
				}
				else if (!tile[i][j].getIsObstacle()) {
					// add neighbors that aren't obstacles to adjacency list 
					if (!tile[i - 1][j].getIsObstacle()) {
						tile[i][j].adjList.add(tile[i - 1][j]);
					}

					if (!tile[i + 1][j].getIsObstacle()) {
						tile[i][j].adjList.add(tile[i + 1][j]);
					}

					if (!tile[i][j - 1].getIsObstacle()) {
						tile[i][j].adjList.add(tile[i][j - 1]);
					}

					if (!tile[i][j + 1].getIsObstacle()) {
						tile[i][j].adjList.add(tile[i-1][j + 1]);
					}
				}

			}
		}
		
		return null;
	}
}
