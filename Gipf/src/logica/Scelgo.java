package logica;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("scelgo")
public class Scelgo {

	@Param(0)
	int x;
	
	@Param(1)
	int y;
	
	public Scelgo() {
		this.x = 0;
		this.y = 0;
	}
	
	public Scelgo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public void setX(int x) { this.x = x; }
	
	public void setY(int y) { this.y = y; }
	
	
	
}
