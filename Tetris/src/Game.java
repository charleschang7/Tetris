import java.util.Random;

import processing.core.PApplet;
public class Game extends PApplet{
	public TetrisBoard board, oldBoard, displaying;
	public NextBlock yo;
	int squareSize, secondCount;
	boolean canFall = true, canMoveLeft = true, canMoveRight = true, stopped = false, lost = false;
	static boolean filled = false;
	Blocks current, next;


	public static void main(String[] args) {
		PApplet.main("Game");
	}

	public void settings(){
		size(800,800);
	}

	public void setup(){
		//I set the framerate at the start and the second counter to make the block fall
		frameRate(10);
		secondCount = second();
		
		//The tetris board takes in this as a Papplet so it can access the methods in processing
		board = new TetrisBoard(this, 600, 600);
		oldBoard = new TetrisBoard(this, 600, 600);
		displaying = new TetrisBoard(this, 600, 600);
		yo = new NextBlock(this, 800, 600);
		
		//I make an old board and a new board to remember which blocks can move and which ones can't
		squareSize = 30;

		//This part creates the random tetris block
		Random rand = new Random();
		current = new Blocks(rand.nextInt(7));
		current.makeShape();
		
		next = new Blocks(rand.nextInt(7));
		next.makeShape();

		//This adds the tetris block to the board
		for(int i = 0; i <= 4; i++){
			for(int j = 0; j <= 4; j++) {
				if(i < current.shape.length && j < current.shape[i].length)
					board.intBoard[j][4 + i] += current.shape[i][j];
			}
		}
		
		//This adds the next block to the side screen
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++) {
				if(i < next.shape.length && j < next.shape[i].length)
					yo.intBoard[i][j] += next.shape[i][j];
			}
		}
	//	printBoard(board.intBoard);
	}

	public void draw(){
		//The 5 and 8 are from the board being a 16 by 10 board so centering the board needs to be half of the dimension size
		displaying.draw(squareSize, width/2 - squareSize * 5, height/2 - squareSize * 8);
		
		//I draw the displaying next block
		yo.draw(squareSize / 2, width - squareSize * 5, height/2 - squareSize * 10);
		
		
		//I make the board that is shown equal the board that can move plus the board that cannot move
		displaying.intBoard = add(board.intBoard, oldBoard.intBoard);
		
		//This is the part that allows the block to move left
		if((keyPressed == true) && (keyCode == LEFT)) {
			for(int i = 0; i < board.intBoard.length; i++) {
				for(int j = 0; j < board.intBoard[i].length; j++){
					//I have to check if all the squares of the current piece can move left and then move each of the pieces left
					//I only need to check the left most piece of each block and see if that can move left
					if(board.intBoard[i][j] != 0) {
//						System.out.println("i is " + i);
//						System.out.println("j is " + j);
					//If the any part of the block is at the left I want the block to not be able to move left
						if(j <= 0) {
							canMoveLeft = false;
						}else if(oldBoard.intBoard[i][j - 1] != 0) {
							canMoveLeft = false;
//							System.out.println(canMoveLeft + "in the false");
						}
					}
				}
			}
			if(canMoveLeft) {
				for(int i = 0; i < board.intBoard.length; i++) {
					for(int j = 0; j < board.intBoard[i].length; j++){
//						System.out.println("Its in the move on the left");
						if(displaying.intBoard[i][j] != 0 && j > 0) {
							board.intBoard[i][j - 1] = board.intBoard[i][j];
							board.intBoard[i][j] = 0;
							canMoveRight = true;
//							System.out.println();
//							printBoard();
						}
					}
				}
			}
		}
		
		//This is for moving right, its the same as left really
		//Actually it isnt... this is hard... I've been sitting here for an hour and cant think of right after I already did left ._.
		//I finally got it! I had to go from the right and move each block one to the right if it could move
		if((keyPressed == true) && (keyCode == RIGHT)){
			for(int i = 0; i < board.intBoard.length; i++) {
				for(int j = board.intBoard[i].length - 1; j >= 0; j--){
//					System.out.println("i is" + i);
//					System.out.println("j is" + j);
//					System.out.println("canMoveRight is " + canMoveRight);
					if(board.intBoard[i][j] != 0) {
						if(j >= 9) {
							canMoveRight = false;
						}else if(oldBoard.intBoard[i][j + 1] != 0) {
							canMoveRight = false;
						}
					}
				}
			}
			if(canMoveRight) {
				for(int i = 0; i < board.intBoard.length; i++) {
					for(int j = board.intBoard[i].length; j > 0 ; j--){
//						System.out.println("Its in the move");
						if(board.intBoard[i][j - 1] != 0 && j < 16) {
							board.intBoard[i][j] = board.intBoard[i][j - 1];
							board.intBoard[i][j - 1] = 0;
//							System.out.println();
//							printBoard(board.intBoard);
							canMoveLeft = true;
						}
					}
				}
			}
		}

		//Make a seconds counter and then check if seconds is greater than the counter
		//Make the frame rate = 1  
		
		//Here is where the blocks begin to fall
		
//		System.out.println(secondCount);
//		System.out.println(second());
		
		//I make the block rotate if it is pressed
		if((keyPressed == true) && (keyCode == UP)) {
			System.out.println("you pressed up");
			printBoard(board.intBoard);
			rotate(board.intBoard);
			System.out.println("new one");
			printBoard(board.intBoard);
		}
		
		if((keyPressed == true) && (keyCode == DOWN)) {
			fall();
			stop();
		}
		
		if(secondCount != second()) {
			secondCount = second();
			fall();
			stop();
		}
		

	}

	public void printBoard(int[][] board){
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}
	
	//In the fall I need to loop through the grid and check if the piece at the bottom of the block can move
	public void fall(){
		for(int i = board.intBoard[0].length - 1; i >= 0; i--) {
			for(int j = board.intBoard.length - 1; j >= 0; j--){
//				System.out.println("i is" + i);
//				System.out.println("j is" + j);
				if(board.intBoard[j][i] != 0) {
//					if(intLost == 0) {
//						System.out.println("You Lose");
//						noLoop();
//					}
					if(j == board.intBoard.length - 1) {
						canFall = false;
					}else if(oldBoard.intBoard[j + 1][i] != 0) {
						canFall = false;
					}
					break;
				}
//				intLost++;
			}
		}
//		System.out.println("Can Fall is " + canFall);
//		System.out.println(canMoveRight);
	
		if(canFall) {
//			System.out.println("You're in canfall");
			for(int i = board.intBoard[0].length - 1; i >= 0; i--) {
				for(int j = board.intBoard.length - 1; j >= 0; j--){
//					System.out.println("i is" + i);
//					System.out.println("j is" + j);
					if(board.intBoard[j][i] != 0) {
						board.intBoard[j + 1][i] = board.intBoard[j][i];
						board.intBoard[j][i] = 0;
					}
				}
			}
		}
	}
	
	public void stop() {
		//This code is for when the block has hit the ground
		if(!canFall) {
			moveOver(board.intBoard, oldBoard.intBoard);
			resetBoard(board.intBoard);
//			printBoard(board.intBoard);
//			System.out.println();
//			printBoard(oldBoard.intBoard);
			
			//Here is where the game will remove the line if it can
			line(oldBoard.intBoard);
			
			//This part creates the next tetris block and sets the one to spawn as the previous next one
			Random rand = new Random();
			current = next;
			next = new Blocks(rand.nextInt(7));
			next.makeShape();
			current.makeShape();

			
			//This checks if you lose, If there is a block on the top layer then that means you lost
			for(int i = 0; i < oldBoard.intBoard[0].length; i++) {
				if(oldBoard.intBoard[0][i] != 0) {
					System.out.println("Ur Dun");
					noLoop();
					System.exit(0);
				}
			}
			
			//This adds the tetris block to the board
			for(int i = 0; i <= 4; i++){
				for(int j = 0; j <= 4; j++) {
					if(i < current.shape.length && j < current.shape[i].length)
						board.intBoard[j][4 + i] += current.shape[i][j];
				}
			}
			

			//This adds the next block to the side screen
			for(int i = 0; i < 4; i++){
				for(int j = 0; j < 4; j++) {
					if(i < next.shape.length && j < next.shape[i].length) {
						yo.intBoard[i][j] = next.shape[i][j];
					}else {
						yo.intBoard[i][j] = 0;
					}
				}
			}
			canFall = true;
			canMoveLeft = true;
			canMoveRight = true;
		}
	}
	
	//This method moves the stopped board into a board that doesnt move
	public void moveOver(int[][] first, int[][] second) {
		for(int i = 0; i < first.length; i++) {
			for(int j = 0; j < first[i].length; j++) {
				second[i][j] += first[i][j];
			}
		}
	}
	
	//This adds the board that can move and the one that can't
	public int[][] add(int[][] first, int[][] second) {
		int[][] third = new int[first.length][first[0].length];
		for(int i = 0; i < first.length; i++) {
			for(int j = 0; j < first[i].length; j++) {
				third[i][j] += first[i][j];
			}
		}
		for(int i = 0; i < second.length; i++) {
			for(int j = 0; j < second[i].length; j++) {
				third[i][j] += second[i][j];
			}
		}
		return third;
	}
	
	//This method sets the current board to all 0's
	public void resetBoard(int[][] board) {
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				board[i][j] = 0;
			}
		}
	}
	
	public static void line(int[][] arr) {
		//This part checks for a line and then gets rid of it
		for(int i = arr.length - 1; i >= 0; i--) {
			//First I have to check if there is a line filled and if there is then I need to move all the other lines down
			if(lineFilled(arr[i])){
				filled = true;
			}
		}
		//System.out.println(filled);
		if(filled) {
			for(int i = 0; i < arr.length; i++) {
				if(lineFilled(arr[i])) {
					setZero(arr[i]);
				}
			}
		//I recursively call a method that moves all the lines down if they can be moved down
			moveLine(arr);
			filled = false;
		}
	}
	
	//This returns true if the line is filled and false otherwise
	public static boolean lineFilled(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == 0) {
				return false;
			}
		}
		return true;
	}
	
	public static void moveLine(int[][] arr) {
		for(int i = arr.length - 1; i >= 0; i--) {
			if(i < arr.length && i > 0 && checkZero(arr[i]) && !checkZero(arr[i - 1])) {
				moveLine(arr[i - 1], arr[i]);
//				System.out.println("yooyoyo");
				moveLine(arr);
			}
		}
	}
	
	public static void moveLine(int[] arr, int[] arr2) {
		for(int i = 0; i < arr.length; i++) {
			arr2[i] = arr[i];
			arr[i] = 0;
		}
	}
	
	public static boolean checkZero(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] != 0) {
				return false;
			}
		}
		return true;
	}
	
	public static void setZero(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			arr[i] = 0;
		}
	}
	
	public void rotate(int[][] arr) {
		for(int i = 0; i < arr.length; i++) {
			for(int j = arr[i].length - 1; j >= 0; j--) {
				switch(arr[i][j]) {
				//This code is used to rotate the long piece
				case 2:
//					System.out.println("arr[" + i + "][" + j + "] is" + arr[i][j]);
//					System.out.println("arr[" + i + 1 + "][" + j + "] is" + arr[i + 1][j]);
					if(arr[i + 1][j] == 2) {
						arr[i][j + 1] = 2;
						arr[i][j + 2] = 2;
						arr[i][j + 3] = 2;
						arr[i + 1][j] = 0;
						arr[i + 2][j] = 0;
						arr[i + 3][j] = 0;
					}else if(arr[i][j + 1] == 2) {
						arr[i + 1][j] = arr[i][j + 1];
						arr[i][j + 1] = 0;
					}
					break;
					
					default: break;
				}
			}
		}
	}

}
