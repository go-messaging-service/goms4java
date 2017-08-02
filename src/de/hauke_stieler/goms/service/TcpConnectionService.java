package de.hauke_stieler.goms.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import juard.contract.Contract;

public class TcpConnectionService implements ConnectionService
{
	private String	host;
	private int		port;
	private Socket	socket;
	
	public static ConnectionService create(String host, int port)
	{
		TcpConnectionService service = new TcpConnectionService(host, port);
		
		Contract.NotNull(service);
		return service;
	}
	
	private TcpConnectionService(String host, int port)
	{
		Contract.NotNull(host);
		Contract.Satisfy(!host.isEmpty());
		Contract.Satisfy(port >= 0);
		Contract.Satisfy(port <= 65535);
		
		this.host = host;
		this.port = port;
		
		socket = null;
	}
	
	@Override
	public void connect() throws UnknownHostException, IOException
	{
		socket = new Socket(host, port);
		
		startListening();
	}
	
	public void startListening()
	{
		Thread thread = new Thread(() ->
		{
			try
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				String line;
				line = reader.readLine();
				
				while (!line.isEmpty())
				{
					// TODO fire event
					System.out.println(line);
					
					line = reader.readLine();
				}
			}
			catch (Exception e)
			{
			}
		});
		thread.start();
	}
	
	@Override
	public void sendData(String data)
	{
		// TODO Auto-generated method stub
		
	}
	
}
