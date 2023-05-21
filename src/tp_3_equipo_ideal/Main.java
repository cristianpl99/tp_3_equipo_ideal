package tp_3_equipo_ideal;

import javax.swing.UIManager;

import tp.gui.HomeScreen;

public class Main {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			System.out.println(e);
		}
		HomeScreen launch = new HomeScreen();
		launch.setResizable(false);
		launch.setVisible(true);
		launch.setLocationRelativeTo(null);
	}

}
