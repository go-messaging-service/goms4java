package de.hauke_stieler.goms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class GoMessagingService
{
	private String host;
	private int port;
	private Socket socket;

	public GoMessagingService(String host, int port)
	{
		this.host = host;
		this.port = port;

		socket = null;
	}

	public void connect() throws UnknownHostException, IOException
	{
		socket = new Socket(host, port);

		startListening();
	}

	public void startListening()
	{
		Thread thread = new Thread(() -> {
			try
			{
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				String line;
				line = reader.readLine();

				while(!line.isEmpty())
				{
					System.out.println(line);

					line = reader.readLine();
				}
			}
			catch(Exception e)
			{
			}
		});
		thread.start();
	}

	public void send(String data) throws IOException
	{
		if(!data.endsWith("\n"))
		{
			data = data + "\n";
		}

		socket.getOutputStream().write(data.getBytes());
	}

	public void close() throws IOException
	{
		socket.close();
	}
}
