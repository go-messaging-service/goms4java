package de.hauke_stieler.goms.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import juard.contract.Contract;
import juard.event.DataEvent;

public class TcpConnectionService implements ConnectionService
{
	public DataEvent<String> MessageReceived = new DataEvent<String>();
	
	private String	host;
	private int		port;
	private Socket	socket;
	
	public TcpConnectionService(String host, int port)
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
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())))
			{
				String line;
				line = reader.readLine();
				
				System.out.println("listen");
				while (!line.isEmpty() && !socket.isClosed())
				{
					MessageReceived.fireEvent(line);
					
					line = reader.readLine();
				}
			}
			catch (Exception e)
			{
				System.out.println("suddenly disconnected");
			}
		});
		thread.start();
	}
	
	@Override
	public void send(String data) throws IOException
	{
		Contract.NotNull(data);
		Contract.NotNull(socket);
		
		if (!data.endsWith("\n"))
		{
			data = data + "\n";
		}
		
		socket.getOutputStream().write(data.getBytes());
	}
	
	@Override
	public void close() throws IOException
	{
		socket.getInputStream().close();
		socket.close();
	}
}
