package de.hauke_stieler.goms.material;

import java.util.Arrays;
import java.util.List;

import juard.contract.Contract;

public class Send
{
	private static final String type = "send";
	
	private List<String>	topics;
	private String			data;
	
	public Send(String data, String... topics)
	{
		Contract.NotNull(topics);
		Contract.Satisfy(topics.length > 0);
		Contract.NotNull(data);
		
		this.topics = Arrays.asList(topics);
		this.data = data;
	}
	
	public Send(String data, List<String> topics)
	{
		Contract.NotNull(topics);
		Contract.Satisfy(topics.size() > 0);
		Contract.NotNull(data);
		
		this.topics = topics;
		this.data = data;
	}
}
