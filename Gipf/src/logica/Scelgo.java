package logica;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("scelgo")
public class Scelgo {

	@Param(0)
	int x;
	
	@Param(1)
	int y;
	
	@Param(2)
	String direzione;
	
	public Scelgo() {
		this.x = 0;
		this.y = 0;
	}
	
	public Scelgo(int x, int y, String direzione) {
		this.x = x;
		this.y = y;
		this.direzione = direzione;
	}
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public String getDirezione() { return direzione; }

	public void setX(int x) { this.x = x; }
	
	public void setY(int y) { this.y = y; }
	
	public void setDirezione(String direzione) { this.direzione = direzione; }
	
	@Override
	public String toString() {
		return "Scelgo [x=" + x + ", y=" + y + ", dir=" + direzione + "]";
	}

	
}
