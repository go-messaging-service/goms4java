package de.hauke_stieler.goms.material;

import java.util.List;

public class Register 
{
	private String messagetype;
	private List<String> topics;

	public Register(){}

	public Register(String messagetype, List<String> topics)
	{
		this.messagetype = messagetype;
		this.topics = topics;
	}

	public String getmessagetype()
	{
		return messagetype;
	}

	public List<String> gettopics()
	{
		return topics;
	}
}


