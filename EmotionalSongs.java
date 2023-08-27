package EmotionalSongs;

import Database.serverES;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class EmotionalSongs {

    public static void main(String[] args) throws SQLException, RemoteException, NotBoundException {
        serverES serverImpl = new serverES();
        Registry registry = LocateRegistry.createRegistry(8999);
        registry.rebind("SERVER", serverImpl);

        clientEs emsong = new clientEs();

    }
}
