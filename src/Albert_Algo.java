package Lab5;

import java.util.LinkedList;

public class Albert_Algo {
	private static Tiles [][] map;
	private static UltrasonicSensor fwd_sensor;
	private static UltrasonicSensor back_sensor;
	private static final double TILEAWAY_0 = 20.0;
	private static final double TILEAWAY_1 = TILEAWAY_0 + 30.0;
	private static final double TILEAWAY_2 = TILEAWAY_1 + 40.0;
	private static LinkedList<Arrow> possibilities = new LinkedList<Arrow>();
	private static LinkedList<PathNode> currentPath;
	private static int counter = 1;
	private static LinkedList<Arrow> memRatio = new LinkedList<Arrow>();
	
	// north = 0
	// west = 1
	// south = 2
	// east = 3
	
	public static void main(String[]args){
		Map maps = new Map(new int[]{1,7,8,14});
		map = maps.getMap();
		fwd_sensor = new UltrasonicSensor(1);
		back_sensor = new UltrasonicSensor(2);
		fwd_sensor.off();
		back_sensor.off();
		
		currentPath = new LinkedList<PathNode>();
		counter = 1;
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				System.out.print(counter + " ");
				for(int k = 0; k < 4; k++){
					if(!map[i][j].getObstacles()[k]){
						Arrow now = map[i][j].getPositionsArrows(k);
						System.out.print(now.getPoint() + ",");
						possibilities.add(now);
					}
				}
				counter++;
				System.out.println("");
			}
		}
	}
	
	public Albert_Algo(int [] blocks_tilenumber){
		
		//Map maps = new Map(blocks_tilenumber);
		Map maps = new Map(new int[]{1,7,8,14});
		map = maps.getMap();
		fwd_sensor = new UltrasonicSensor(1);
		back_sensor = new UltrasonicSensor(2);
		fwd_sensor.off();
		back_sensor.off();
		
		currentPath = new LinkedList<PathNode>();
		counter = 1;
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++){
				for(int k = 0; k < 4; k++){
					if(!map[i][j].getObstacles()[k]){
						Arrow now = map[i][j].getPositionsArrows(k);
						possibilities.add(now);
					}
				}
			}
		}
	}
	
	public boolean seesObject(int wallsAway){
		if(wallsAway < 0) return false;
		return true;
	}
	public int algorithm(){
		String mvtType = null;
		// TODO move forward
		int data_front = 0;
		int data_back = 0;
		while(possibilities.size() > 1){
			data_front = numTilesAway(fwd_getFilteredData());
			// TODO consider a variable move_back for the case where the robot starts and needs to see back
			if(data_front == 0){
				int bestRatio = calculateBestRatio(false);
				if(bestRatio != 1 || bestRatio != 3)
					System.out.println("error");
				else{
					// TODO turn to best ratio
					data_back = numTilesAway(back_getFilteredData());
					
					if(bestRatio == 1){
						mvtType = "turnLeft";
						
					} else if (bestRatio == 3){
						mvtType = "turnRight";
					}
					currentPath.add(new PathNode(mvtType,seesObject(data_front),data_front));
					
					if(mvtType.equals("turnLeft")) mvtType = "turnRight";
					else mvtType = "turnLeft";
					currentPath.add(new PathNode(mvtType,seesObject(data_back),data_back));
					// TODO some method that processes the 1 path node that has been added and that removes
					// it afterwards as it is not part of the robot path.
				}
				//  the robot is currently turned to the new position... no need to change anything				
			} else if(data_front == 1 || data_front == 2){
				int bestRatio = calculateBestRatio(true);
				if(bestRatio != 0 || bestRatio != 1 || bestRatio != 3)
					System.out.println("Error");
				else {
					if(bestRatio == 1 || bestRatio == 3){
						// TODO turn to best ratio
						data_back = numTilesAway(back_getFilteredData());
						
						if(bestRatio == 1){
							mvtType = "turnLeft";
							
						} else if (bestRatio == 3){
							mvtType = "turnRight";
						}
						currentPath.add(new PathNode(mvtType,seesObject(data_front),data_front));
						// TODO some method that processes objects far_away
						if(mvtType.equals("turnLeft")) mvtType = "turnRight";
						else mvtType = "turnLeft";
						currentPath.add(new PathNode(mvtType,seesObject(data_back),data_back));
						// TODO some method that processes the 1 path node that has been added and that removes
						// it afterwards as it is not part of the robot path.
					} else { 
						// TODO go forward
						mvtType = "forward";
						currentPath.add(new PathNode(mvtType, seesObject(data_front), data_front));
						// TODO some method that processes objects far_away
					}
				}
			} else { // move_fwd sees infinity i.e. -1
				int bestRatio = calculateBestRatio(true);
				if(bestRatio != 0 || bestRatio != 1 || bestRatio != 3)
					System.out.println("Error");
				else {
					if(bestRatio == 1 || bestRatio == 3){
						// TODO turn to best ratio
						data_back = numTilesAway(back_getFilteredData());
						
						if(bestRatio == 1){
							mvtType = "turnLeft";
							
						} else if (bestRatio == 3){
							mvtType = "turnRight";
						}
						currentPath.add(new PathNode(mvtType,seesObject(data_front), data_front));
						// TODO some method that processes objects far_away
						if(mvtType.equals("turnLeft")) mvtType = "turnRight";
						else mvtType = "turnLeft";
						currentPath.add(new PathNode(mvtType,seesObject(data_back),data_back));
						// TODO some method that processes the 1 path node that has been added and that removes
						// it afterwards as it is not part of the robot path.
					} else { 
						// TODO go forward
						mvtType = "forward";
						currentPath.add(new PathNode(mvtType, seesObject(data_front), data_front));
						// TODO some method that processes objects far_away
					}
				}
			}
			updatePositions();
			counter++;
		}
		return 0;
	}
	
	public void moveCursor(int move, int direction){
		if(move < 0){
			// move 3 in the direction it is told and come back to its original position
			// crash the cases that are expecting not to be able to move this far
		} else if (move == 0){
			// crash all the cases that could still go forward
		} else if (move == 1){
			// crash all the cases where the cursor could not move forward
			// crash all the cases that could move more than tileaway_1
		} else if (move == 2){
			// crash all the cases where the cursor could only move 1 or 2 tiles
			// crash all the cases that could go more than that
		}
		// NEVER FORGET TO RETURN THE CURSOR WHERE THE ROBOT IS
	}
	
	public static double fwd_getFilteredData(){
		fwd_sensor.ping();
		double fwd_distance = fwd_sensor.getDistance();
		return fwd_distance;
	}
	
	private int numTilesAway(double data){
		if(data < TILEAWAY_0){
			return 0;
		} else if(data < TILEAWAY_1){
			return 1;
		} else if(data < TILEAWAY_2){
			return 2;
		} else {
			return -1;
		}
	}
	
	public static double back_getFilteredData(){
		back_sensor.ping();
		double back_distance = back_sensor.getDistance();
		return back_distance;
	}
	public static double calculateRelativeTurn(){
		return 0.0;
	}
	
	public static double calculateRatios(double current){
		return 0.0;
		
	}
	
	public int calculateBestRatio(boolean canGoForward){
		if(possibilities.size() > 1){
			int pool = possibilities.size();
			String mvt_right = "turnRight";
			String mvt_left = "turnLeft";
			double ratio_fwd = 0.0;
			if(canGoForward){
				String mvt_fwd = "forward";
				PathNode temp_fwd = new PathNode(mvt_fwd, true, 1);
				currentPath.add(temp_fwd);
				updatePositions();
				int poss_fwd = possibilities.size();
				for (int i = memRatio.size() - 1; i >= 0; i--) {
					possibilities.add(memRatio.get(i));
					memRatio.clear();
				}
				currentPath.removeLast();
				ratio_fwd = Math.abs((poss_fwd/pool) - 0.5);
				
			}
			
			PathNode temp_right = new PathNode(mvt_right,true,1);
			PathNode temp_left = new PathNode(mvt_left,true,1);
			
			
			currentPath.add(temp_right);
			updatePositions();
			int poss_right = possibilities.size();
			for(int i = memRatio.size() - 1; i >= 0; i--){
				possibilities.add(memRatio.get(i));
				memRatio.clear();
			}
			currentPath.removeLast();
			
			currentPath.add(temp_left);
			updatePositions();
			int poss_left = possibilities.size();
			for(int i = memRatio.size() - 1; i >= 0; i--){
				possibilities.add(memRatio.get(i));
				memRatio.clear();
			}
			currentPath.removeLast();

			double ratio_left = Math.abs((poss_left/pool) - 0.5);
			double ratio_right = Math.abs((poss_right/pool) - 0.5);
			
			if(canGoForward){
				double min = Math.min(ratio_fwd,
						Math.min(ratio_left, ratio_right));
				if (min == ratio_fwd) {
					return 0;
				} else if (min == ratio_left) {
					return 1;
				} else if (min == ratio_right) {
					return 3;
				}
			} else {
				double min = Math.min(ratio_left, ratio_right);
				if(min == ratio_left) return 1;
				else return 3;
			}
		}
		
		return -1;
	}
		
	public int checkPosition(Arrow pos, int index) {
		PathNode mvt = currentPath.get(currentPath.size() - 1);
		Arrow next = pos.getNext();
		int row = next.getRow();
		int col = next.getColumn();
		
		if(mvt.getMvt().equals("turnLeft")){
			if (next.getPoint() == 'n' && map[row][col].isNorth()) {
				//check west arrow
				if (map[row][col].isWest() == mvt.isObstacleSeen()) {
					pos.setNext(map[row][col].getPositionsArrows(1));
				}
				else {
					return index;
				}
			}
			else if (next.getPoint() == 'w' && map[row][col].isWest()) {
				//check south arrow
				if (map[row][col].isSouth() == mvt.isObstacleSeen()) {
					pos.setNext(map[row][col].getPositionsArrows(2));
				}
				else {
					return index;
				}
			}
			else if (next.getPoint() == 's' && map[row][col].isSouth()) {
				//check east arrow
				if (map[row][col].isEast() == mvt.isObstacleSeen()) {
					pos.setNext(map[row][col].getPositionsArrows(3));
				}
				else {
					return index;
				}
			}
			else if (next.getPoint() == 'e' && map[row][col].isEast()) {
				//check north arrow
				if (map[row][col].isNorth() == mvt.isObstacleSeen()) {
					pos.setNext(map[row][col].getPositionsArrows(0));
				}
				else {
					return index;
				}
			}
			else return index;
		} else if(mvt.getMvt().equals("turnRight")){
			if (next.getPoint() == 'n' && map[row][col].isNorth()) {
				//check west arrow
				if (map[row][col].isEast() == mvt.isObstacleSeen()) {
					pos.setNext(map[row][col].getPositionsArrows(3));
				}
				else {
					return index;
				}
			}
			else if (next.getPoint() == 'w' && map[row][col].isWest()) {
				//check south arrow
				if (map[row][col].isNorth() == mvt.isObstacleSeen()) {
					pos.setNext(map[row][col].getPositionsArrows(0));
				}
				else {
					return index;
				}
			}
			else if (next.getPoint() == 's' && map[row][col].isSouth()) {
				//check east arrow
				if (map[row][col].isWest() == mvt.isObstacleSeen()) {
					pos.setNext(map[row][col].getPositionsArrows(1));
				}
				else {
					return index;
				}
			}
			else if (next.getPoint() == 'e' && map[row][col].isEast()) {
				//check north arrow
				if (map[row][col].isSouth() == mvt.isObstacleSeen()) {
					pos.setNext(map[row][col].getPositionsArrows(2));
				}
				else {
					return index;
				}
			}
			else return index;
		} 
		else{
			if (next.getPoint() == 'n' && !map[row][col].isNorth()) {
				if ((row + 1) < 4 && map[row + 1][col].isNorth() == mvt.isObstacleSeen()) {
					pos.setNext(map[row + 1][col].getPositionsArrows(0));
				}
				else {
					return index;
				}
			} else if (next.getPoint() == 'w' && !map[row][col].isWest()) {
				if ((col - 1) >= 0
						&& map[row][col - 1].isWest() == mvt.isObstacleSeen()) {
					pos.setNext(map[row][col - 1].getPositionsArrows(1));
				} else {
					return index;
				}
			} else if (next.getPoint() == 's' && !map[row][col].isSouth()) {
				if ((row - 1) >= 0
						&& map[row - 1][col].isSouth() == mvt.isObstacleSeen()) {
					pos.setNext(map[row - 1][col].getPositionsArrows(2));
				} else {
					return index;
				}
			} else if (next.getPoint() == 'e' && !map[row][col].isEast()) {
				if ((col + 1) < 4
						&& map[row][col + 1].isEast() == mvt.isObstacleSeen()) {
					pos.setNext(map[row][col + 1].getPositionsArrows(3));
				} else {
					return index;
				}
			} else
				return index;
		}
	return -1;
	}
	
	public void newPosition (Arrow startPos) {
		//go through all the nodes of the currentPath
		//update position based on starting one
		PathNode mvt;
		int row, col;
		for (int i = 0; i < currentPath.size(); i++) {
			mvt = currentPath.get(i);
			if (mvt.getMvt().equals("turnLeft")) {
				if (startPos.getPoint() == 'n') {
					startPos.setPoint('w');
				}
				else if (startPos.getPoint() == 'w') {
					startPos.setPoint('s');
				}
				else if (startPos.getPoint() == 's') {
					startPos.setPoint('e');
				}
				else if (startPos.getPoint() == 'e') {
					startPos.setPoint('n');
				}
			} else if(mvt.getMvt().equals("turnRight")){
				if (startPos.getPoint() == 'n') {
					startPos.setPoint('e');
				}
				else if (startPos.getPoint() == 'w') {
					startPos.setPoint('n');
				}
				else if (startPos.getPoint() == 's') {
					startPos.setPoint('w');
				}
				else if (startPos.getPoint() == 'e') {
					startPos.setPoint('s');
				}
			}
			else { //mvt.getMvt().equals("forward")
				if (startPos.getPoint() == 'n') {
					//movement along the positive x-axis
					//increment row
					row = startPos.getRow();
					startPos.setRow(row + 1);
				}
				else if (startPos.getPoint() == 'w') {
					//movement along the positive y-axis
					//decrement column
					col = startPos.getColumn();
					startPos.setColumn(col - 1);
				}
				else if (startPos.getPoint() == 's') {
					//movement along the negative x-axis
					//decrement row
					row = startPos.getRow();
					startPos.setRow(row - 1);
				}
				else if (startPos.getPoint() == 'e') {
					//movement along the negative y-axis
					//increment column
					col = startPos.getColumn();
					startPos.setColumn(col + 1);
				}
			}
		}
	}
	
	public void updatePositions() {
		Arrow cur;
		LinkedList<Integer> itemsToRemove = new LinkedList<Integer>();
		int ind;
		memRatio.clear();
		for (int i = 0 ; i < possibilities.size() ; i++) {	//for all possible arrows, check if the new position of the robot matches 
			cur = possibilities.get(i);
			ind = checkPosition(cur, i);
			
			if (ind != -1) {
				itemsToRemove.add(ind);
			}
		}
		int error = 0;
		for (Integer j : itemsToRemove) {	//remove position which can't be the starting one 
			memRatio.add(possibilities.get(j-error)); // will get all the elements from last to some element i of possibilities
			possibilities.remove(j - error);
			error++;
		}
	}
	
	public void take_data(int data){
		
	}
}
