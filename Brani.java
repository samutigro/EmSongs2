package emotionalsongs;

import database.Database;
import database.InterfacciaDatabase;
import database.Query;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.*;
import java.util.ArrayList;
//import javax.mail.*;
//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
class Brani {
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

    Brani() throws RemoteException, NotBoundException {
    }

    /**
     *
     * @param brano brano da dare in input al metodo, che restituirà tutte le canzoni con la stringa del brano nel titolo
     * @param db database
     * @return ritora una matrice contennte i titoli e i rispettivi codici univoci
     * @throws SQLException eccezione sql
     */
    public static String[][] cercaBranoMusicale(String brano, Database db) throws SQLException, RemoteException{
        //query che cerca tutti i brani che hanno nel titolo la string brano
        String q = "select titolo, codcanz from canzoni where titolo like '%"+brano+"%'";
        Query query = new Query(q);
        String [][] matrice=databaseInterface.cercaBranoMusicaleTitolo(query);
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
        //query per cercare i titoli in base ad autore e anno
        String q = "select titolo, codcanz from canzoni where autore ='"+autore+"' and anno="+anno+" group by codcanz";
        Query query = new Query(q);
        String [][] matrice= databaseInterface.cercaBranoMusicaleAutAnno(query);
        return matrice;

    }

    /**
     *
     * @param codCanz codice univoco della canzone di cui si vuole visualizzare l'emozione
     * @param db database
     * @return
     * @throws SQLException
     */
    public static double[] visualizzaEmozioneBrano(String codCanz, Database db) throws SQLException {
        //query che chiede al db le emozioni e i voti relative ad esse in base ad un codice canzone, per
        // evitare le canzoni con il nome uguale
        String q = "select emozione, voto from emozioni where codcanz = '"+codCanz+"'";
        Query query = new Query(q);
        Statement stm = db.getStatement();
        ResultSet rs = stm.executeQuery(query.getQuery());
        //Amazement,Solemnity,Tenderness,Nostalgia,Calmness,Power,Joy,Tension,Sadness
        int Amazement=0,Solemnity=0,Tenderness=0,Nostalgia=0,Calmness=0,Power=0,Joy=0,Tension=0,Sadness=0;
        double votoAmazement=0,votoSolemnity=0,votoTenderness=0,votoNostalgia=0,votoCalmness=0,votoPower=0,votoJoy=0,votoTension=0,votoSadness=0;
        double [] array = new double [9];
        while(rs.next()){
            String emozione = rs.getString(1);
            double voto = rs.getDouble(2);

            switch (emozione){
                case "Amazement":
                    Amazement++;
                    votoAmazement+=voto;
                    break;
                case "Solemnity":
                    Solemnity++;
                    votoSolemnity+=voto;
                    break;
                case "Tenderness":
                    Tenderness++;
                    votoTenderness+=voto;
                    break;
                case "Nostalgia":
                    Nostalgia++;
                    votoNostalgia+=voto;
                    break;
                case "Calmness":
                    Calmness++;
                    votoCalmness+=voto;
                    break;
                case "Power":
                    Power++;
                    votoPower+=voto;
                    break;
                case "Joy":
                    Joy++;
                    votoJoy+=voto;
                    break;
                case "Tension":
                    Tension++;
                    votoTension+=voto;
                    break;
                case "Sadness":
                    Sadness++;
                    votoSadness+=voto;
                    break;
            }

        }
        array[0] = votoAmazement/Amazement;
        array[1] = votoSolemnity/Solemnity;
        array[2] = votoTenderness/Tenderness;
        array[3] = votoNostalgia/Nostalgia;
        array[4] = votoCalmness/Calmness;
        array[5] = votoPower/Power;
        array[6] = votoJoy/Joy;
        array[7] = votoTension/Tension;
        array[8] = votoSadness/Sadness;

        return array;

    }

    public static void registraVotoEmozione(Query query) throws SQLException, RemoteException {
        databaseInterface.RegistraVotoEmozione(query);
    }

    public static void registraPlaylist(emotionalsongs.Playlist playlist) throws SQLException, RemoteException {

        databaseInterface.RegistraPlaylist(playlist);


    }

    //metodo per popolare il database con il file della prof
    public static void popola(Database db)throws SQLException,IOException{
        FileReader fr = new FileReader("/Users/samueleauteri/Desktop/java/EmotionalSongs.java/src/database/FiveHundredThousandSongs.txt");
        BufferedReader br = new BufferedReader(fr);
        String line="";
        String anno="";
        String codcanz="";
        String autore="";
        String titolo="";
        String query="";
        int xxx=257788*2;
        String [] ar;
        query="insert into canzoni (codcanz,titolo,autore,anno) values (?,?,?,?)";
        PreparedStatement ps = db.getConnection().prepareStatement(query);
        do{ line=br.readLine();
            ar=line.split("<SEP>");
            anno=ar[0];
            codcanz=ar[1];
            autore=ar[2];
            titolo=ar[3];

            ps.setString(1,codcanz);
            ps.setString(2,titolo);
            ps.setString(3,autore);
            ps.setInt(4,Integer.parseInt(anno));
            ps.executeUpdate();
            xxx--;
        }while(xxx!=0);
    }

    //metodo per cercare i brani delle canzoni contenute in una playlist sotto forma di codici
    public static ArrayList<String> cercaBraniPlaylist(String nomePlaylist, Database db ) throws SQLException {
        String q = "select titolo from canzoni where codcanz IN (select codcanz from playlist where nomeplaylist = '" + nomePlaylist +"')";
        Query query = new Query(q);
        Statement stm = db.getStatement();
        ResultSet rs = stm.executeQuery(query.getQuery());
        ArrayList<String> arrayList = new ArrayList<>();

        while(rs.next()){
            arrayList.add(rs.getString(1));
        }

        return arrayList;

    }

   /* public boolean isValidEmail(String email) {
        boolean valid = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException e) {
            valid = false;
        }
        return valid;
    } */
}