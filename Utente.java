package EmotionalSongs;/*
Cermisoni Marco, MATRICOLA 748739, VA
Oldani Marco, MATRICOLA 748243, VA
De Vito Francesco, MATRICOLA 749044, VA
Auteri Samuele, MATRICOLA 749710, VA
*/

import java.io.Serial;
import java.io.Serializable;

public class Utente implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String nome;
    private final String cognome;
    private final String codiceFiscale;
    private String data;
    private String email;
    private String id;
    private String password;

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public String getData() {
        return data;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public Utente(String nome, String cognome, String codiceFiscale, String data, String email, String id, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.data = data;
        this.email = email;
        this.id = id;
        this.password = password;
    }
}