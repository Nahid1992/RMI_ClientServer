package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client_Interface extends Remote{	
    void retrieveMsg(String name, String msg) throws RemoteException;
    void retrieveErrorMsg(String name, String msg) throws RemoteException;
    void retrieveList(Client_Interface clientInfo) throws RemoteException;
    void printList() throws RemoteException;
    void clearList() throws RemoteException;
    String getName() throws RemoteException;
    double getLocationX() throws RemoteException;
    void setLocationX(double x) throws RemoteException;
    void setLocationY(double y) throws RemoteException;
    double getLocationY() throws RemoteException;
    int getAge() throws RemoteException;
    int getUniqueID() throws RemoteException;
    void setUniqueID(int id) throws RemoteException;
}