package server;

import client.Client;
import client.Client_Interface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements Server_Interface {

	private static final long serialVersionUID = 1L;
	private ArrayList<Client_Interface> clients;
	public int uid = 1;

	protected Server() throws RemoteException {
		clients = new ArrayList<Client_Interface>();
		System.out.println();
		System.out.println("Server is Created Sucessfully.");
	}

	public synchronized void registerClient(Client_Interface client)
			throws RemoteException {
		
		boolean check_available = true;
		//System.out.println("CHECK Username: " + client.getName());
		for(int i=0;i<clients.size();i++){
			//System.out.println("CHECK Username: " + clients.get(i).getName() +" vs. "+client.getName());
			if(clients.get(i).getName().equalsIgnoreCase(client.getName())){
				//System.out.println("Error: Same Username Found.");
				check_available = false;
				client.retrieveErrorMsg("Server", "Duplicate username found. Please try again with a different username.");
				break;				
			}
		}
		
		if(check_available == true){
			client.setUniqueID(uid);
			this.clients.add(client);
			System.out.println();
			System.out.println("*Registered*");
			System.out.println("Unique ID : " + client.getUniqueID());
			System.out.println("Name : " + client.getName());
			System.out.println("Age : " + client.getAge());
			System.out.println("Location : " + client.getLocationX() + " "
					+ client.getLocationY());
			uid++;
		}
	}

	public synchronized void broadcastMsgIndi(String name, int sendTo,
			String msg) throws RemoteException {
		/*
		 * int i = 0; while (i < clients.size()) {
		 * clients.get(i).retrieveMsg(name, collection.get(2)); i = i + 1; } }
		 */
	}

	public synchronized void broadcastMsg(int id, String msg)
			throws RemoteException { //id = msg sender's id

		ArrayList<String> collection = new ArrayList<String>();
		for (String retval : msg.split(" ")) {
			collection.add(retval);
		}
		
		

		if (msg.equalsIgnoreCase("quit")
				|| collection.get(0).equalsIgnoreCase("quit")) {
			for (int i = 0; i < clients.size(); i++) {
				if (clients.get(i).getUniqueID() == id) {
					System.out.println("Client " + clients.get(i).getName()
							+ " has left.");
					clients.remove(i);
				}
			}
		} else if (collection.get(0).equalsIgnoreCase("send")) {
			
			String sendMsg = "";
			for (int i = 2; i < collection.size(); i++) {
				sendMsg = sendMsg + collection.get(i) + " ";
			}
			
			String sender_name = "$";
			Client c;
			for(int i=0;i<clients.size();i++){
				if(clients.get(i).getUniqueID() == id){
					sender_name = clients.get(i).getName();					
				}
			}
			boolean check = true;
			for (int i = 0; i < clients.size(); i++) {
				if (clients.get(i).getUniqueID() == Integer.parseInt(collection.get(1)) && (clients.get(i).getUniqueID() != id)) {					
					clients.get(i).retrieveMsg(sender_name,sendMsg); 					
				}else check = false;
			}
			
		} else if(collection.get(0).equalsIgnoreCase("get")){
			String location = "";
			for(int i=0;i<clients.size();i++){
				if(clients.get(i).getUniqueID() == id){
					location = location + clients.get(i).getLocationX() + " " + clients.get(i).getLocationY();
					clients.get(i).retrieveMsg("Own Location", location);
				}
			}			
		}else if(collection.get(0).equalsIgnoreCase("go")){
			double locationX = 0;
			double locationY = 0;
			for(int i=0;i<clients.size();i++){
				if(clients.get(i).getUniqueID() == id){
					locationX = Double.parseDouble(collection.get(1)) + clients.get(i).getLocationX();
					locationY = Double.parseDouble(collection.get(2)) + clients.get(i).getLocationY();
					clients.get(i).setLocationX(locationX);
					clients.get(i).setLocationY(locationY);
					String location = String.valueOf(locationX) + " " + String.valueOf(locationY);
					clients.get(i).retrieveMsg("New Location", location);
				}	
			}
		}else if(collection.get(0).equalsIgnoreCase("list")){
			double range = Double.parseDouble(collection.get(1));
			int index = -1;
			
			double x=0;
			double y=0;
			for(int i=0;i<clients.size();i++){
				if(id == clients.get(i).getUniqueID()){
					index = i;
					x = clients.get(i).getLocationX();
					y = clients.get(i).getLocationY();
				}
			}
			if(index == -1){
				System.err.println("No such Client Found");
				System.exit(1);
			}
			
			clients.get(index).clearList();
			for(int i=0;i<clients.size();i++){
				if(clients.get(i).getUniqueID() == id){
					continue;
				}
				double a = clients.get(i).getLocationX();
				double b = clients.get(i).getLocationY();
				double distance_sq = (x-a)*(x-a) + (y-b)*(y-b); 
				if(distance_sq<=range*range){
					clients.get(index).retrieveList(clients.get(i));
				}
			}
			clients.get(index).printList();
		}
	}
}
