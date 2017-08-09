package de.hauke_stieler.goms.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import de.hauke_stieler.goms.material.AbstractMessage;
import de.hauke_stieler.goms.material.ErrorMessage;
import de.hauke_stieler.goms.material.Message;
import juard.contract.Contract;
import juard.event.DataEvent;

public class TcpConnectionService implements ConnectionService
{
	public DataEvent<Message>		MessageReceived	= new DataEvent<Message>();
	public DataEvent<ErrorMessage>	ErrorReceived	= new DataEvent<ErrorMessage>();
	
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
				String line = reader.readLine();
				Gson gson = new Gson();
				
				System.out.println("listen");
				while (line != null && !line.isEmpty() && !socket.isClosed())
				{
					AbstractMessage message = gson.fromJson(line, AbstractMessage.class);
					
					if (message.getType().equals("error"))
					{
						ErrorReceived.fireEvent(gson.fromJson(line, ErrorMessage.class));
					}
					else
					{
						MessageReceived.fireEvent(gson.fromJson(line, Message.class));
					}
					
					line = reader.readLine();
				}
			}
			catch (Exception e)
			{
				System.out.println("suddenly disconnected");
				e.printStackTrace();
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
		socket.close();
	}
}
