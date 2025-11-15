package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jefrey
 */
public class Conexion {
    
    // Atributos que solo usara esta clase (variables privadas)
    
    private Connection conexion = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    // Datos para conectar con Mysql (la base de datos)
    
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca_db";
    private static final String USUARIO = "root";
    private static final String CONTRASENIA = "123456";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Constructor, establece la conexion a la base de datos 
    
    public Conexion() {
        try {
            
            // Paso 1 se carga el driver de Mysql
            Class.forName(DRIVER); // carga una clase por su nombre
            System.out.println("Driver de Mysql cargado correctamente");
            
            // Paso 2 se establece conexion con la DB
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENIA); // LOS TRES PARAMETROS QUE NECESITA, DriverManager es una clase que maneja las conexiones y el getConnection() las obtiene. 
            System.out.println("Conexion a la DB establecida correctamente");
            
            // Paso 3 el Statement para las consultas del Mysql
            statement = conexion.createStatement(); // createStatement crea un ejecutador de comandos SQL
            System.out.println("Statement creado correctamente");
            
            
        } catch (ClassNotFoundException e) {
            // Manda el error que no se encontro ninguna clase, en este caso (DRIVER)
            System.err.println("Error no se encontro el DRIVER de Mysql: " + e.getMessage());
            System.exit(0);
            
            // Problemas con la conexion de la SQL (errores de usuario, contrasenia o no existe la base de datos)
        } catch (SQLException e) {
            System.err.println("Error fallo en la conexion SQL: " + e.getMessage());
            // Detenemos el sistema
            System.exit(0);
        }
    }
    
    // Metodo que devuelve la conexion activa de MySQL para otros modulos
    
    public Connection obtenerConexion() {
        // Devuelve la conexion (la variable)
        return conexion;
    }
    
    // Metodo para los resultados de la consulta SQL
    
    // (RusultSet = tipo de dato que devuelve) (ejcutarConsulta = nombre del metodo) (String sql = parametro que recibe un txt que seria la consulta).
    public ResultSet ejecutarConsulta (String sql) {
        
        try {
            // Ejecuta la consulta SQL
            // "resultSet" es donde se almacena la consulta sql, "statement" es el ejecutor para hacer las consultas, .executeQuery es el metodo para hacer el SELECT.
            resultSet = statement.executeQuery(sql);
            System.out.println("Consulta ejecutada: " + sql);
            
            // Devuelve el objeto con los resultados
            return resultSet; 
            
        } catch (SQLException e) {
            System.err.println("ERROR en la consulta: " + e.getMessage());
            System.err.println("SQL: " + sql);
            
            // Si no funciona se devuelve un valor nulo
            return null;        
        } 
    }
    
    // Metodo para ejecutar INSERT, UPDATE, DELETE
    
    // (boolean = tipo de dato que devuelve) (ejcutarActualizacion = nombre del metodo) (String sql = parametro que recibe un txt que seria la consulta).
    public boolean ejecutarActualizacion (String sql) {
        
        try {
            // Ejecuta la sentencia INSERT, UPDATE o DELETE
            // "statement" es el ejecutor para hacer las consultas, .executeUpdate es el metodo para hacer INSERT, UPDATE, DELETE.
            statement.executeUpdate(sql);
            System.out.println("Actualizacion ejecutada: " + sql);
            
            // Si llegó aquí sin error, retorna true (éxito)
            return true;
            
        } catch (SQLException e) {
            System.err.println("ERROR en la actualizacion: " + e.getMessage());
            System.err.println("SQL: " + sql);
            
            // Si no funciona se devuelve false
            return false;        
        } 
    }
    
    // Metodo para probar si la conexion funciona
    
    // (boolean = tipo de dato que devuelve) (probarConexion = nombre del metodo)
    public boolean probarConexion() {
        
        try {
            // Verifica si la conexion esta cerrada
            // isClosed() devuelve true si esta cerrada, false si esta abierta
            if (conexion.isClosed()) {
                System.err.println("ERROR: La conexion esta cerrada");
                return false;
            }
            
            // Si llega aqui, la conexion esta abierta
            System.out.println("Conexion a BD verificada - TODO OK");
            return true;
            
        } catch (SQLException e) {
            System.err.println("ERROR al probar conexion: " + e.getMessage());
            return false;
        }
    }
    
    // Metodo para cerrar la conexion
    // IMPORTANTE: Siempre llamar a este metodo cuando se termine
    
    // (void = no devuelve nada) (cerrarConexion = nombre del metodo)
    public void cerrarConexion() {
        
        try {
            // Cerrar ResultSet si existe
            // Verificamos que no sea null y que no este ya cerrado
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
                System.out.println("ResultSet cerrado");
            }
            
            // Cerrar Statement si existe
            // Verificamos que no sea null y que no este ya cerrado
            if (statement != null && !statement.isClosed()) {
                statement.close();
                System.out.println("Statement cerrado");
            }
            
            // Cerrar Connection si existe
            // Verificamos que no sea null y que no este ya cerrada
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexion cerrada correctamente");
            }
            
        } catch (SQLException e) {
            System.err.println("ERROR al cerrar conexion: " + e.getMessage());
        }
    }
    
}