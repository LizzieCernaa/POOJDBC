import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.regex.Pattern;

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
    private JRadioButton rbh;
    private JButton btnInsertar;
    private JButton btnEliminar;
    private JButton btnEliminarTodo;
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
    private JRadioButton rbm;

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

        ButtonGroup group = new ButtonGroup();
        group.add(rbm);
        group.add(rbh);


        btnInsertar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (!ValidarFormulario()) return;


                String id = txtID.getText();
                String name = txtNombre.getText();
                String lastName = txtApellido.getText();
                String birthdate = txtNacimiento.getText();
                String address = txtDireccion.getText();
                String phone = txtTelefono.getText();
                String mail = txtcorreo.getText();
                String height = txtEstatura.getValue().toString();
                String weight = txtPeso.getValue().toString();
                String nationality = txtNacionalidad.getText();
                String sex = rbm.isSelected() ? "M" : "H";


                Alumno alumno = new Alumno();
                alumno.setId(Long.parseLong(id));
                alumno.setNombre(name);
                alumno.setApellido(lastName);
                alumno.setFechaNacimiento(birthdate);
                alumno.setDireccion(address);
                alumno.setTelefono(phone);
                alumno.setCorreo(mail);
                alumno.setNacionalidad(nationality);
                alumno.setSexo(sex);
                alumno.setEstatura(Double.parseDouble(height));
                alumno.setPeso(Double.parseDouble(weight));



                AlumnoRepository respository = new AlumnoRepository();
                if (respository.AgregarAlumno(alumno))
                {
                    modelo.addRow(new Object[]{id, name, lastName, birthdate, address, phone, mail, height,weight,nationality,sex});
                    LimpiarFormulario();
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Error agregando alumno "+ respository.getError());
                }

            }
        });
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here



    }

    private void LimpiarFormulario ()
    {
        String vacio ="";
        txtID.setText(vacio);
        txtcorreo.setText(vacio);
        txtApellido.setText(vacio);
        txtDireccion.setText(vacio);
        txtEstatura.setValue(0);
        txtNacionalidad.setText(vacio);
        txtNacimiento.setText(vacio);
        txtPeso.setValue(0);
        txtTelefono.setText(vacio);
        rbm.setSelected(true);
        txtNombre.setText(vacio);
    }

    private boolean ValidarFormulario()
    {
        boolean isValid = true;


        boolean isFilled =  txtNombre.getText().length() > 0 && txtApellido.getText().length() > 0
                && txtNacimiento.getText().length() > 0 && txtDireccion.getText().length() > 0
                && txtTelefono.getText().length() > 0 && txtcorreo.getText().length() > 0
                && txtEstatura.getValue().toString() != "0" && txtPeso.getValue().toString() != "0"
                && txtNacionalidad.getText().length() > 0;

        if ( !isFilled )
        {
            JOptionPane.showMessageDialog(this,"Debe de llenar todos los campos");
            isValid = false;
        }

        //Validando formato fecha

        if (!validarFormatoFecha(txtNacimiento.getText()))
        {
            JOptionPane.showMessageDialog(this,"La fecha no es valida. Debe ser dia/mes/año");
            isValid = false;
        }

        //validando telefono
        if (!validarFormatoTelefono(txtTelefono.getText()))
        {
            JOptionPane.showMessageDialog(this,"Numero de telefono no valido, Debe de ser 9999-9999");
            isValid = false;
        }

        //validando correo
        if (!validarFormatoCorreo(txtcorreo.getText()))
        {
            JOptionPane.showMessageDialog(this,"Correo electronico no valido");
            isValid = false;
        }

        //Validando Peso
        if (!validarPeso(txtPeso.getValue().toString()))
        {
            JOptionPane.showMessageDialog(this,"Peso no valido");
            isValid = false;
        }

        //Validando Estatura
        if (!validarEstatura(txtEstatura.getValue().toString()))
        {
            JOptionPane.showMessageDialog(this,"Estatura no valida");
            isValid = false;
        }

        return isValid;
    }

    public static boolean validarFormatoFecha(String txtFecha) {
        String formato = "yyyy-MM-dd";

        LocalDate fechaInicio = LocalDate.of(1923, 1, 1);
        LocalDate fechaFin = LocalDate.now().minusYears(15);

        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(txtFecha);
            Instant instant = Instant.ofEpochMilli(date.getTime());
            LocalDate fecha = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();

            return (fecha.isEqual(fechaInicio) || fecha.isAfter(fechaInicio))
                    && (fecha.isEqual(fechaFin) || fecha.isBefore(fechaFin));

        } catch (ParseException e) {
            return false;
        }
    }
        public static boolean validarFormatoTelefono(String telefono) {
            String formatoTelefono = "^\\d{4}-\\d{4}$"; // formato esperado: cuatro dígitos numéricos, un guion, cuatro dígitos numéricos
            Pattern patron = Pattern.compile(formatoTelefono);
            return patron.matcher(telefono).matches();
        }


        public static boolean validarFormatoCorreo(String correo) {
            String formatoCorreo = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"; // formato esperado: [cadena de caracteres alfanuméricos y guiones bajos]@[cadena de caracteres alfanuméricos y guiones bajos].[cadena de caracteres alfanuméricos]
            Pattern patron = Pattern.compile(formatoCorreo);
            return patron.matcher(correo).matches();
        }


    public boolean validarEstatura(String estatura) {
        try {
            double valorEstatura = Double.parseDouble(estatura); // Verificar si el valor de la estatura es un número válido
            // Verificar que el valor de la estatura esté dentro de un rango razonable basado en el contexto de la aplicación o sistema
            return (valorEstatura >= 120 && valorEstatura <= 220);

        } catch (NumberFormatException e) {
            return false; // Si el valor de la estatura no es un número válido, devuelve false
        }
    }

    public boolean validarPeso(String peso) {
        try {
            double validarPeso = Double.parseDouble(peso); // Verificar si el valor de la estatura es un número válido
            // Verificar que el valor de la estatura esté dentro de un rango razonable basado en el contexto de la aplicación o sistema
            return (validarPeso >= 50 && validarPeso <= 300);

        } catch (NumberFormatException e) {
            return false; // Si el valor de la estatura no es un número válido, devuelve false
        }
    }
}


