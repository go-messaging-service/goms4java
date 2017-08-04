package de.hauke_stieler.goms;

import java.io.Closeable;
import java.io.IOException;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import de.hauke_stieler.goms.material.Register;
import de.hauke_stieler.goms.service.ConnectionService;
import de.hauke_stieler.goms.service.TcpConnectionService;
import juard.contract.Contract;

public class GoMessagingService implements Closeable
{
	private ConnectionService service;
	
	public GoMessagingService(String host, int port) throws UnknownHostException, IOException
	{
		service = new TcpConnectionService(host, port);
		service.connect();
	}
	
	public void register(String... topics) throws IOException
	{
		Register register = new Register(topics);
		
		Gson gson = new Gson();
		String data = gson.toJson(register);
		
		service.send(data);
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
