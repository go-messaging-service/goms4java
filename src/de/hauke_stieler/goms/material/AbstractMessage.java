package de.hauke_stieler.goms.material;

import java.util.List;

public class AbstractMessage 
{
	private String messagetype;

	public AbstractMessage(){}

	public AbstractMessage(String messagetype)
	{
		this.messagetype = messagetype;
	}

	public String getmessagetype()
	{
		return messagetype;
	}
}


