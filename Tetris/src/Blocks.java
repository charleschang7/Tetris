import processing.core.PApplet;

public class Blocks extends PApplet{
	int[][] shape;
	int blockNum;
	
	public Blocks(int blockNum) {
		this.blockNum = blockNum;
	}
	
	public int[][] makeShape() {
		switch(blockNum) {
		//This is square shape
		case 0: shape = new int[2][2];
				shape[0][0] = 1;
				shape[0][1] = 1;
				shape[1][0] = 1;
				shape[1][1] = 1;
				break;
		//This is the long piece
		case 1: shape = new int[4][1];
				shape[0][0] = 2;
				shape[1][0] = 2;
				shape[2][0] = 2;
				shape[3][0] = 2;
				break;
		//This is the L
		case 2: shape = new int[3][2];
				shape[0][0] = 3;
				shape[1][0] = 3;
				shape[2][0] = 3;
				shape[2][1] = 3;
				break;
		//This is the other direction L
		case 3: shape = new int[3][2];
				shape[0][1] = 4;
				shape[1][1] = 4;
				shape[2][1] = 4;
				shape[2][0] = 4;
				break;
		//This is the S block
		case 4: shape = new int[2][3];
				shape[0][1] = 5;
				shape[0][2] = 5;
				shape[1][1] = 5;
				shape[1][0] = 5;
				break;
		//This is the Z block
		case 5: shape = new int[2][3];
				shape[0][0] = 6;
				shape[0][1] = 6;
				shape[1][1] = 6;
				shape[1][2] = 6;
				break;
		//This is the T block
		case 6: shape = new int[2][3];
				shape[1][0] = 7;
				shape[1][1] = 7;
				shape[1][2] = 7;
				shape[0][1] = 7;
				break;
		}
		return shape;
	}
	
}
