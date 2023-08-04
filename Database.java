package database;//prova

import java.io.Serializable;
import java.sql.*;

public class Database implements Serializable{
    private static final long serialVersionUID = 1L;
    /*
    Variabli login
     */
    private final String protocol = "jdbc:postgresql://";
    private final String host = "localhost/";
    private final String db_name = "EmotionalSongs";
    private final String url = protocol + host + db_name;

    private final String user = "postgres";
    private final String password = "admin";

    /*
    Variabili connessione DB
     */
    private static Database database;
    private static Connection connection;
    private static Statement statement;

    public Database() throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    /*
    Metodo statico per Pattern Singleton
     */
    public static Database getInstance() throws SQLException {
        if (database == null)
            database = new Database();
        return database;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static Connection getConnection(){
        return connection;
    }
}