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

	static int x = 0;
	static int y = 0;

	ArrayList<Punto> listaPunti;
	ArrayList<Start> listaSpot;
	ArrayList<Pedina> pedineNere;
	ArrayList<Pedina> pedineBianche;
	Start[] suggerimento;     // per i suggerimenti grafici
	boolean scelto;

	Bottone bottone;

	Image scacchiera, damaNera, damaBianca, puntoRosso, puntoVerde;

	public static Handler handler;  

	Scelgo sceltadlv;

	public Pannello() {
		initGUI();
		initEH();
		initListe();
		initDlv();
	}

	public void initGUI() {
		this.setFocusable(true);
		try {
			scacchiera = ImageIO.read(new File("src/grafica/scacchiera.png"));
			damaNera = ImageIO.read(new File("src/grafica/damaNera.png"));
			damaBianca = ImageIO.read(new File("src/grafica/damaBianca.png"));
			puntoRosso = ImageIO.read(new File("src/grafica/puntoRosso.png"));
			puntoVerde = ImageIO.read(new File("src/grafica/puntoVerde.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		bottone = new Bottone(200,10,80,80);
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

				for(Start punto : listaSpot) {
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
							suggerimento[1].set(100, 100, 1);  // non farlo vedere
						}
						else {
							if(dir2 == 1) suggerimento[1].set(x,y-2,dir2);
							if(dir2 == 2)  suggerimento[1].set(x+1,y-1,dir2);
							if(dir2 == 3) suggerimento[1].set(x+1,y+1,dir2);
							if(dir2 == 4) suggerimento[1].set(x,y+2,dir2);
							if(dir2 == 5) suggerimento[1].set(x-1,y+1,dir2);
							if(dir2 == 6)  suggerimento[1].set(x-1,y-1,dir2);
						}

						scelto = true;
					}
				}

				for(int i=0; i<2; i++) {
					if(suggerimento[i].isFocus(x, y)) {
						for(Pedina pedina : pedineNere ) {
							if(pedina.getX()==0 && pedina.getY()==0) {
								pedina.setX(suggerimento[i].getX());
								pedina.setY(suggerimento[i].getY());
								break;
							}
						}
						scelto = false;
					}
				}

				if(bottone.isFocus(x, y)) {
					scegli();
				}

			}

		});

	}

	public void initListe() {
		listaPunti = new ArrayList<Punto>();
		listaSpot = new ArrayList<Start>();
		for(int i=0; i<9; i++) {               // Inizialiazzazione scacchiera
			for(int j=0; j<17; j++) {
				if(i%2 == j%2) {
					if(i==0 && j==4) {
						listaSpot.add(new Start(i,j,3));
					}
					else if(i+j==4 && i!=0 && i!=4) {
						listaSpot.add(new Start(i,j,3, 4));
					}
					else if(i==4 && j==0) {
						listaSpot.add(new Start(i,j,4));
					}
					else if(i-j==4 && i!=4 && i!=8) {
						listaSpot.add(new Start(i,j,4, 5));
					}
					else if(i==8 && j==4) {
						listaSpot.add(new Start(i,j,5));
					}
					else if(i==8 && j>5 && j<11) {
						listaSpot.add(new Start(i,j,5, 6));
					}
					else if(i==8 && j==12) {
						listaSpot.add(new Start(i,j,6));
					}
					else if(i+j==20 && i!=4 && i!=8) {
						listaSpot.add(new Start(i,j,6, 1));
					}
					else if(i==4 && j==16) {
						listaSpot.add(new Start(i,j,1));
					}
					else if(j-i==12 && i!=0 && i!=4) {
						listaSpot.add(new Start(i,j,1, 2));
					}
					else if(i==0 && j==12) {
						listaSpot.add(new Start(i,j,2));
					}
					else if(i==0 && j>5 && j<11) {
						listaSpot.add(new Start(i,j,2, 3));
					}
					else if(i+j>4 && i-j<4 && i+j<20 && j-i<12) {
						listaPunti.add(new Punto(i,j));
					}
				}					
			}
		}

		pedineNere = new ArrayList<Pedina>();
		for(int i=0; i<12; i++) {
			pedineNere.add(new Pedina(0,0,0));
		}
		
		pedineBianche = new ArrayList<Pedina>(); 
		for(int i=0; i<12; i++) {
			pedineBianche.add(new Pedina(8,0,1));
		}

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
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sceltadlv = new Scelgo(100,100,"");

	//	scegli();
		
	}

	public void scegli() {
		handler.removeAll();
		
		InputProgram facts = new ASPInputProgram();
		for(Start punto : listaSpot) {
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
		handler.addProgram(facts);

		InputProgram encoding = new ASPInputProgram();
		encoding.addFilesPath("codice/iniziale");
		handler.addProgram(encoding);

		Output o =  handler.startSync();    // esegue il dlv

		AnswerSets answers = (AnswerSets) o;

		sceltadlv = new Scelgo(100,100,"");  // se non c'è nessuno scelgo
		int as = 0;
		int oggetti = 0;
		int scelgo = 0;
		for(AnswerSet a : answers.getAnswersets()){
			as++;
			try {
				for(Object obj : a.getAtoms()){
					oggetti++;

					if(obj instanceof Scelgo) {
						System.out.println((Scelgo)obj);
						scelgo++;
						sceltadlv = (Scelgo) obj;
					}
					
					if(obj instanceof Pedina) {
						Pedina pedina = (Pedina) obj;
						System.out.println(pedina);
						if(pedina.getColore()==1) {
							for(Pedina bianca : pedineBianche) {
								if(bianca.getX()==8 && bianca.getY()==0) {
									bianca.setX(pedina.getX());
									bianca.setY(pedina.getY());
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

		System.out.println("answersets : " + as);
		System.out.println("atomi : " + oggetti);
		System.out.println("scelte : " + scelgo);
		System.out.println("coordinate della scelta : " + sceltadlv.getX() + "  " + sceltadlv.getY());

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		g.drawImage(scacchiera,0,0,700,700,this);

		g.drawImage(damaBianca, bottone.getX(), bottone.getY(), bottone.getLarghezza(), bottone.getAltezza(), this);

		int countPedine = 0;
		for(Pedina pedina : pedineNere) {
			if(pedina.getX()==0 && pedina.getY()==0) {
				countPedine++;
			}
			g.drawImage(damaNera, pedina.getX()*60-20+110, pedina.getY()*35-20+70, 40, 40, this);
		}
		g.drawString(""+countPedine, 140, 80);
		
		int countBianche = 0;
		for(Pedina pedina : pedineBianche) {
			if(pedina.getX()==8 && pedina.getY()==0) {
				countBianche++;
			}
			g.drawImage(damaBianca, pedina.getX()*60-20+110, pedina.getY()*35-20+70, 40, 40, this);
		}
		g.drawString(""+countBianche, 540, 80);

		for(Start punto : listaSpot) {
			if(punto.isFocus(x, y)) {
				g.drawImage(puntoRosso, punto.getX()*60-20+110, punto.getY()*35-20+70, 40, 40, this);  // fare funzione

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
			}
		}

		g.drawImage(puntoRosso, sceltadlv.getX()*60-20+110, sceltadlv.getY()*35-20+70, 40, 40, this);



	}


}


