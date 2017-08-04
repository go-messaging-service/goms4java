package de.hauke_stieler.goms.material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import juard.contract.Contract;

public class Message
{
	private String			type;
	private List<String>	topics;
	private String			data;
	
	public Message(String data, String... topics)
	{
		Contract.NotNull(data);
		Contract.NotNull(topics);
		Contract.Satisfy(topics.length > 0);
		
		this.data = data;
		this.topics = Arrays.asList(topics);
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getData()
	{
		return data;
	}
	
	public List<String> getTopics()
	{
		// calling the constructor will copy the entire list
		return new ArrayList<>(topics);
	}
}
