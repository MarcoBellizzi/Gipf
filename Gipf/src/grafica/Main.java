package grafica;

import javax.swing.JFrame;

public class Main {

	public static JFrame frame;
	
	public static void main(String[] args) {
		frame = new JFrame();
		frame.setSize(690,700);
		frame.setLocation(330, 0);
		frame.setContentPane(new Pannello());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);		
	}

}
