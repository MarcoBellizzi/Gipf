package logica;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("direzione")
public class Direzione {

	@Param(0)
	int d;
	
	public Direzione() {
		d = 0;
	}
	
	public Direzione(int d2) {
		d = d2;
	}
	
	public int getD() { 
		return d; 
	}
	
	@Override
	public String toString() {
		return "Direzione [d=" + d + "]";
	}

	public void setD(int d2) { 
		d = d2;
	}
	
}
