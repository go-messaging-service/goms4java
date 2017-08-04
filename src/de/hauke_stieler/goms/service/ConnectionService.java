package de.hauke_stieler.goms.service;

import java.io.Closeable;
import java.io.IOException;
import java.net.UnknownHostException;

public interface ConnectionService extends Closeable
{
	void connect() throws UnknownHostException, IOException;
	
	void send(String data) throws IOException;
}
