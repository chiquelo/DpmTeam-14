import java.util.ArrayList;


public class GraphGenerator {
	private Tiles[][] tile;
	private ArrayList<Tiles> graph;

	public GraphGenerator(Map map) {
		tile = map.getTiles();
		graph = new ArrayList<Tiles>();
	}

	public void createGraph() {	//unfinished
		int rows = tile.length;
		int col = tile[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < col; j++) {
				// if tile[i][j] is not an obstacle
				if (!tile[i][j].getIsObstacle()) {
					// add neighbors that aren't obstacles to adjacency list of the tile
					if (i != 0 && !tile[i - 1][j].getIsObstacle()) {
						tile[i][j].adjList.add(tile[i - 1][j]);
					}

					if (i != rows - 1 && !tile[i + 1][j].getIsObstacle()) {
						tile[i][j].adjList.add(tile[i + 1][j]);
					}

					if (j != 0 && !tile[i][j - 1].getIsObstacle()) {
						tile[i][j].adjList.add(tile[i][j - 1]);
					}

					if (j != col - 1 && !tile[i][j + 1].getIsObstacle()) {
						tile[i][j].adjList.add(tile[i-1][j + 1]);
					}
				}
				
				// add tile to ArrayList<Tile> which represents the graph as an adjacency list
				graph.add(tile[i][j]);
			}
		}
	}
	
	public void printGraph() {
		for (int i = 0; i < graph.size(); i++) {
			for ( Tiles t : graph.get(i).adjList) {
				System.out.println("For tile "+ graph.get(i).getTileNumber() +  " " + t.getTileNumber());
			}
		}
	}
}
