package de.hauke_stieler.goms.service;

import java.io.Closeable;
import java.io.IOException;
import java.net.UnknownHostException;

import de.hauke_stieler.goms.material.ErrorMessage;
import de.hauke_stieler.goms.material.Message;
import juard.event.DataEvent;

public abstract class ConnectionService implements Closeable
{
	DataEvent<Message>		MessageReceived	= new DataEvent<Message>();
	DataEvent<ErrorMessage>	ErrorReceived	= new DataEvent<ErrorMessage>();
	
	public abstract void connect() throws UnknownHostException, IOException;
	
	public abstract void send(String data) throws IOException;
}
