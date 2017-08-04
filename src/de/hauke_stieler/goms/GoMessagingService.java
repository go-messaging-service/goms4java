package de.hauke_stieler.goms;

import java.io.Closeable;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import com.google.gson.Gson;

import de.hauke_stieler.goms.material.Register;
import de.hauke_stieler.goms.material.Send;
import de.hauke_stieler.goms.service.ConnectionService;
import de.hauke_stieler.goms.service.TcpConnectionService;
import juard.contract.Contract;
import juard.event.DataEvent;

public class GoMessagingService implements Closeable
{
	public DataEvent<String> MessageReceived = new DataEvent<String>();
	
	private ConnectionService service;
	
	public GoMessagingService(String host, int port) throws UnknownHostException, IOException
	{
		TcpConnectionService service = new TcpConnectionService(host, port);
		
		service.connect();
		service.MessageReceived.add(data -> MessageReceived.fireEvent(data));
		
		this.service = service;
	}
	
	public void register(String... topics) throws IOException
	{
		Contract.NotNull(topics);
		Contract.Satisfy(topics.length > 0);
		
		Register register = new Register(topics);
		
		sendMessage(register);
	}
	
	public void send(String data, String... topics) throws IOException
	{
		Contract.NotNull(data);
		Contract.NotNull(topics);
		Contract.Satisfy(topics.length > 0);
		Contract.NotNull(service);
		
		Send send = new Send(data, topics);
		
		sendMessage(send);
	}
	
	public void send(String data, List<String> topics) throws IOException
	{
		Contract.NotNull(data);
		Contract.NotNull(topics);
		Contract.Satisfy(topics.size() > 0);
		Contract.NotNull(service);
		
		Send send = new Send(data, topics);
		
		sendMessage(send);
	}
	
	private void sendMessage(Object messageObject) throws IOException
	{
		Contract.NotNull(messageObject);
		
		Gson gson = new Gson();
		String messageString = gson.toJson(messageObject);
		
		service.send(messageString);
	}
	
	@Override
	public void close() throws IOException
	{
		Contract.NotNull(service);
		
		service.close();
	}
}
