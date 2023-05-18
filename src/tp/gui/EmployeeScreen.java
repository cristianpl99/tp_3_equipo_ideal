package tp.gui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import tp.logic.Employee;
import java.awt.Font;
import java.awt.Image;


import java.io.File;

public class EmployeeScreen extends JFrame {

    private JPanel contentPane;
    private JLabel lblPhoto;
    private JLabel lblFirstName;
    private JLabel lblLastName;
    private JLabel lblRating;
    private JLabel lblRole;

    public EmployeeScreen(Employee employee) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 526, 297);
        setTitle("Id " + employee.getId() + " - Employee Record");
        ImageIcon icon = new ImageIcon("src/tp/dal/images/icon.png");
        setIconImage(icon.getImage());
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblFirstName = new JLabel("First Name: " + employee.getFirstName());
        lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblFirstName.setBounds(10, 26, 200, 20);
        contentPane.add(lblFirstName);

        lblLastName = new JLabel("Last Name: " + employee.getLastName());
        lblLastName.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblLastName.setBounds(10, 57, 200, 20);
        contentPane.add(lblLastName);

        lblRating = new JLabel("Rating: " + employee.getRating());
        lblRating.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblRating.setBounds(10, 88, 200, 20);
        contentPane.add(lblRating);

        lblRole = new JLabel("Role: " + employee.getRole());
        lblRole.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblRole.setBounds(10, 119, 200, 20);
        contentPane.add(lblRole);

        lblPhoto = new JLabel();
        lblPhoto.setBounds(259, 11, 241, 236);
        contentPane.add(lblPhoto);

        if (employee.getPhoto() != null && !employee.getPhoto().isEmpty()) {
            String photoPath = employee.getPhoto();
            File photoFile = new File(photoPath);
            if (photoFile.exists()) {
                ImageIcon photoIcon = new ImageIcon(photoFile.getAbsolutePath());
                Image scaledImage = photoIcon.getImage().getScaledInstance(lblPhoto.getWidth(), lblPhoto.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledPhotoIcon = new ImageIcon(scaledImage);
                lblPhoto.setIcon(scaledPhotoIcon);
            }
        }
    
}
}



