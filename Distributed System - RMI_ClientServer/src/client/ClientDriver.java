package client;

import server.Server_Interface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientDriver {
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException{
        String host = args[0];
    	String ServerURL = "rmi://"+host+"/RMIServer";
        Server_Interface server = (Server_Interface) Naming.lookup(ServerURL);
        new Thread(new Client(args, server)).start();
    }
}