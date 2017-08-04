package de.hauke_stieler.goms;

import java.io.Closeable;
import java.io.IOException;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import de.hauke_stieler.goms.material.Register;
import de.hauke_stieler.goms.material.Send;
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
		String registerString = gson.toJson(register);
		
		service.send(registerString);
	}
	
	public void send(String data, String... topics) throws IOException
	{
		Contract.NotNull(data);
		Contract.NotNull(topics);
		Contract.Satisfy(topics.length > 0);
		Contract.NotNull(service);
		
		Send send = new Send(data, topics);
		
		Gson gson = new Gson();
		String sendString = gson.toJson(send);
		
		service.send(sendString);
	}
	
	@Override
	public void close() throws IOException
	{
		Contract.NotNull(service);
		
		service.close();
	}
}
