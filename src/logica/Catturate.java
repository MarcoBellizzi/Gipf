package logica;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("catturate")
public class Catturate {

	@Param(0)
	int numero;
	
	@Param(1)
	int colore;
	
	public Catturate() {
		numero = 0;
	}
	
	public Catturate(int n, int col) {
		numero = n;
		colore = col;
	}
	
	public int getNumero() { return numero; }
	
	public int getColore() { return colore; }
	
	public void setNumero(int n) { numero = n; }
	
	public void setColore(int c) { colore = c; }
	
}
