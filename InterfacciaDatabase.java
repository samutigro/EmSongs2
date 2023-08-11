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
import java.util.ArrayList;

public interface InterfacciaDatabase extends Remote{
    public Database getInstance() throws SQLException, RemoteException;
    public Statement getStatement() throws RemoteException;
    public Connection getConnection() throws RemoteException;

    void Registrazione(Utente user) throws RemoteException,SQLException;

      Boolean Login(String username, String password) throws SQLException, RemoteException;

      Utente QueryLogin(Query query) throws SQLException, RemoteException;

    ArrayList<String> QueryNomiPlaylist(Query query) throws SQLException, RemoteException;

    String[][] cercaBranoMusicaleTitolo(Query query) throws SQLException, RemoteException;

    ArrayList<String> QueryVisualizzaPlaylist(Query query) throws SQLException, RemoteException;

    String[][] cercaBranoMusicaleAutAnno(Query query) throws SQLException, RemoteException;

    void RegistraPlaylist(emotionalsongs.Playlist playlist) throws SQLException, RemoteException;

    ArrayList<String> QueryRicercaCanzoniGiaInPlaylist(Query query) throws SQLException, RemoteException;

    void RegistraVotoEmozione(Query query) throws SQLException, RemoteException;


}
