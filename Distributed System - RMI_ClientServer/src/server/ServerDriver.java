package server;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerDriver {	
    public static void main(String [] args) throws RemoteException, MalformedURLException{
    	Registry registry = LocateRegistry.getRegistry(1099); //to run in different port, update this field.
    	//Naming.rebind("RMIServer",new Server()); //for default
        registry.rebind("RMIServer",new Server());
    }
}