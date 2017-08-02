package de.hauke_stieler.goms.service;

import java.io.IOException;
import java.net.UnknownHostException;

public interface ConnectionService
{
	void connect() throws UnknownHostException, IOException;
	
	void addReceivedListener();
	
	void removeReceivedListener();
	
	void sendData(String data);
}
