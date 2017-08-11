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

	public void setmessagetype(String messagetype)
	{
		this.messagetype = messagetype;
	}

	public List<String> gettopics()
	{
		return topics;
	}

	public void settopics(List<String> topics)
	{
		this.topics = topics;
	}

	public String getdata()
	{
		return data;
	}

	public void setdata(String data)
	{
		this.data = data;
	}
}


