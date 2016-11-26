package tdd.training.session1;

import java.util.ArrayList;

enum direction {N,S,W,E}

public class MarsRover {
	private int x;
	private int y;
	String obstacles;
	String obstaclesFound;
	private direction facing;
	private Coordinate position;
	
	public MarsRover(int x, int y, String obstacles) throws MarsRoverException{
	/*	x and y represent the size of the grid.
	 *  Obstacles is a String formatted as follows: ?(o1_x,o1_y)(o2_x,o2_y)...(on_x,on_y)? with no white spaces. 
	 *  
		Example use:
		MarsRover rover = new MarsRover(100,100,"?(5,5)(7,8)?")  //A 100x100 grid with two obstacles at coordinates (5,5) and (7,8) 
	 */
		if(x<=0 || y<= 0){
			throw new MarsRoverException();
		}
		else{
			this.x = x;
			this.y = y;
			this.obstacles = obstacles;
			obstaclesFound="";
			facing = direction.N;
			position = new Coordinate();
		}
	}
	
	public String executeCommand(String command) throws MarsRoverException{
		
		/* The command string is composed of "f" (forward), "b" (backward), "l" (left) and "r" (right)
		 * Example: 
		 * The rover is on a 100x100 grid at location (0, 0) and facing NORTH. The rover is given the commands "ffrff" and should end up at (2, 2) facing East.
		 
		 * The return string is in the format: ?(x,y,facing)(o1_x,o1_y)(o2_x,o2_y)?..(on_x,on_y)?  
		 * Where x and y are the final coordinates, facing is the current direction the rover is pointing to (N,S,W,E).
		 * The return string should also contain a list of coordinates of the encountered obstacles. No white spaces.
		 */
		char[] sequence = splittedCommandSequence(command);
		for(int i=0;i<sequence.length;i++){
			move(sequence[i]);
		}
		calculateSpawnPosition();
		
		return "("+position.getX()+","+position.getY() +"," + facing +")" + obstaclesFound;
	}
	
	public Coordinate getPosition(){
		return position;
	}
	
	public char[] splittedCommandSequence(String command) throws MarsRoverException{
		int sequenceLength = command.length();
		char[] sequence = new char[sequenceLength];
		char currentChar;
		for(int i=0;i<sequenceLength;i++){
			currentChar = command.charAt(i);
			if(currentChar == 'f' || currentChar == 'b' || currentChar == 'r' || currentChar == 'l'){
				sequence[i] = currentChar;
			}
			else{
				throw new MarsRoverException();
			}
		}
		return sequence;
	}
	
	public void move(char singleCommand) throws MarsRoverException{
		ArrayList<Coordinate> obstacles = computeObstacles();
		switch(singleCommand){
		case 'l':
			moveCounterClockWise();
			break;
		case 'r':
			moveClockWise();
			break;
		case 'f':
			moveForward(obstacles);
			break;
		case 'b':
			moveBackward(obstacles);
			break;
		default:
			break;
		}
	}
	
	public void moveClockWise(){
		switch(facing){
		case N:
			facing = direction.E;
			break;
		case S:
			facing = direction.W;
			break;
		case W:
			facing = direction.N;
			break;
		case E:
			facing = direction.S;
			break;
		default:
			break;
		}
	}
	
	public void moveCounterClockWise(){
		switch(facing){
		case N:
			facing = direction.W;
			break;
		case S:
			facing = direction.E;
			break;
		case W:
			facing = direction.S;
			break;
		case E:
			facing = direction.N;
			break;
		default:
			break;
		}
	}
	
	public void moveForward(ArrayList<Coordinate> obstacles){
		switch(facing){
		case N:
			position.addY();
			if(coordinateListContainsACoordinate(obstacles,position)){
				position.subY();
			}
			break;
		case S:
			position.subY();
			if(coordinateListContainsACoordinate(obstacles,position)){
				position.addY();
			}
			break;
		case W:
			position.subX();
			if(coordinateListContainsACoordinate(obstacles,position)){
				position.addX();
			}
			break;
		case E:
			position.addX();
			if(coordinateListContainsACoordinate(obstacles,position)){
				position.subX();
			}
			break;
		default:
			break;
		}
	}
	
	public void moveBackward(ArrayList<Coordinate> obstacles){
		switch(facing){
		case N:
			position.subY();
			if(coordinateListContainsACoordinate(obstacles,position)){
				position.addY();
			}
			break;
		case S:
			position.addY();
			if(coordinateListContainsACoordinate(obstacles,position)){
				position.subY();
			}
			break;
		case W:
			position.addX();
			if(coordinateListContainsACoordinate(obstacles,position)){
				position.subX();
			}
			break;
		case E:
			position.subX();
			if(coordinateListContainsACoordinate(obstacles,position)){
				position.addX();
			}
			break;
		default:
			break;
		}
	}
	
	public boolean coordinateListContainsACoordinate(ArrayList<Coordinate> list, Coordinate point){
		if(list.contains(point)){
			if(!obstaclesFound.contains(point.toString())){
				obstaclesFound +=point.toString();
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	public void calculateSpawnPosition(){
		if(position.getX()<0){
			position.setX(position.getX()+x);
		}
		else if(position.getX()>=x){
			position.setX(position.getX()-x);
		}
		if(position.getY()<0){
			position.setY(position.getY()+y);
		}
		else if(position.getY()>=y){
			position.setY(position.getY()-y);
		}
	}
	
	public ArrayList<Coordinate> computeObstacles() throws MarsRoverException{
		ArrayList<Coordinate> obstaclesInGrid = new ArrayList<Coordinate>();
		String[] splittedObstacles = obstacles.split("[//(//,//)]");
		for(int i=0;i<splittedObstacles.length;i++){
			if(!splittedObstacles[i].equals("")){
				obstaclesInGrid.add(new Coordinate(Integer.parseInt(splittedObstacles[i]),Integer.parseInt(splittedObstacles[i+1])));
				i++;
			}
		}
		for(int i=0;i<obstaclesInGrid.size();i++){
			if(obstaclesInGrid.get(i).getX() <= 0 || obstaclesInGrid.get(i).getX() > x || obstaclesInGrid.get(i).getY() <= 0 || obstaclesInGrid.get(i).getY() > y ){
				throw new MarsRoverException();
			}
		}
		return obstaclesInGrid;
	}
	
	public String toString(){
		return "("+position.getX()+","+position.getY() +"," + facing +")" + obstaclesFound;
	}
}
