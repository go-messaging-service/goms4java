package de.hauke_stieler.goms.service;

import java.io.Closeable;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import de.hauke_stieler.goms.material.Close;
import de.hauke_stieler.goms.material.Error;
import de.hauke_stieler.goms.material.ErrorMessage;
import de.hauke_stieler.goms.material.Logout;
import de.hauke_stieler.goms.material.Message;
import de.hauke_stieler.goms.material.Register;
import de.hauke_stieler.goms.material.Send;
import juard.contract.Contract;
import juard.event.DataEvent;
import juard.event.DataEventHandler;

public class GoMessagingService implements Closeable
{
	public final DataEvent<Error> ErrorReceived = new DataEvent<>();
	
	private Map<String, List<DataEventHandler<String>>>	messageHandlerList;
	private ConnectionService							service;
	
	public GoMessagingService(ConnectionService service) throws UnknownHostException, IOException
	{
		this.service = service;
		
		service.connect();
		service.MessageReceived.add(data -> handleReceivedMessage(data));
		service.ErrorReceived.add(data -> handleReceivedError(data));
		
		messageHandlerList = new HashMap<>();
	}
	
	private void handleReceivedMessage(Message data)
	{
		for (String topic : data.gettopics())
		{
			messageHandlerList.get(topic).forEach(handler -> handler.handleEvent(data.getdata()));
		}
	}
	
	private void handleReceivedError(ErrorMessage data)
	{
		Error error = new Error(data.geterrorcode(), data.geterror());
		
		ErrorReceived.fireEvent(error);
	}
	
	public void register(DataEventHandler<String> handler, String... topics) throws IOException
	{
		Contract.NotNull(topics);
		Contract.Satisfy(topics.length > 0);
		
		for (String topic : topics)
		{
			if (messageHandlerList.get(topic) == null)
			{
				messageHandlerList.put(topic, new ArrayList<>());
			}
			
			messageHandlerList.get(topic).add(handler);
		}
		
		// TODO create enum for the "register" until json2code supports enums
		Register register = new Register("register", Arrays.asList(topics));
		
		sendMessage(register);
	}
	
	public void send(String data, String... topics) throws IOException
	{
		Contract.NotNull(data);
		Contract.NotNull(topics);
		Contract.Satisfy(topics.length > 0);
		Contract.NotNull(service);
		
		// TODO create enum for the "send" until json2code supports enums
		Send send = new Send("send", Arrays.asList(topics), data);
		
		sendMessage(send);
	}
	
	public void logout(String... topics) throws IOException
	{
		Contract.NotNull(topics);
		Contract.Satisfy(topics.length > 0);
		Contract.NotNull(service);
		
		// TODO create enum for the "send" until json2code supports enums
		Logout logout = new Logout("logout", Arrays.asList(topics));
		
		sendMessage(logout);
	}
	
	@Override
	public void close() throws IOException
	{
		Contract.NotNull(service);
		
		// TODO create enum for the "close" until json2code supports enums
		sendMessage(new Close("close"));
		
		service.close();
	}
	
	private void sendMessage(Object messageObject) throws IOException
	{
		Contract.NotNull(messageObject);
		
		Gson gson = new Gson();
		String messageString = gson.toJson(messageObject);
		
		service.send(messageString);
	}
}
