package logica;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("nuova")
public class Nuova {

	@Param(0)
	int x;
	
	@Param(1)
	int y;

	@Param(2)
	int colore;

	public Nuova() {}
	
	public Nuova(int x, int y, int colore) {
		this.x = x;
		this.y = y;
		this.colore = colore;
	}
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public int getColore() { return colore; }
	
	public void setX(int x) { this.x = x; }
	
	public void setY(int y) { this.y = y; }
	
	public void setColore(int colore) { this.colore = colore; }

	@Override
	public String toString() {
		return "Nuova [x=" + x + ", y=" + y + ", colore=" + colore + "]";
	}
	
	
}