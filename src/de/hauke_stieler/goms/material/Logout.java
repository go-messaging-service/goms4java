package de.hauke_stieler.goms.material;

import java.util.List;

public class Logout 
{
	private String messagetype;
	private List<String> topics;

	public Logout(){}

	public Logout(String messagetype, List<String> topics)
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


