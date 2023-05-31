package tp.gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import tp.logic.Employee;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import java.io.File;

public class EmployeeScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblPhoto;
	private JLabel lblFirstName;
	private JLabel lblLastName;
	private JLabel lblRating;
	private JLabel lblRole;

	public EmployeeScreen(Employee employee) {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 526, 297);
		setTitle("Dni " + employee.getDni() + " - Employee Record");
		ImageIcon icon = new ImageIcon("src/tp/dal/images/icon.png");
		setIconImage(icon.getImage());

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblFirstName = createLabel("First Name: " + employee.getFirstName(), 12, 10, 26, 200, 20);
		lblLastName = createLabel("Last Name: " + employee.getLastName(), 12, 10, 57, 200, 20);
		lblRating = createLabel("Rating: " + employee.getRating(), 12, 10, 88, 200, 20);
		lblRole = createLabel("Role: " + employee.getRole(), 12, 10, 119, 200, 20);

		contentPane.add(lblFirstName);
		contentPane.add(lblLastName);
		contentPane.add(lblRating);
		contentPane.add(lblRole);

		lblPhoto = new JLabel();
		lblPhoto.setBounds(259, 11, 241, 236);
		contentPane.add(lblPhoto);

		if (employee.getPhoto() != null && !employee.getPhoto().isEmpty()) {
			String photoPath = employee.getPhoto();
			File photoFile = new File(photoPath);
			if (photoFile.exists()) {
				ImageIcon photoIcon = new ImageIcon(photoFile.getAbsolutePath());
				Image scaledImage = photoIcon.getImage().getScaledInstance(lblPhoto.getWidth(), lblPhoto.getHeight(),
						Image.SCALE_SMOOTH);
				ImageIcon scaledPhotoIcon = new ImageIcon(scaledImage);
				lblPhoto.setIcon(scaledPhotoIcon);
			}
		}
	}

	private JLabel createLabel(String text, int fontSize, int x, int y, int width, int height) {
		JLabel label = new JLabel(text);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Tahoma", Font.BOLD, fontSize));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setBounds(x, y, width, height);
		return label;
	}

}