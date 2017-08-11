package de.hauke_stieler.goms.material;

import java.util.List;

public class ErrorMessage 
{
	private String messagetype;
	private String errorcode;
	private String error;

	public ErrorMessage(){}

	public ErrorMessage(String messagetype, String errorcode, String error)
	{
		this.messagetype = messagetype;
		this.errorcode = errorcode;
		this.error = error;
	}

	public String getmessagetype()
	{
		return messagetype;
	}

	public String geterrorcode()
	{
		return errorcode;
	}

	public String geterror()
	{
		return error;
	}
}


