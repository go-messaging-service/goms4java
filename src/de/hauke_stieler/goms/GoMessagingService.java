package de.hauke_stieler.goms;

import java.io.Closeable;
import java.io.IOException;

import de.hauke_stieler.goms.service.ConnectionService;
import de.hauke_stieler.goms.service.TcpConnectionService;
import juard.contract.Contract;

public class GoMessagingService implements Closeable
{
	private ConnectionService service;
	
	public GoMessagingService(String host, int port)
	{
		service = new TcpConnectionService(host, port);
	}
	
	public void send(String data) throws IOException
	{
		Contract.NotNull(data);
		Contract.NotNull(service);
	}
	
	@Override
	public void close() throws IOException
	{
		Contract.NotNull(service);
		
		service.close();
	}
}
