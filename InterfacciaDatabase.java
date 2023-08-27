package Database;/*
Cermisoni Marco, MATRICOLA 748739, VA
Oldani Marco, MATRICOLA 748243, VA
De Vito Francesco, MATRICOLA 749044, VA
Auteri Samuele, MATRICOLA 749710, VA
*/

import Database.Database;
import EmotionalSongs.Utente;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InterfacciaDatabase extends Remote{
    Database getInstance() throws SQLException, RemoteException;

    void Registrazione(Utente user) throws RemoteException,SQLException;

    Boolean Login(String username, String password) throws SQLException, RemoteException;

    Utente QueryLogin(Query query) throws SQLException, RemoteException;

    String[][] cercaBranoMusicaleTitolo(Query query) throws SQLException, RemoteException;

    ArrayList<String> QueryVisualizzaPlaylist(Query query) throws SQLException, RemoteException;

    String[][] cercaBranoMusicaleAutAnno(Query query) throws SQLException, RemoteException;

    void RegistraPlaylist(EmotionalSongs.Playlist playlist) throws SQLException, RemoteException;

    ArrayList<String> QueryRicercaCanzoniGiaInPlaylist(Query query) throws SQLException, RemoteException;

    void RegistraVotoEmozione(Query query) throws SQLException, RemoteException;

    ArrayList<String>  QueryCercaVoti (Query query) throws SQLException, RemoteException;
}