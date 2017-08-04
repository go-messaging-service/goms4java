package de.hauke_stieler.goms;

import java.io.Closeable;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import de.hauke_stieler.goms.material.ErrorMessage;
import de.hauke_stieler.goms.material.Message;
import de.hauke_stieler.goms.material.Register;
import de.hauke_stieler.goms.material.Send;
import de.hauke_stieler.goms.service.ConnectionService;
import de.hauke_stieler.goms.service.TcpConnectionService;
import juard.contract.Contract;
import juard.event.DataEvent;
import juard.event.DataEventHandler;

public class GoMessagingService implements Closeable
{
	public final DataEvent<String> ErrorReceived = new DataEvent<>();
	
	private Map<String, List<DataEventHandler<String>>>	messageHandlerList;
	private ConnectionService							service;
	
	public GoMessagingService(String host, int port) throws UnknownHostException, IOException
	{
		TcpConnectionService service = new TcpConnectionService(host, port);
		this.service = service;
		
		service.connect();
		service.MessageReceived.add(data -> handleReceivedMessage(data));
		service.ErrorReceived.add(data -> handleReceivedError(data));
		
		messageHandlerList = new HashMap<>();
	}
	
	private void handleReceivedMessage(Message data)
	{
		for (String topic : data.getTopics())
		{
			messageHandlerList.get(topic).forEach(handler -> handler.handleEvent(data.getData()));
		}
	}
	
	private void handleReceivedError(ErrorMessage data)
	{
		ErrorReceived.fireEvent(data.getError());
	}
	
	public void register(DataEventHandler<String> handler, String... topics) throws IOException
	{
		Contract.NotNull(topics);
		Contract.Satisfy(topics.length > 0);
		
		for (String topic : topics)
		{
			registerMessageHandler(handler, topic);
		}
		
		Register register = new Register(topics);
		
		sendMessage(register);
	}
	
	private void registerMessageHandler(DataEventHandler<String> handler, String topic)
	{
		Contract.NotNull(handler);
		Contract.NotNullOrEmpty(topic);
		
		if (messageHandlerList.get(topic) == null)
		{
			messageHandlerList.put(topic, new ArrayList<>());
		}
		
		messageHandlerList.get(topic).add(handler);
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
