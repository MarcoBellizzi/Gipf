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
import logica.Pedina.colore;
import logica.Spot.direzione;

public class Pannello extends JPanel {

	private static final long serialVersionUID = 1L;

	static int x = 0;
	static int y = 0;

	ArrayList<Punto> listaPunti;
	ArrayList<Spot> listaSpot;
	ArrayList<Pedina> pedineNere;
	Spot[] suggerimento;     // per i suggerimenti grafici
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

				for(Spot punto : listaSpot) {
					if(punto.isFocus(x, y)) {
						int i = 0;
						int x = punto.getX();
						int y = punto.getY();
						for(direzione dir : punto.getDirezioni()) {
							if(dir == direzione.SU) suggerimento[i].set(x,y-2,dir);
							if(dir == direzione.DESTRA_SU)  suggerimento[i].set(x+1,y-1,dir);
							if(dir == direzione.DESTRA_GIU) suggerimento[i].set(x+1,y+1,dir);
							if(dir == direzione.GIU) suggerimento[i].set(x,y+2,dir);
							if(dir == direzione.SINISTRA_GIU) suggerimento[i].set(x-1,y+1,dir);
							if(dir == direzione.SINISTRA_SU)  suggerimento[i].set(x-1,y-1,dir);
							i++;
						}
						if(i==1) suggerimento[1].set(100, 100, direzione.SU);  // non farlo vedere

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
					System.out.println("bottone premuto");
					initDlv();
				}

			}

		});

	}

	public void initListe() {
		listaPunti = new ArrayList<Punto>();
		listaSpot = new ArrayList<Spot>();
		for(int i=0; i<9; i++) {               // Inizialiazzazione scacchiera
			for(int j=0; j<17; j++) {
				if(i%2 == j%2) {
					if(i==0 && j==4) {
						listaSpot.add(new Spot(i,j,direzione.DESTRA_GIU));
					}
					else if(i+j==4 && i!=0 && i!=4) {
						listaSpot.add(new Spot(i,j,direzione.DESTRA_GIU, direzione.GIU));
					}
					else if(i==4 && j==0) {
						listaSpot.add(new Spot(i,j,direzione.GIU));
					}
					else if(i-j==4 && i!=4 && i!=8) {
						listaSpot.add(new Spot(i,j,direzione.GIU, direzione.SINISTRA_GIU));
					}
					else if(i==8 && j==4) {
						listaSpot.add(new Spot(i,j,direzione.SINISTRA_GIU));
					}
					else if(i==8 && j>5 && j<11) {
						listaSpot.add(new Spot(i,j,direzione.SINISTRA_GIU, direzione.SINISTRA_SU));
					}
					else if(i==8 && j==12) {
						listaSpot.add(new Spot(i,j,direzione.SINISTRA_SU));
					}
					else if(i+j==20 && i!=4 && i!=8) {
						listaSpot.add(new Spot(i,j,direzione.SINISTRA_SU, direzione.SU));
					}
					else if(i==4 && j==16) {
						listaSpot.add(new Spot(i,j,direzione.SU));
					}
					else if(j-i==12 && i!=0 && i!=4) {
						listaSpot.add(new Spot(i,j,direzione.SU, direzione.DESTRA_SU));
					}
					else if(i==0 && j==12) {
						listaSpot.add(new Spot(i,j,direzione.DESTRA_SU));
					}
					else if(i==0 && j>5 && j<11) {
						listaSpot.add(new Spot(i,j,direzione.DESTRA_SU, direzione.DESTRA_GIU));
					}
					else if(i+j>4 && i-j<4 && i+j<20 && j-i<12) {
						listaPunti.add(new Punto(i,j));
					}
				}					
			}
		}

		pedineNere = new ArrayList<Pedina>();
		for(int i=0; i<12; i++) {
			pedineNere.add(new Pedina(0,0,colore.NERA));
		}

		suggerimento = new Spot[2];
		for(int i=0; i<2; i++) {
			suggerimento[i] = new Spot();
		}

	}
	
	public void initDlv() {
		handler = new DesktopHandler(new DLV2DesktopService("lib/dlv2"));

		// register the class for reflection
		try {
			ASPMapper.getInstance().registerClass(Spot.class);
			ASPMapper.getInstance().registerClass(Scelgo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		InputProgram facts = new ASPInputProgram();
		for(Spot punto : listaSpot) {
			try {
				facts.addObjectInput(punto);
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

		sceltadlv = new Scelgo(2,0);
		int as = 0;
		int oggetti = 0;
		int scelgo = 0;
		for(AnswerSet a : answers.getAnswersets()){
			as++;
			try {
				for(Object obj : a.getAtoms()){
					oggetti++;

					if(obj instanceof Scelgo) {
						scelgo++;
						sceltadlv = (Scelgo) obj;
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			} 

		}
		System.out.println(as);
		System.out.println(oggetti);
		System.out.println(scelgo);

		System.out.println(sceltadlv.getX());
		System.out.println(sceltadlv.getY());


	}
	

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		g.drawImage(scacchiera,0,0,700,700,this);

		g.drawImage(damaBianca, bottone.getX(), bottone.getY(), bottone.getLarghezza(), bottone.getAltezza(), this);
		
		for(Pedina pedina : pedineNere) {
			g.drawImage(damaNera, pedina.getX()*60-20+110, pedina.getY()*35-20+70, 40, 40, this);
		}

		for(Spot punto : listaSpot) {
			if(punto.isFocus(x, y)) {
				g.drawImage(puntoRosso, punto.getX()*60-20+110, punto.getY()*35-20+70, 40, 40, this);  // fare funzione

				for(direzione dir : punto.getDirezioni()) { 
					if(dir == direzione.SU)  g.drawImage(puntoVerde, punto.getX()*60-20+110, punto.getY()*35-20+70-70, 40, 40, this);
					if(dir == direzione.DESTRA_SU)  g.drawImage(puntoVerde, punto.getX()*60-20+110+60, punto.getY()*35-20+70-35, 40, 40, this);
					if(dir == direzione.DESTRA_GIU)  g.drawImage(puntoVerde, punto.getX()*60-20+110+60, punto.getY()*35-20+70+35, 40, 40, this);
					if(dir == direzione.GIU)  g.drawImage(puntoVerde, punto.getX()*60-20+110, punto.getY()*35-20+70+70, 40, 40, this);
					if(dir == direzione.SINISTRA_GIU)  g.drawImage(puntoVerde, punto.getX()*60-20+110-60, punto.getY()*35-20+70+35, 40, 40, this);
					if(dir == direzione.SINISTRA_SU)  g.drawImage(puntoVerde, punto.getX()*60-20+110-60, punto.getY()*35-20+70-35, 40, 40, this);
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


