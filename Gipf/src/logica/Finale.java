package logica;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("finale")
public class Finale {

	@Param(0)
	int x;
	
	@Param(1)
	int y;
	
	/*
	 * 0 = nera
	 * 1 = bianca
	 */
	@Param(2)
	int colore;

	public Finale() {}
	
	public Finale(int x, int y, int colore) {
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
		return "Finale [x=" + x + ", y=" + y + ", colore=" + colore + "]";
	}
	
	
	
}





