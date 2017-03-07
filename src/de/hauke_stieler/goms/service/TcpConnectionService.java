package de.hauke_stieler.goms.service;

import juard.Contract;

public class TcpConnectionService implements ConnectionService
{
	
	private String host;
	private int port;

	public static ConnectionService create(String host, int port)
	{
		TcpConnectionService service = new TcpConnectionService(host, port);
		
		Contract.EnsureNotNull(service);
		return service;
	}
	
	private TcpConnectionService(String host, int port)
	{
		Contract.RequireNotNull(host);
		Contract.Require(!host.isEmpty());
		Contract.Require(port >= 0);
		Contract.Require(port <= 65535);
		
		this.host = host;
		this.port = port;
	}

	@Override
	public void connect()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addReceivedListener()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeReceivedListener()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendData(String data)
	{
		// TODO Auto-generated method stub
		
	}

}
