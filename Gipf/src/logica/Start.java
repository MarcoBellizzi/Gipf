package logica;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("start")
public class Start {     // rinominare start

	@Param(0)
	int x;
	
	@Param(1)
	int y;
	
	@Param(2)
	int direzione1;
	
	@Param(3)
	int direzione2;

	public Start() {
		this.x = 0;
		this.y = 0;
		this.direzione1 = 0;
		this.direzione2 = 0;
	}
	
	public Start(int x, int y) {
		this.x = x;
		this.y = y;
		this.direzione1 = 0;
		this.direzione2 = 0;
	}
	
	public Start(int x, int y, int direzione) {
		this.x = x;
		this.y = y;
		this.direzione1 = direzione;
		this.direzione2 = direzione;
	}
	
	public Start(int x, int y, int direzione1, int direzione2) {
		this.x = x;
		this.y = y;
		this.direzione1 = direzione1;
		this.direzione2 = direzione2;
	} 
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public int getDirezione1() { return direzione1; }
	
	public int getDirezione2() { return direzione2; }

	public void setX(int x) { this.x = x; }
	
	public void setY(int y) { this.y = y; }
	
	public void setDirezione1(int direzione) { this.direzione1 = direzione; }
	
	public void setDirezione2(int direzione) { this.direzione2 = direzione; }
	
	public void set(int x, int y, int direzione) {
		this.x = x;
		this.y = y;
		this.direzione1 = direzione;
		this.direzione2 = direzione;
	}
	
	public boolean isFocus(int x, int y) {    // collisione con il mouse
		x -= 110;
		y -= 70;
		int X = this.x*60;
		int Y = this.y*35;
		return x > X-10 && x < X+10 && y > Y-10 && y < Y+10;
	}
	
}










