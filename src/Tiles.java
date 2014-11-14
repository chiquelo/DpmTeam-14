package Lab5;

public class Tiles {
	
	private int x;
	private int y;
	
	private static Arrow[] positions;
	
	//variable keeping track whether there are hasBlock in the 4 tiles surrounding current tile
	private boolean [] hasBlock = new boolean[4];
	//tile number from 1 to max size of board
	private int tileNumber;
	//variable keeping track whether or not tile is an obstacle
	private boolean isObstacle = false;
	// arrows of tiles
	
	public Tiles(int tileNumber, boolean isObstacle){
		this.tileNumber = tileNumber;
		this.isObstacle = isObstacle;
		for(int i=0; i<4; i++){
			this.hasBlock[i] = false;
		}
		int count = 1;
		for(int i = 0; i < 4; i++){
			for(int j = 0; j<4; j++){
				if(tileNumber == count){
					this.x = j;
					this.y = i;
				}
				count++;
			}
		}
	}
	
	public boolean[] getObstacles(){
		return hasBlock;
	}
	
	
	public void setObstacles(int index, boolean yolo){
		if(index < 4){
			hasBlock[index] = yolo;
		}
		else return;
	}
	
	public int getTileNumber(){
		return tileNumber;
	}
	
	public boolean getIsObstacle(){
		return isObstacle;
	}
	public void setBlock(){
		isObstacle = true;
		for(int i = 0; i < 4 ; i++){
			hasBlock[i] = true;
		}
	}
	
	public String coordinate(int index){
		if(index == 0)
			return "North";
		if(index == 1)
			return "West";
		if(index == 2)
			return "South";
		if (index == 3)
			return "East";
		else
			return "You're drunk, go home";
	}
	public boolean isNorth() {
		return hasBlock[0];
	}

	public boolean isEast() {
		return hasBlock[3];
	}

	public boolean isWest() {
		return hasBlock[1];
	}

	public boolean isSouth() {
		return hasBlock[2];
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int xC){
		x = xC;
	}
	public void setY(int yC){
		y = yC;
	}
	public void generatePos(int r, int c){
		positions = new Arrow[4];
		
		positions[0] = new Arrow(r, c, 'n');
		positions[1] = new Arrow(r, c, 'w');
		positions[2] = new Arrow(r, c, 's');
		positions[3] = new Arrow(r, c, 'e');
	}
	
	public Arrow getPositionsArrows(int index){
		if(index >= 0 || index <= 3)
			return positions[index];
		return null;
	}
}
