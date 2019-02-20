package grafica;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import logica.*;

public class Pannello extends JPanel {

	private static final long serialVersionUID = 1L;

	static int x;   // muose
	static int y;

	ArrayList<Start> listaStart;
	ArrayList<Pedina> pedineNere;
	ArrayList<Pedina> pedineBianche;
	Start[] suggerimento;     // per i suggerimenti grafici

	Scelgo sceltadlv;

	static int scelta;   // per la direzione in cui mangiare se ce ne sono due
	static boolean deviScegliere;

	boolean scelto;   // posizionamento pediana nero

	static int catturateBianche;
	static int catturateNere;

	boolean almenoUnGipfBianco;
	boolean almenoUnGipfNero;
	boolean finitePedineBianche;
	boolean finitePedineNere;
	boolean vintoBianco;
	boolean vintoNero;

	static Handler handler;  

	Gestore gestoreTurni;

	Image scacchiera, damaNera, damaBianca, puntoRosso, puntoVerde, Iscelta, gipfNero, gipfBianco;

	public Pannello() {
		initGame();
		initGUI();
		initEH();
		initListe();
		initDlv();
	}

	public void initGame() {
		x = 0;
		y = 0;
		scelta = 0;
		sceltadlv = new Scelgo(100,100,10);
		deviScegliere = false;
		catturateBianche = 0;
		catturateNere = 0;
		almenoUnGipfBianco = true;
		almenoUnGipfNero = true;
		finitePedineBianche = false;
		finitePedineNere = false;
		vintoBianco = false;
		vintoNero = false;
		gestoreTurni = new Gestore(this);
		gestoreTurni.start();
	}

