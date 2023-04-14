import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;

public class fomAlumno extends JFrame {
    public JPanel panelPrincipal;
    private JButton button1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;

    public fomAlumno() {
    button1.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            String url = "jdbc:postgresql://localhost:5432/test";
            String UserName = "postgres";
            String password = "NADA12345";
            try {
                Connection conn = DriverManager.getConnection(url, UserName, password);
                JOptionPane.showMessageDialog(null, "Coneccion Establecida");
            }catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null,"Error tratando de conectarse "+ ex.getMessage());
            }




        }

    });
}
}
