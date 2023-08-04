package database;

import database.Database;
import emotionalsongs.JListUtility;
import emotionalsongs.Registrazione;
import emotionalsongs.Utente;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface InterfacciaDatabase extends Remote{
    public Database getInstance() throws SQLException, RemoteException;
    public Statement getStatement() throws RemoteException;
    public Connection getConnection() throws RemoteException;

    void Registrazione(Utente user) throws RemoteException,SQLException;

      Boolean Login(String username, String password) throws SQLException, RemoteException;

      Utente QueryLogin(Query query) throws SQLException, RemoteException;

      JListUtility QueryNomiPlaylist(Query query) throws SQLException, RemoteException;
}
