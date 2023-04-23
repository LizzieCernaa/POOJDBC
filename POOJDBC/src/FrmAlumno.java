import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
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
    private JButton btnEditar;
    private JButton btnNuevo;

    public FrmAlumno ()
    {
        ButtonGroup group = new ButtonGroup();
        group.add(rbm);
        group.add(rbh);
        CargarAlumnos();

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

                boolean resultado = (alumno.getId() == 0)? respository.AgregarAlumno(alumno): respository.ActualizarAlumno(alumno);

                if (resultado)
                {
                   CargarAlumnos();
                   LimpiarFormulario();
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Error al guardar cambios "+ respository.getError());
                }

            }
        });
        btnEliminarTodo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea Elimar a todos los alumnos?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    // El usuario seleccionó "Sí".
                    AlumnoRepository repository = new AlumnoRepository();
                    if (repository.EliminarTodosAlumnos()){
                        CargarAlumnos();
                        JOptionPane.showConfirmDialog(null, "Alumnos Eliminados");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Error al intentar eliminar a los alumnos" + repository.getError());
                    }
                }
            }
        });
        TblDatos.addMouseListener(new MouseAdapter() {
        });
        btnEditar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int selectedRow = TblDatos.getSelectedRow(); // Obtiene el índice de la fila seleccionada

                if (selectedRow == -1) { // Se asegura de que haya una fila seleccionada
                   JOptionPane.showMessageDialog(null,"Debe de seleccionar un Alumno");
                   return;
                }
                TableModel model = TblDatos.getModel();
                int id = Integer.parseInt(model.getValueAt(selectedRow,0).toString());

                AlumnoRepository repository = new AlumnoRepository();
                Alumno alumno = new Alumno();
                alumno = repository.ObtenerAlumno(id);
                txtID.setText(Long.toString(alumno.getId()));
                txtcorreo.setText(alumno.getCorreo());
                txtApellido.setText(alumno.getApellido());
                txtDireccion.setText(alumno.getDireccion());
                txtEstatura.setValue(alumno.getEstatura());
                txtNacionalidad.setText(alumno.getNacionalidad());
                txtNacimiento.setText(alumno.getFechaNacimiento());
                txtPeso.setValue(alumno.getPeso());
                txtTelefono.setText(alumno.getTelefono());
                txtNombre.setText(alumno.getNombre());
                if (alumno.getSexo().equals("M")){
                    rbm.setSelected(true);
                }
                else {
                    rbh.setSelected(true);
                }
            }
        });
        btnNuevo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                LimpiarFormulario();
            }
        });
        btnEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = TblDatos.getSelectedRow(); // Obtiene el índice de la fila seleccionada

                if (selectedRow == -1) { // Se asegura de que haya una fila seleccionada
                    JOptionPane.showMessageDialog(null,"Debe de seleccionar un Alumno");
                    return;
                }
                TableModel model = TblDatos.getModel();
                Long id = Long.parseLong(model.getValueAt(selectedRow,0).toString());
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar al alumno seleccionado?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    AlumnoRepository repository = new AlumnoRepository();
                    if (repository.EliminarAlumno(id)){
                        CargarAlumnos();
                    }else{
                        JOptionPane.showMessageDialog(null, "Error al intentar eliminar al alumno " + repository.getError());
                    }


                }

            }
        });
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    private void CargarAlumnos(){
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
        List<Alumno> alumnos = new ArrayList<>();
        AlumnoRepository respository = new AlumnoRepository();
        alumnos = respository.ListaAlumnos();

        for (Alumno alumno : alumnos) {
            modelo.addRow(new Object[]{alumno.getId(), alumno.getNombre(), alumno.getApellido(), alumno.getFechaNacimiento(), alumno.getDireccion(), alumno.getTelefono(),
                    alumno.getCorreo(), alumno.getEstatura(), alumno.getPeso(), alumno.getNacionalidad(), alumno.getSexo()});
        }
    }

    private void LimpiarFormulario ()
    {
        String vacio ="";
        txtID.setText("0");
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