	public void initGUI() {
		this.setFocusable(true);
		try {
			scacchiera = ImageIO.read(new File("src/grafica/scacchiera.png"));
			damaNera = ImageIO.read(new File("src/grafica/damaNera.png"));
			damaBianca = ImageIO.read(new File("src/grafica/damaBianca.png"));
			puntoRosso = ImageIO.read(new File("src/grafica/puntoRosso.png"));
			puntoVerde = ImageIO.read(new File("src/grafica/puntoVerde.png"));
			Iscelta = ImageIO.read(new File("src/grafica/scelta.png"));
			gipfBianco = ImageIO.read(new File("src/grafica/gipfBianco.png"));
			gipfNero = ImageIO.read(new File("src/grafica/gipfNero.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initEH() {
		this.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				Pannello.x = e.getX();
				Pannello.y = e.getY();
				repaint();
			}

		});

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				scelto = false;

				for(Start punto : listaStart) {
					if(punto.isFocus(x, y)) {

						int x = punto.getX();
						int y = punto.getY();
						int dir1 = punto.getDirezione1();
						int dir2 = punto.getDirezione2();

						if(dir1 == 1) suggerimento[0].set(x,y-2,dir1);
						if(dir1 == 2)  suggerimento[0].set(x+1,y-1,dir1);
						if(dir1 == 3) suggerimento[0].set(x+1,y+1,dir1);
						if(dir1 == 4) suggerimento[0].set(x,y+2,dir1);
						if(dir1 == 5) suggerimento[0].set(x-1,y+1,dir1);
						if(dir1 == 6)  suggerimento[0].set(x-1,y-1,dir1);

						if(dir1==dir2) {
							suggerimento[1].set(100, 100, 1);       // non farlo vedere
						}
						else {
							if(dir2 == 1) suggerimento[1].set(x,y-2,dir2);
							if(dir2 == 2) suggerimento[1].set(x+1,y-1,dir2);
							if(dir2 == 3) suggerimento[1].set(x+1,y+1,dir2);
							if(dir2 == 4) suggerimento[1].set(x,y+2,dir2);
							if(dir2 == 5) suggerimento[1].set(x-1,y+1,dir2);
							if(dir2 == 6) suggerimento[1].set(x-1,y-1,dir2);
						}

						sceltadlv = new Scelgo(x,y,10); 
						scelto = true;
					}
				}

				for(int i=0; i<2; i++) {
					if(suggerimento[i].isFocus(x, y)) {
						sceltadlv.setDirezione(suggerimento[i].getDirezione1());
						scelto = false;
						muoviNero();
					}
				}

				scelta = focusScelta(x,y);
				if(scelta != 0) {
					deviScegliere = false;
					muoviNero2();
				}

			}

		});

	}

	public void initListe() {
		listaStart = new ArrayList<Start>();
		for(int i=0; i<9; i++) {          
			for(int j=0; j<17; j++) {
				if(i%2 == j%2) {
					if(i==0 && j==4) {
						listaStart.add(new Start(i,j,3));
					}
					else if(i+j==4 && i!=0 && i!=4) {
						listaStart.add(new Start(i,j,3, 4));
					}
					else if(i==4 && j==0) {
						listaStart.add(new Start(i,j,4));
					}
					else if(i-j==4 && i!=4 && i!=8) {
						listaStart.add(new Start(i,j,4, 5));
					}
					else if(i==8 && j==4) {
						listaStart.add(new Start(i,j,5));
					}
					else if(i==8 && j>5 && j<11) {
						listaStart.add(new Start(i,j,5, 6));
					}
					else if(i==8 && j==12) {
						listaStart.add(new Start(i,j,6));
					}
					else if(i+j==20 && i!=4 && i!=8) {
						listaStart.add(new Start(i,j,6, 1));
					}
					else if(i==4 && j==16) {
						listaStart.add(new Start(i,j,1));
					}
					else if(j-i==12 && i!=0 && i!=4) {
						listaStart.add(new Start(i,j,1, 2));
					}
					else if(i==0 && j==12) {
						listaStart.add(new Start(i,j,2));
					}
					else if(i==0 && j>5 && j<11) {
						listaStart.add(new Start(i,j,2, 3));
					}
				}					
			}
		}

		pedineNere = new ArrayList<Pedina>();
		for(int i=0; i<12; i++) {
			pedineNere.add(new Pedina(0,0,0,0));
		}
		pedineNere.add(new Pedina(4,2,0,1));
		pedineNere.add(new Pedina(7,11,0,1));
		pedineNere.add(new Pedina(1,11,0,1));

		pedineBianche = new ArrayList<Pedina>(); 
		for(int i=0; i<12; i++) {
			pedineBianche.add(new Pedina(8,0,1,0));
		}
		pedineBianche.add(new Pedina(1,5,1,1));
		pedineBianche.add(new Pedina(7,5,1,1));
		pedineBianche.add(new Pedina(4,14,1,1));

		suggerimento = new Start[2];
		for(int i=0; i<2; i++) {
			suggerimento[i] = new Start();
		}

	}

	public void initDlv() {
		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));

		// register the class for reflection
		try {
			ASPMapper.getInstance().registerClass(Start.class);
			ASPMapper.getInstance().registerClass(Scelgo.class);
			ASPMapper.getInstance().registerClass(Pedina.class);
			ASPMapper.getInstance().registerClass(Finale.class);
			ASPMapper.getInstance().registerClass(Catturate.class);
			ASPMapper.getInstance().registerClass(Direzione.class);
			ASPMapper.getInstance().registerClass(Nuova.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	public void muoviBianco() {
		handler.removeAll();

		InputProgram facts = new ASPInputProgram();
		for(Start punto : listaStart) {
			try {
				facts.addObjectInput(punto);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		for(Pedina pedina : pedineNere) {
			try {
				facts.addObjectInput(pedina);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		for(Pedina pedina : pedineBianche) {
			try {
				facts.addObjectInput(pedina);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		handler.addProgram(facts);

		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath("codice/bianco");
		handler.addProgram(encoding);

		Output o =  handler.startSync(); 
		AnswerSets answers = (AnswerSets) o;

		sceltadlv = new Scelgo(100,100,10);      // se non c'è nessuno scelgo

		for(AnswerSet a : answers.getAnswersets()){
			try {
				for(Object obj : a.getAtoms()) {
					if(obj instanceof Catturate) {
						if(((Catturate)obj).getColore() == 0 ) {
							catturateNere += ((Catturate) obj).getNumero();
						}
						else {
							catturateBianche += ((Catturate) obj).getNumero();
						}
					}
				}

				ripristina(pedineNere,0);
				ripristina(pedineBianche,1);

				for(Object obj : a.getAtoms()){

					if(obj instanceof Scelgo) {
						sceltadlv = (Scelgo) obj;
					}

					if(obj instanceof Finale) {
						Finale pedina = (Finale) obj;

						if(pedina.getColore()==1) {
							finitePedineBianche = true;
							for(Pedina bianca : pedineBianche) {
								if(bianca.getX()==8 && bianca.getY()==0) {
									finitePedineBianche = false;
									bianca.setX(pedina.getX());
									bianca.setY(pedina.getY());
									bianca.setGipf(pedina.getGipf());
									break;
								}
							}
						}
						else {
							finitePedineNere = true;
							for(Pedina nera : pedineNere) {
								if(nera.getX()==0 && nera.getY()==0) {
									finitePedineNere = false;
									nera.setX(pedina.getX());
									nera.setY(pedina.getY());
									nera.setGipf(pedina.getGipf());
									break;
								}
							}
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} 
		}

		verifica();
	}

	public void muoviNero() {
		handler.removeAll();

		InputProgram facts = new ASPInputProgram();

		try {
			facts.addObjectInput(sceltadlv);

			for(Pedina pedina : pedineNere) {
				facts.addObjectInput(pedina);
			}
			for(Pedina pedina : pedineBianche) {
				facts.addObjectInput(pedina);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		handler.addProgram(facts);

		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath("codice/nero");
		handler.addProgram(encoding);

		Output o =  handler.startSync();
		AnswerSets answers = (AnswerSets) o;

		for(AnswerSet p : answers.getAnswersets()) {
			try {

				ripristina(pedineNere,0);
				ripristina(pedineBianche,1);

				for(Object op : p.getAtoms()) {

					if(op instanceof Direzione) {    // eccezione (più direzioni in cui si può mangiare)
						if(((Direzione)op).getD() == 4) {
							deviScegliere = true;							
						}
					}

					if(op instanceof Nuova) {
						Nuova pedina = (Nuova) op;
						if(pedina.getColore()==1) {
							finitePedineBianche = true;
							for(Pedina bianca : pedineBianche) {
								if(bianca.getX()==8 && bianca.getY()==0) {
									finitePedineBianche = false;
									bianca.setX(pedina.getX());
									bianca.setY(pedina.getY());
									bianca.setGipf(pedina.getGipf());
									break;
								}
							}
						}
						else {
							finitePedineNere = true;
							for(Pedina nera : pedineNere) {
								if(nera.getX()==0 && nera.getY()==0) {
									finitePedineNere = false;
									nera.setX(pedina.getX());
									nera.setY(pedina.getY());
									nera.setGipf(pedina.getGipf());
									break;
								}
							}
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if(!deviScegliere) {
			muoviNero2();
		}
		else {
			System.out.println("scegli la direzione");
		}

	}

	public void muoviNero2() {
		handler.removeAll();

		InputProgram facts = new ASPInputProgram();

		try {
			facts.addObjectInput(new Direzione(scelta));

			for(Pedina pedina : pedineNere) {
				facts.addObjectInput(pedina);
			}
			for(Pedina pedina : pedineBianche) {
				facts.addObjectInput(pedina);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		handler.addProgram(facts);

		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath("codice/nero2");
		handler.addProgram(encoding);

		Output o =  handler.startSync(); 
		AnswerSets answers = (AnswerSets) o;

		for(AnswerSet a : answers.getAnswersets()) {
			try {

				for(Object obj : a.getAtoms()) {
					if(obj instanceof Catturate) {
						if(((Catturate)obj).getColore() == 0 ) {
							catturateNere += ((Catturate) obj).getNumero();
						}
						else {
							catturateBianche += ((Catturate) obj).getNumero();
						}
					}
				}

				ripristina(pedineNere,0);
				ripristina(pedineBianche,1);

				for(Object obj : a.getAtoms()) {
					if(obj instanceof Finale) {
						Finale pedina = (Finale) obj;

						if(pedina.getColore()==1) {
							finitePedineBianche = true;
							for(Pedina bianca : pedineBianche) {
								if(bianca.getX()==8 && bianca.getY()==0) {
									finitePedineBianche = false;
									bianca.setX(pedina.getX());
									bianca.setY(pedina.getY());
									bianca.setGipf(pedina.getGipf());
									break;
								}
							}
						}
						else {
							finitePedineNere = true;
							for(Pedina nera : pedineNere) {
								if(nera.getX()==0 && nera.getY()==0) {
									finitePedineNere = false;
									nera.setX(pedina.getX());
									nera.setY(pedina.getY());
									nera.setGipf(pedina.getGipf());
									break;
								}
							}
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		verifica();

		gestoreTurni.rilascia();

	}


	public static void ripristina(ArrayList<Pedina> lista, int colore) {
		lista.clear();

		if(colore==0) {
			for(int i=0; i<15-catturateNere; i++) {
				lista.add(new Pedina(0,0,0,0));
			}
		}
		else {
			for(int i=0; i<15-catturateBianche; i++) {
				lista.add(new Pedina(8,0,1,0));
			}
		}
	}

	public static int focusScelta(int x, int y) {
		if(x>50 && x< 70 && y>552 && y<572) return 1;
		if(x>95 && x< 115 && y>570 && y<590) return 2;
		if(x>95 && x< 115 && y>630 && y<650) return 3;

		return 0;
	}


	public void verifica() {
		if(finitePedineBianche) {
			vintoNero = true;
		}
		if(finitePedineNere) {
			vintoBianco = true;
		}
		almenoUnGipfBianco = false;
		for(Pedina pedina : pedineBianche) {
			if(pedina.getGipf()==1)
				almenoUnGipfBianco = true;
		}
		if(!almenoUnGipfBianco) {
			vintoNero = true;
		}
		almenoUnGipfNero = false;
		for(Pedina pedina : pedineNere) {
			if(pedina.getGipf()==1)
				almenoUnGipfNero = true;
		}
		if(!almenoUnGipfNero) {
			vintoBianco = true;
		}
	}


	@Override
	public void paint(Graphics g) {
		super.paint(g);


		if(!vintoBianco && !vintoNero) {
			g.drawImage(scacchiera,0,0,700,700,this);

			int countPedine = 0;
			for(Pedina pedina : pedineNere) {
				if(pedina.getX()==0 && pedina.getY()==0) {
					countPedine++;
				}
				if(pedina.getGipf()==0) {
					g.drawImage(damaNera, pedina.getX()*60-20+110, pedina.getY()*35-20+70, 40, 40, this);
				}
				else {
					g.drawImage(gipfNero, pedina.getX()*60-20+110, pedina.getY()*35-20+70, 40, 40, this);				
				}
			}
			g.drawString(""+countPedine, 140, 80);

			int countBianche = 0;
			for(Pedina pedina : pedineBianche) {
				if(pedina.getX()==8 && pedina.getY()==0) {
					countBianche++;
				}
				if(pedina.getGipf()==0) {
					g.drawImage(damaBianca, pedina.getX()*60-20+110, pedina.getY()*35-20+70, 40, 40, this);		
				}
				else {				
					g.drawImage(gipfBianco, pedina.getX()*60-20+110, pedina.getY()*35-20+70, 40, 40, this);
				}
			}
			g.drawString(""+countBianche, 540, 80);

			for(Start punto : listaStart) {
				if(punto.isFocus(x, y)) {
					g.drawImage(damaNera, punto.getX()*60-20+110, punto.getY()*35-20+70, 40, 40, this);  // fare funzione

					for(int i=0; i<2; i++) {
						int dir = punto.getDirezione1();
						if(i==1) dir = punto.getDirezione2();

						if(dir == 1)  g.drawImage(puntoVerde, punto.getX()*60-20+110, punto.getY()*35-20+70-70, 40, 40, this);
						if(dir == 2)  g.drawImage(puntoVerde, punto.getX()*60-20+110+60, punto.getY()*35-20+70-35, 40, 40, this);
						if(dir == 3)  g.drawImage(puntoVerde, punto.getX()*60-20+110+60, punto.getY()*35-20+70+35, 40, 40, this);
						if(dir == 4)  g.drawImage(puntoVerde, punto.getX()*60-20+110, punto.getY()*35-20+70+70, 40, 40, this);
						if(dir == 5)  g.drawImage(puntoVerde, punto.getX()*60-20+110-60, punto.getY()*35-20+70+35, 40, 40, this);
						if(dir == 6)  g.drawImage(puntoVerde, punto.getX()*60-20+110-60, punto.getY()*35-20+70-35, 40, 40, this);
					}
				}
			}

			if(scelto) {
				for(int i=0; i<2; i++) {
					g.drawImage(puntoVerde, suggerimento[i].getX()*60-20+110, suggerimento[i].getY()*35-20+70, 40, 40, this);
					g.drawImage(damaNera, sceltadlv.getX()*60-20+110, sceltadlv.getY()*35-20+70, 40, 40, this);			
				}
			}
			else {
				g.drawImage(puntoRosso, sceltadlv.getX()*60-20+110, sceltadlv.getY()*35-20+70, 40, 40, this);			
			}

			if(deviScegliere) {
				g.drawImage(Iscelta, 20, 540, 120, 120, this);
			}

		}
		else {
			if(vintoNero) {
				g.drawString("HAI VINTO", 300, 300);				
			}
			else {
				g.drawString("HAI PERSO", 300, 300);
			}
		}


	}


}

