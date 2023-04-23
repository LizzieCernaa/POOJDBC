import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AlumnoRepository {
    private String url = "jdbc:postgresql://localhost:5432/poo";
    private String UserName = "postgres";
    private String password = "NADA12345";
    private Connection conn;

    private String error;
    public AlumnoRepository()
    {
        error = "";
    }
    public boolean Conectar()
    {
        try {
            conn = DriverManager.getConnection(url, UserName, password);
            return true;
        }catch (Exception ex)
        {
           error= ex.getMessage();
            return false;
        }
    }

    public boolean Desconectar()
    {
        try {
            conn.close();
            return true;
        }catch (Exception ex)
        {
            error= ex.getMessage();
            return false;
        }
    }

    public boolean AgregarAlumno(Alumno alumno)
    {
        try {
            this.Conectar();

            String sql = "INSERT INTO alumno ( fecha_nacimiento, nombre, apellido, telefono, direccion, estatura, peso, correo, nacionalidad, sexo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDate(1, Date.valueOf(alumno.getFechaNacimiento()));
            statement.setString(2, alumno.getNombre());
            statement.setString(3, alumno.getApellido());
            statement.setString(4, alumno.getTelefono());
            statement.setString(5, alumno.getDireccion());
            statement.setDouble(6, alumno.getEstatura());
            statement.setDouble(7, alumno.getPeso());
            statement.setString(8, alumno.getCorreo());
            statement.setString(9, alumno.getNacionalidad());
            statement.setString(10, alumno.getSexo());

            int filasInsertadas = statement.executeUpdate();
            statement.close();
            this.Desconectar();
            if (filasInsertadas > 0) {
               return true;
            } else {
               error = "No se pudo insertar el alumno";
                return false;
            }

        }catch (Exception ex)
        {
            error= ex.getMessage();
            return false;
        }
    }
    public boolean ActualizarAlumno(Alumno alumno)
    {
        try {
            this.Conectar();
            String sql = "UPDATE alumnos SET fecha_nacimiento =?, nombre=?, apellido =?, telefono =?, direccion =?," +
                        " estatura =?, peso =?, correo =?, nacionalidad =?, sexo =? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, alumno.getFechaNacimiento());
            statement.setString(2, alumno.getNombre());
            statement.setString(3, alumno.getApellido());
            statement.setString(4, alumno.getTelefono());
            statement.setString(5, alumno.getDireccion());
            statement.setDouble(6, alumno.getEstatura());
            statement.setDouble(7, alumno.getPeso());
            statement.setString(8, alumno.getCorreo());
            statement.setString(9, alumno.getNacionalidad());
            statement.setString(10, alumno.getSexo());
            statement.setLong(11, alumno.getId());

            int filasActualizadas = statement.executeUpdate();
            statement.close();
            this.Desconectar();
            if (filasActualizadas > 0) {
                return true;
            } else {
                error = "No se pudo actualizar al alumno";
                return false;
            }

        }catch (Exception ex)
        {
            error= ex.getMessage();
            return false;
        }
    }

    public boolean EliminarAlumno (Long id)
    {
        try {
            this.Conectar();
            String query = "DELETE FROM alumno WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            int filasAfectadas = statement.executeUpdate();
            statement.close();
            this.Desconectar();
            if (filasAfectadas > 0) {
                return true;
            } else {
                error = "No se pudo eliminar al alumno";
                return false;
            }

        }
        catch (Exception ex)
        {
            error= ex.getMessage();
            return false;
        }
    }

    public boolean EliminarTodosAlumnos ()
    {
        try {
            this.Conectar();
            String query = "DELETE FROM alumno";
            PreparedStatement statement = conn.prepareStatement(query);
            int filasAfectadas = statement.executeUpdate();
            statement.close();
            this.Desconectar();
            if (filasAfectadas > 0) {
                return true;
            } else {
                error = "No se pudieron eliminar a los alumno";
                return false;
            }

        }
        catch (Exception ex)
        {
            error= ex.getMessage();
            return false;
        }
    }

    public String getError() {
        return error;
    }

}
