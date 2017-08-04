package de.hauke_stieler.goms;

import java.io.Closeable;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import de.hauke_stieler.goms.material.Message;
import de.hauke_stieler.goms.material.Register;
import de.hauke_stieler.goms.material.Send;
import de.hauke_stieler.goms.service.ConnectionService;
import de.hauke_stieler.goms.service.TcpConnectionService;
import juard.contract.Contract;
import juard.event.DataEventHandler;

public class GoMessagingService implements Closeable
{
	private Map<String, List<DataEventHandler<String>>>	handlerList;
	private ConnectionService							service;
	
	public GoMessagingService(String host, int port) throws UnknownHostException, IOException
	{
		TcpConnectionService service = new TcpConnectionService(host, port);
		this.service = service;
		
		service.connect();
		service.MessageReceived.add(data -> handleReceivedMessage(data));
		
		handlerList = new HashMap<>();
	}
	
	private void handleReceivedMessage(Message data)
	{
		for (String topic : data.getTopics())
		{
			handlerList.get(topic).forEach(handler -> handler.handleEvent(data.getData()));
		}
	}
	
	public void register(DataEventHandler<String> handler, String... topics) throws IOException
	{
		Contract.NotNull(topics);
		Contract.Satisfy(topics.length > 0);
		
		for (String topic : topics)
		{
			if (handlerList.get(topic) == null)
			{
				handlerList.put(topic, new ArrayList<>());
			}
			
			handlerList.get(topic).add(handler);
		}
		
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
