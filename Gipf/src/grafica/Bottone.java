package grafica;

public class Bottone {

	int x, y, larghezza, altezza;
	
	public Bottone(int x, int y, int larghezza, int altezza) {
		this.x = x;
		this.y = y;
		this.larghezza = larghezza;
		this.altezza = altezza;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getLarghezza() {
		return larghezza;
	}

	public int getAltezza() {
		return altezza;
	}
	
	public boolean isFocus(int x, int y) {    // collisione con il mouse
		return x > this.x && x < this.x+larghezza && y > this.y && y < this.y+altezza;
	}
	
}
