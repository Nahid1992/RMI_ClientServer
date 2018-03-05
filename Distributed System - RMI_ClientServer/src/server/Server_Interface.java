package server;

import client.Client;
import client.Client_Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server_Interface extends Remote{
    void registerClient(Client_Interface client) throws RemoteException;
    void broadcastMsg(int id, String msg) throws RemoteException;    
    void broadcastMsgIndi(String name, int sendTo, String msg) throws RemoteException;
}