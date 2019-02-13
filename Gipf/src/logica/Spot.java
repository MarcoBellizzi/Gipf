package logica;

import java.util.ArrayList;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("spot")
public class Spot {

	public enum direzione { SU, DESTRA_SU, DESTRA_GIU, GIU, SINISTRA_GIU, SINISTRA_SU};

	@Param(0)
	int x;
	
	@Param(1)
	int y;
	
	ArrayList<direzione> direzioni;

	public Spot() {
		this.x = 0;
		this.y = 0;
		direzioni = new ArrayList<direzione>();
	}
	
	public Spot(int x, int y) {
		this.x = x;
		this.y = y;
		direzioni = new ArrayList<direzione>();
	}
	
	public Spot(int x, int y, direzione direzione1) {
		this.x = x;
		this.y = y;
		direzioni = new ArrayList<direzione>();
		direzioni.add(direzione1);
	}
	
	public Spot(int x, int y, direzione direzione1, direzione direzione2) {
		this.x = x;
		this.y = y;
		direzioni = new ArrayList<direzione>();
		direzioni.add(direzione1);
		direzioni.add(direzione2);
	} 
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public void setX(int x) { this.x = x; }
	
	public void setY(int y) { this.y = y; }
	
	public ArrayList<direzione> getDirezioni() { return direzioni; }
	
	public void set(int x, int y, direzione direzione) {
		this.x = x;
		this.y = y;
		direzioni.clear();
		direzioni.add(direzione);
	}
	
	public boolean isFocus(int x, int y) {    // collisione con il mouse
		x -= 110;
		y -= 70;
		int X = this.x*60;
		int Y = this.y*35;
		return x > X-10 && x < X+10 && y > Y-10 && y < Y+10;
	}
	
}










