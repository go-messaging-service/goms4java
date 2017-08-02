package de.hauke_stieler.goms.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import juard.Contract;

public class TcpConnectionService implements ConnectionService
{
	private String	host;
	private int		port;
	private Socket	socket;
	
	public static ConnectionService create(String host, int port)
	{
		TcpConnectionService service = new TcpConnectionService(host, port);
		
		Contract.EnsureNotNull(service);
		return service;
	}
	
	private TcpConnectionService(String host, int port)
	{
		Contract.RequireNotNull(host);
		Contract.Require(!host.isEmpty());
		Contract.Require(port >= 0);
		Contract.Require(port <= 65535);
		
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
	public void addReceivedListener()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void removeReceivedListener()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void sendData(String data)
	{
		// TODO Auto-generated method stub
		
	}
	
}
