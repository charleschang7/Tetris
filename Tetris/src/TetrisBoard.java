import processing.core.PApplet;

public class TetrisBoard{
	PApplet p;
	int x, y;
	int size;
	int[][] intBoard =     		   
		{{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0},}; 
	
	public TetrisBoard(PApplet p, int x, int y) {
		this.p = p;
		this.x = x;
		this.y = y;
	}

	public void draw(int s, int x0, int y0){
		size = s;
		p.background(0,0,255);
		p.stroke(0);
		p.strokeWeight(2);
		//This for loop makes the lines that go down
		for(int i = 0; i < intBoard.length; i++) {
			for(int j = 0; j < intBoard[0].length; j++) {
				switch(intBoard[i][j]) {
				case 0: p.fill(255); break;
				case 1: p.fill(255,255,0); break;
				case 2: p.fill(66,244,241); break;
				case 3: p.fill(255,170,0); break;
				case 4: p.fill(0,0,255); break;
				case 5: p.fill(0,255,0); break;
				case 6: p.fill(255,0,0); break;
				case 7: p.fill(250,0,255); break;
				}
				
				p.rect(x0 + j * size, y0 + i * size,size,size);
			}
		}
	}

}
