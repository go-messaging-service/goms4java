package de.hauke_stieler.goms.material;

import juard.contract.Contract;

public class Error
{
	private final String	errorCode;
	private final String	errorMessage;
	
	public Error(String errorCode, String errorMessage)
	{
		Contract.NotNullOrEmpty(errorCode);
		Contract.NotNullOrEmpty(errorMessage);
		
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public String getErrorCode()
	{
		return errorCode;
	}
	
	public String getErrorMessage()
	{
		return errorMessage;
	}
}
