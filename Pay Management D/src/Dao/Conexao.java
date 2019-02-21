
package Dao;
import java.sql.*;

import javax.swing.JOptionPane;
public class Conexao {
    
    public static Connection conexao() throws ClassNotFoundException{
        try{
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/PayD", "postgres", "305790");
            return con;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
