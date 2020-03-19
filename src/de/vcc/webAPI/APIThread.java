package de.vcc.webAPI;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import commands.MediaKeys;

public class APIThread extends Thread{

	private Socket socket;
	private boolean keepAlive = true;
	
	@Override
	public void run() {
		
		VolumeControlCenter.clientCount++;
		VolumeControlCenter.updateClientCount();
		
		while(keepAlive) {
			waitForInput();
		}
	}
	
	public APIThread(Socket socket) {
		this.socket = socket;
	}
	
	private void waitForInput() {
		try {
			InputStream in = socket.getInputStream();
			
			int volumeControl = in.read();
			if (volumeControl == 0) {
				MediaKeys.volumeDown();
			} else if (volumeControl == 1){
				MediaKeys.volumeUp();
			} else if (volumeControl == 2){
				keepAlive = false;
				VolumeControlCenter.clientCount--;
				VolumeControlCenter.updateClientCount();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}