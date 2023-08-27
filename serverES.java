package Database;/*
Cermisoni Marco, MATRICOLA 748739, VA
Oldani Marco, MATRICOLA 748243, VA
De Vito Francesco, MATRICOLA 749044, VA
Auteri Samuele, MATRICOLA 749710, VA
*/

import EmotionalSongs.Utente;
import static EmotionalSongs.Registrazione.login;
import static EmotionalSongs.Registrazione.registrazione;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class serverES extends UnicastRemoteObject implements InterfacciaDatabase {
    public static final long serialVersionUID = 1L;
    Database db;
    Utente user;

    public serverES() throws RemoteException, SQLException {
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

    public void Registrazione(Utente user) throws RemoteException, SQLException {
        registrazione(user, db);
    }

    public Boolean Login (String username, String password) throws SQLException, RemoteException{
        Boolean flag;
        flag = login(username,password,db);
        return flag;
    }

    public Utente QueryLogin(Query query) throws SQLException, RemoteException{
        Statement stm = db.getInstance().getStatement();
        ResultSet rs = stm.executeQuery(query.getQuery());

        while(rs.next()){
            String codF = rs.getString(1);
            String nome = rs.getString(2);
            String cognome = rs.getString(3);
            String dataNascita = rs.getString(4);
            String email = rs.getString(5);
            String username = rs.getString(6);
            String password = rs.getString(7);
            user = new EmotionalSongs.Utente(nome, cognome, codF, dataNascita, email, username, password);
        }
        return user;
    }

    public String[][] cercaBranoMusicaleTitolo(Query query) throws SQLException{
        Statement stm = db.getStatement();
        ResultSet rs = stm.executeQuery(query.getQuery());
        rs.next();

        //Raccolta dei brani in un array e dei rispettivi codici
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayCod = new ArrayList<>();

        while(rs.next()){
            String tit = rs.getString(1);
            String cod = rs.getString(2);
            arrayList.add(tit);
            arrayCod.add(cod);
        }

        //Cambio gli arraylist in array
        Object[] arrayCanz = arrayList.toArray();
        Object[] arrayCodici = arrayCod.toArray();

        //Creo una matrice e la riempio in modo da avere due colonne; nella prima ci sarà il titolo e nella seconda colonna il suo codice
        String[][] matrice = new String[arrayList.size()][2];

        for(int i=0; i<matrice.length; i++){
            matrice[i][0] = arrayCanz[i].toString();
            matrice[i][1] = arrayCodici[i].toString();
        }
        return matrice;
    }

    public ArrayList<String> QueryVisualizzaPlaylist(Query query) throws SQLException, RemoteException{
        Statement stm = db.getStatement();
        ResultSet rs = stm.executeQuery(query.getQuery());
        ArrayList<String> arrayList = new ArrayList<>();

        while(rs.next()){
            String ris = rs.getString(1);
            arrayList.add(ris);
        }
        return arrayList;
    }

    public String[][] cercaBranoMusicaleAutAnno(Query query) throws SQLException, RemoteException{
        Statement stm = db.getStatement();
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayCod = new ArrayList<>();
        ResultSet rs = stm.executeQuery(query.getQuery());

        //Raccolta dei brani in un array e dei rispettivi codici
        while(rs.next()){
            String tit = rs.getString(1);
            String codice = rs.getString(2);
            arrayList.add(tit);
            arrayCod.add(codice);
        }

        //Cambio gli arraylist in array
        Object[] arrayCanz = arrayList.toArray();
        Object[] arrayCodici = arrayCod.toArray();

        //Creo una matrice e la riempio in modo da avere due colonne; nella prima ci sarà il titolo e nella seconda colonna il suo codice
        String[][] matrice = new String[arrayList.size()][2];

        for(int i=0; i<matrice.length; i++){
            matrice[i][0] = arrayCanz[i].toString();
            matrice[i][1] = arrayCodici[i].toString();
        }
        return matrice;

    }

    public void RegistraPlaylist(EmotionalSongs.Playlist playlist) throws SQLException, RemoteException{
        Statement stm = db.getStatement();

        if((playlist.lunghezza)!=0){
            Object[] array = playlist.getCanzoni().toArray();

            for(int i=0; i<array.length; i++){
                String query="insert into playlist(codcanz,nomeplaylist,codf) Values('"+array[i]+"','"+playlist.getNomePlaylist()+"','"+playlist.getAutore().getCodiceFiscale()+"')";
                Query q = new Query(query);
                stm.executeUpdate(q.getQuery());
            }
        }
    }

    public ArrayList<String> QueryRicercaCanzoniGiaInPlaylist(Query query) throws SQLException, RemoteException{
        Statement stm = db.getStatement();
        ResultSet rs = stm.executeQuery(query.getQuery());
        ArrayList<String> arrayList = new ArrayList<>();

        while(rs.next()){
            String ris = rs.getString(1);
            arrayList.add(ris);
        }
        return arrayList;
    }

    public void RegistraVotoEmozione(Query query) throws SQLException, RemoteException{
        Statement stm = db.getStatement();
        stm.executeUpdate(query.getQuery());
    }

    public ArrayList<String>  QueryCercaVoti (Query query) throws SQLException, RemoteException{
        Statement stm = db.getStatement();
        ResultSet rs = stm.executeQuery(query.getQuery());
        ArrayList<String> arrayList = new ArrayList<>();

        while(rs.next()){
            String ris1 = rs.getString(1);
            String ris2 = rs.getString(2);
            arrayList.add(ris1);
            arrayList.add(ris2);
        }
        return arrayList;
    }
}
