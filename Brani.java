package EmotionalSongs;/*
Cermisoni Marco, MATRICOLA 748739, VA
Oldani Marco, MATRICOLA 748243, VA
De Vito Francesco, MATRICOLA 749044, VA
Auteri Samuele, MATRICOLA 749710, VA
*/

import Database.Database;
import Database.Database;
import Database.InterfacciaDatabase;
import Database.Query;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Brani {
    static Registry registry;

    static {
        try {
            registry = LocateRegistry.getRegistry("127.0.0.1", 8999);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    static InterfacciaDatabase databaseInterface;

    static {
        try {
            databaseInterface = (InterfacciaDatabase)registry.lookup("SERVER");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param brano brano da dare in input al metodo, che restituirà tutte le canzoni con la stringa del brano nel titolo
     * @param db database
     * @return ritorna una matrice contenente i titoli e i rispettivi codici univoci
     * @throws SQLException eccezione sql
     */
    public static String[][] cercaBranoMusicale(String brano, Database db) throws SQLException, RemoteException{
        //Database.Query che cerca tutti i brani che hanno nel titolo la string brano
        String q = "select titolo, codcanz from canzoni where titolo like '%"+brano+"%'";
        Query query = new Query(q);
        String[][] matrice = databaseInterface.cercaBranoMusicaleTitolo(query);
        return matrice;
    }

    /**
     *
     * @param autore nome dell'autore di cui si vogliono cercare i brani
     * @param anno anno di uscita del brano
     * @param db database
     * @return
     * @throws SQLException
     */
    public static String[][] cercaBranoMusicale(String autore, int anno, Database db) throws SQLException, RemoteException {
        //Database.Query per cercare i titoli in base ad autore e anno
        String q = "select titolo, codcanz from canzoni where autore ='"+autore+"' and anno="+anno+" group by codcanz";
        Query query = new Query(q);
        String[][] matrice = databaseInterface.cercaBranoMusicaleAutAnno(query);
        return matrice;
    }

    public static void registraVotoEmozione(Query query) throws SQLException, RemoteException {
        databaseInterface.RegistraVotoEmozione(query);
    }

    public static void registraPlaylist(Playlist playlist) throws SQLException, RemoteException {
        databaseInterface.RegistraPlaylist(playlist);
    }

    public static String convertDateFormat(String inputDateStr) {
        String outputDateStr = null;
        try {
            // Definisce il formato di input
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            // Parsa la data di input
            Date inputDate =  inputDateFormat.parse(inputDateStr);

            // Definisce il formato di output
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Formatta la data nel nuovo formato
            outputDateStr = outputDateFormat.format(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateStr;
    }
}
