package client;

import server.Server_Interface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends UnicastRemoteObject implements Client_Interface,
		Runnable {
	private static final long serialVersionUID = 1L;
	private Server_Interface server;
	public static String name = null;
	public static int age = 0;
	public static double locationX = 0.0;
	public static double locationY = 0.0;
	public static int uniqueID;
	
	public static LinkedList<Client_Interface> clientList = new LinkedList<Client_Interface>();

	protected Client(String[] args, Server_Interface server)
			throws RemoteException {
		this.server = server;
		this.name = args[1];
		this.locationX = Double.parseDouble(args[2]);
		this.locationY = Double.parseDouble(args[3]);
		this.age = Integer.parseInt(args[4]);
		server.registerClient(this);
		System.out.println();
		System.out.println("--------------------");
		System.out.println("*Welcome "+this.name+"*");
		System.out.println("--------------------");
	}

	public void retrieveMsg(String sender_name, String msg)
			throws RemoteException {		
			System.out.println(sender_name + " : " + msg);		
	}
	
	public void retrieveList(Client_Interface clientInfo) throws RemoteException{
		clientList.add(clientInfo);		
	}
	
	public void retrieveErrorMsg(String name, String msg) throws RemoteException{
		System.out.println(name+"(error): " + msg);
		System.exit(1);
	}
	
	public void clearList() throws RemoteException{
		clientList.clear();
	}
	public void printList() throws RemoteException{
		if(clientList.size() != 0){
		for(int i=0;i<clientList.size();i++){
			System.out.println("---------------------");
			System.out.println("Client "+ (i+1));
			System.out.println("Unique ID : " + clientList.get(i).getUniqueID());
			System.out.println("Name : " + clientList.get(i).getName());
			System.out.println("Age : " + clientList.get(i).getAge());
			System.out.println("Location : " + clientList.get(i).getLocationX() + " " + clientList.get(i).getLocationY());
			System.out.println("---------------------");
		}
		}else System.err.println("No client found in the range given.");
	}

	public String getName() throws RemoteException {
		return this.name;
	}

	public double getLocationX() throws RemoteException {
		return this.locationX;
	}
	public void setLocationX(double x) throws RemoteException{
		this.locationX = x;
	}

	public double getLocationY() throws RemoteException {
		return this.locationY;
	}
	public void setLocationY(double y) throws RemoteException{
		this.locationY = y;
	}

	public int getAge() {
		return this.age;
	}

	public int getUniqueID() throws RemoteException {
		return this.uniqueID;
	}

	public void setUniqueID(int id) throws RemoteException {
		this.uniqueID = id;
	}
	
	

	public void run() {
		Scanner scanner = new Scanner(System.in);
		String msg;		
		boolean chat = true;

		while (chat) {
			msg = scanner.nextLine();
			try {
				server.broadcastMsg(uniqueID, msg);
				if (msg.equalsIgnoreCase("quit")) {
					System.exit(1);
					chat = false;
				}
			} catch (RemoteException ex) {
				ex.printStackTrace();
			}
		}
	}

}
