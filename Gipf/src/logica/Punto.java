package logica;

public class Punto {  

	int x, y;
	
	public Punto() {}
	
	public Punto(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public boolean isFocus(int x, int y) {    // collisione con il mouse
		x -= 110;
		y -= 70;
		int X = this.x*60;
		int Y = this.y*35;
		return x > X-10 && x < X+10 && y > Y-10 && y < Y+10;
	}
	
}
