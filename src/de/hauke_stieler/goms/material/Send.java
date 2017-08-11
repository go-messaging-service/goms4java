package de.hauke_stieler.goms.material;

import java.util.List;

public class Send 
{
	private String messagetype;
	private List<String> topics;
	private String data;

	public Send(){}

	public Send(String messagetype, List<String> topics, String data)
	{
		this.messagetype = messagetype;
		this.topics = topics;
		this.data = data;
	}

	public String getmessagetype()
	{
		return messagetype;
	}

	public List<String> gettopics()
	{
		return topics;
	}

	public String getdata()
	{
		return data;
	}
}


