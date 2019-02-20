package grafica;

public class Gestore extends Thread {

	Pannello pannello;
	boolean muovi;

	public Gestore(Pannello pannello) {
		this.pannello = pannello;
		muovi = false;
	}

	@Override
	public void run() {
		super.run();

		while(true) {
			pannello.repaint();
	
			try {
				if(muovi) {
					sleep(2000);
					pannello.muoviBianco();				
					muovi = false;
				}

				sleep(100);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void rilascia() { muovi = true; }

}
