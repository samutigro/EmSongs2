package emotionalsongs;/*
Cermisoni Marco, MATRICOLA 748739, VA
Oldani Marco, MATRICOLA 748243, VA
De Vito Francesco, MATRICOLA 749044, VA
Auteri Samuele, MATRICOLA 749710, VA
*/

//Package della classe

//Importazione della libreria esterna
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Classe per la definizione e la gestione di playlist
 * @author
 * @author
 */
public class Playlist implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<String> canzoni;
    private Utente autore;
    private String nome;
    public int lunghezza;

    /**
     * Costruttore della classe emotionalsongs.Playlist avente due parametri
     * @param nomePlaylist Parametro di tipo String che esprime il nome della playlist
     * @param utente Parametro di tipo Utente che esprime il nome dell'autore della playlist
     */
    public Playlist(String nomePlaylist, Utente utente){
        this.nome = nomePlaylist;
        this.autore = utente;
        this.lunghezza = 0;
    }

    /**
     * Costruttore della classe emotionalsongs.Playlist avente tre parametri
     * @param nomePlaylist Parametro di tipo String che esprime il nome della playlist
     * @param utente Parametro di tipo Utente che esprime il nome dell'autore della playlist
     * @param canzoni Parametro di tipo ArrayList di stringhe rappresentante le canzoni della playlist
     */
    public Playlist(String nomePlaylist, Utente utente, ArrayList<String> canzoni){
        this.nome = nomePlaylist;
        this.autore = utente;
        this.canzoni = canzoni;
        this.lunghezza = canzoni.toArray().length;
    }

    /**
     * Metodo che restituisce tutte le canzoni in una playlist, se ce ne sono
     * @return Il metodo ritorna tutte le canzoni di una playlist tramite un ArrayList di stringhe; se non sono presenti canzoni all'interno della playlist il metodo ritorna null
     */
    public ArrayList<String> getCanzoni(){
        if(lunghezza!=0){
            return this.canzoni;
        }else{
            return null;
        }
    }

    /**
     * Metodo che restituisce il nome della playlist
     * @return Il metodo ritorna come stringa il nome della playlist
     */
    public String getNomePlaylist(){
        return this.nome;
    }

    /**
     * Metodo che restituisce il nome dell'autore della playlist
     * @return Il metodo ritorna il nome dell'autore della playlist di tipo Utente
     */
    public Utente getAutore(){
        return this.autore;
    }

    /**
     * Metodo per l'aggiunta di canzoni ad una playlist
     * @param brani ArrayList di canzoni da aggiungere alla playlist
     */
    public void addCanzoni(ArrayList<String> brani){
        this.canzoni = brani;
        this.lunghezza = brani.toArray().length;
    }

    public static void rimuoviDuplicati(ArrayList<String> lista) {
        HashSet<String> set = new HashSet<>(lista);
        lista.clear();
        lista.addAll(set);
    }

    public static ArrayList<String> rimuoviDuplicati(ArrayList<String> secondaLista, ArrayList<String> primaLista) {
        ArrayList<String> risultato = new ArrayList<>(secondaLista);

        risultato.removeAll(primaLista);

        return risultato;
    }
}