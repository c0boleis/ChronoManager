package fr.chrono.controlers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import fr.chrono.model.interfaces.DomainType;

public class LifeControler {
	
	public static void main(String[] args) {
		int portTest = 6785;
		sendLifeStatus(portTest,DomainType.START.name());
		AuxiliaireControler.listen(portTest);
	}
	
	public static void sendLifeStatus(final int port,String id) {
		try {
			final DatagramSocket datagramSocket = new DatagramSocket();
			InetAddress serveur = null;
			try {
				serveur = InetAddress.getByName("127.0.0.1");
			} catch (UnknownHostException e2) {
				e2.printStackTrace();
			}
			String text = id+"_LIFE";
			byte[] b = text.getBytes(StandardCharsets.UTF_8);
			System.out.println(b.length);
			final DatagramPacket packet = new DatagramPacket(b, b.length,serveur,port);
			Thread threadLife = new Thread(new Runnable() {
				
				@Override
				public void run() {
					int index = 60;
					while(index>0) {
						try {
							datagramSocket.send(packet);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						try {
							Thread.sleep(1000);
							Thread.yield();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						index--;
					}
					datagramSocket.close();
				}
			});
			threadLife.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

}
