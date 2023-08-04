package emotionalsongs;

import database.*;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Registrazione {

    public static void registrazione(Utente utente, Database db) throws SQLException, RemoteException {
        Statement stm = db.getStatement();
        String in = "insert into utenti( nome, datanascita, cognome, codF, email, username, password) values ('" + utente.getNome() + "'" + "," + "'" + utente.getData() + "'" + "," + "'" + utente.getCognome() + "'" + "," + "'" +  utente.getCodiceFiscale() + "'" + "," + "'" + utente.getEmail() + "'" + "," + "'" + utente.getId() + "'" + "," + "'" + utente.getPassword() +"'" +")";
        Query query = new Query(in);
        stm.executeUpdate(query.getQuery());
    }

    public static Boolean login (String username, String password, Database db) throws SQLException, RemoteException {
        Statement stm = db.getStatement();
        String in = "select username, password from utenti where username = '" + username + "'";
        Query query = new Query(in);
        ResultSet rs=stm.executeQuery(query.getQuery());
        String user;
        String pwd;
        Boolean flag=false;
        while(rs.next() != false){
            user = rs.getString("username");
            pwd = rs.getString("password");
            if(user.equals(username) & pwd.equals(password)){
                flag = true;
                break;
            }
        }

        if(flag == true){
            return true;
        }else{
            return false;
        }
    }

}