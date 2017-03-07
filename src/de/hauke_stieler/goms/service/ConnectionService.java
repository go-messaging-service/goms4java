package de.hauke_stieler.goms.service;

public interface ConnectionService
{
	void connect();
	
	void addReceivedListener();
	
	void removeReceivedListener();
	
	void sendData(String data);
}
