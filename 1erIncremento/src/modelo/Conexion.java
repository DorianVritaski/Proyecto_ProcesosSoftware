package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public static Connection getConexion(){
        
        //cambiar la ip con la ip de la pc que contenga la base de datos.
        String url = "jdbc:sqlserver://JhannPier\\SQLEXPRESS:1433;"
                + "database=DBProyectoProcesos;"
                + "user=sa;"
                + "password=12345678;"
                + "encrypt=true;" 
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        try{
            Connection con = DriverManager.getConnection(url);
            return con;
        }catch(SQLException e){
            System.out.println(e.toString());
            return null;
        }
    } 
}
