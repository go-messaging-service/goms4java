package de.hauke_stieler.goms;

import de.hauke_stieler.goms.service.ConnectionService;
import de.hauke_stieler.goms.service.TcpConnectionService;

public class jgoms
{
	public static void main(String[] args)
	{
		ConnectionService service = new TcpConnectionService("localhost", 55545);
		
		try
		{
			System.out.println("connect");
			service.connect();
			
			System.out.println("send (register)");
			service.send("{\"type\":\"register\",\"topics\":[\"golang\",\"news\"]}");
			
			System.out.println("send (data)");
			service.send("{\"type\":\"send\",\"topics\":[\"golang\",\"news\"],\"data\":\"Hallo123\"}");
			
			System.out.println("close");
			service.close();
		}
		catch (Exception e)
		{
			System.out.println("error");
			e.printStackTrace();
		}
	}
}
