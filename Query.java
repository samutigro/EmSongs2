package Database;/*
Cermisoni Marco, MATRICOLA 748739, VA
Oldani Marco, MATRICOLA 748243, VA
De Vito Francesco, MATRICOLA 749044, VA
Auteri Samuele, MATRICOLA 749710, VA
*/

import java.io.Serial;
import java.io.Serializable;

/**
 * Classe che definisce la query
 * @author
 * @author
 */
public class Query implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String query;

    /**
     * Costruttore della classe Database.Query avente un solo parametro
     * @param query Stringa contenente la query
     */
    public Query(String query){
        this.query = query;
    }

    /**
     * Metodo che restituisce la query
     * @return Il metodo ritorna come stringa la query
     */
    public String getQuery() {
        return query;
    }
}