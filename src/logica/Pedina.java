package logica;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("pedina")
public class Pedina {

	@Param(0)
	int x;
	
	@Param(1)
	int y;

	@Param(2)
	int colore;
	
	@Param(3)
	int gipf;

	public Pedina() {}
	
	public Pedina(int x, int y, int colore, int gipf) {
		this.x = x;
		this.y = y;
		this.colore = colore;
		this.gipf = gipf;
	}
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public int getColore() { return colore; }
	
	public int getGipf() { return gipf; }
	
	public void setX(int x) { this.x = x; }
	
	public void setY(int y) { this.y = y; }
	
	public void setColore(int colore) { this.colore = colore; }

	public void setGipf(int gipf) { this.gipf = gipf; }
	
	@Override
	public String toString() {
		return "Pedina [x=" + x + ", y=" + y + ", colore=" + colore + ", gipf=" + gipf + "]";
	}
	
	
	
}





