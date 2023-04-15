import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrmAlumno extends JFrame{
    public JPanel panelPrincipal;
    private JTextField txtID;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtNacimiento;
    private JTextArea txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtcorreo;
    private JSpinner txtEstatura;
    private JSpinner txtPeso;
    private JTextField txtNacionalidad;
    private JRadioButton hombreRadioButton;
    private JButton btnInsertar;
    private JButton btnEliminar;
    private JButton btnEliminarTodo;
    private JButton btnModificar;
    private JLabel lbID;
    private JLabel lbApellido;
    private JLabel lbNombre;
    private JLabel lbNacimiento;
    private JLabel lbDireccion;
    private JLabel lbTelefono;
    private JLabel lbcorreo;
    private JLabel lbEstatura;
    private JLabel lbPeso;
    private JLabel lbNacionalidad;
    private JLabel lbSexo;
    private JTable TblDatos;

    public FrmAlumno ()
    {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Fecha de nacimiento");
        modelo.addColumn("Direccion");
        modelo.addColumn("Telefono");
        modelo.addColumn("Correo");
        modelo.addColumn("Estatura(cm)");
        modelo.addColumn("Peso(libra)");
        modelo.addColumn("Nacionalidad");
        modelo.addColumn("Sexo");

        TblDatos.setModel(modelo);

        btnInsertar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String id = txtID.getText();
                String name = txtNombre.getText();
                String lastName = txtApellido.getText();
                String birthdate = txtNacimiento.getText();
                String address = txtDireccion.getText();
                String phone = txtTelefono.getText();
                String mail = txtcorreo.getText();
                String height = txtEstatura.getText();
                String weight = txtPeso.getText();
                String nationality = txtNacionalidad.getText();
                String sex = lbSexo.getText();


                modelo.addRow(new Object[]{id, name, lastName, birthdate, address, phone, mail, height,weight,nationality,sex});
            }
        });
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here



    }
}
