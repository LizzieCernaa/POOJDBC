import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrmAlumno extends JFrame{
    public JPanel panelPrincipal;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextArea textArea1;
    private JTextField textField5;
    private JTextField textField6;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JTextField textField7;
    private JRadioButton mujerRadioButton;
    private JRadioButton hombreRadioButton;
    private JButton btnInsertar;
    private JButton btnEliminar;
    private JButton btnEliminarTodo;
    private JButton btnModificar;
    private JLabel txtID;
    private JLabel txtApellido;
    private JLabel txtNombre;
    private JLabel txtNacimiento;
    private JLabel txtDireccion;
    private JLabel txtTelefono;
    private JLabel txtcorreo;
    private JLabel txtEstatura;
    private JLabel txtPeso;
    private JLabel txtNacionalidad;
    private JLabel txtSexo;
    private JTable TblDatos;

    public FrmAlumno ()
    {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Edad");


        modelo.addRow(new Object[]{"Juan", "Pérez", 30});
        modelo.addRow(new Object[]{"María", "Gómez", 25});
        modelo.addRow(new Object[]{"Pedro", "Fernández", 40});


        TblDatos.setModel(modelo);

    }
    private void createUIComponents() {
        // TODO: place custom component creation code here



    }
}
