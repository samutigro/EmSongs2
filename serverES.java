package database;

import emotionalsongs.JListUtility;
import emotionalsongs.Utente;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static emotionalsongs.Registrazione.login;
import static emotionalsongs.Registrazione.registrazione;

public class serverES extends UnicastRemoteObject implements InterfacciaDatabase {
    public static final long serialVersionUID = 1L;
    Database db;
    Utente user;

    protected serverES() throws RemoteException, SQLException {
        super();
        db = new Database();
    }
    public static void main(String[] args) throws RemoteException, SQLException {
        serverES serverImpl = new serverES();
        Registry registry = LocateRegistry.createRegistry(8999);
        registry.rebind("SERVER", serverImpl);
    }

    public Database getInstance() throws SQLException, RemoteException {
        return db.getInstance();
    }

    public Statement getStatement() throws RemoteException {
        return db.getStatement();
    }

    public Connection getConnection() throws RemoteException {
        return db.getConnection();
    }

    public void Registrazione(Utente user) throws RemoteException, SQLException {
        registrazione(user, db);
    }

    public Boolean Login (String username, String password) throws SQLException, RemoteException{
        Boolean flag;
        flag= login(username,password,db);
         return flag;
    }

    public Utente QueryLogin(Query query) throws SQLException, RemoteException{
        Statement stm = db.getInstance().getStatement();
        ResultSet rs = stm.executeQuery(query.getQuery());
        //String res = "";

        while(rs.next()){
            String codF = rs.getString(1);
            String nome = rs.getString(2);
            String cognome = rs.getString(3);
            String dataNascita = rs.getString(4);
            String email = rs.getString(5);
            String username = rs.getString(6);
            String password = rs.getString(7);
            user = new emotionalsongs.Utente(nome,cognome,codF,dataNascita,email,username,password);
        }
        return user;
    }

    public JListUtility QueryNomiPlaylist(Query query) throws SQLException, RemoteException{
        JListUtility lista = new JListUtility();

        Statement stm = db.getInstance().getStatement();
        ResultSet rs = stm.executeQuery(query.getQuery());

            while(rs.next()){
                String ris = rs.getString(1);
                lista.addStringToList(ris);
            }
            return lista;

    }
}