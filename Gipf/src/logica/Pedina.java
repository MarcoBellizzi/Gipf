package logica;

public class Pedina {

	public enum colore { NERA, BIANCA }
	
	int x, y;
	
	colore col;
	
	public Pedina() {}
	
	public Pedina(int x, int y, colore col) {
		this.x = x;
		this.y = y;
		this.col = col;
	}
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public void setX(int x) { this.x = x; }
	
	public void setY(int y) { this.y = y; }
	
}





