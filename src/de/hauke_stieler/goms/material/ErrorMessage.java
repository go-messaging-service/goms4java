package de.hauke_stieler.goms.material;

import com.google.gson.annotations.SerializedName;

public class ErrorMessage extends AbstractMessage
{
	@SerializedName ("error-code")
	private String	errorCode;
	private String	error;
	
	public String getErrorCode()
	{
		return errorCode;
	}
	
	public String getError()
	{
		return error;
	}
}
