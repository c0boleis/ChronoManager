package fr.chrono.controlers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import fr.chrono.model.interfaces.DomainType;

public class AuxiliaireControler {
	
	public static void main(String[] args) {
		int portTest = 6785;
		listen(portTest);
	}
	
	public static void listen(final int port) {
		final byte[] buffer = new byte[16];
		Thread threadListener = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Lancement du serveur");
				DatagramSocket socket = null;
				try {
					socket = new DatagramSocket(port);
				} catch (SocketException e) {
					e.printStackTrace();
				}
				int index = 60;
				while(index>0) {
					DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);
					try {
						socket.receive(paquet);
						System.out.println("\n"+paquet.getAddress());
						int taille = paquet.getLength();
						String donnees = new String(paquet.getData(),0, taille);
						System.out.println("Donnees reçues = \""+donnees+"\"");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//					try {
//						Thread.sleep(1000);
//						Thread.yield();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
					index--;
				}
				System.out.println("Arret du serveur");
			}
		});
		threadListener.start();
	}

}
