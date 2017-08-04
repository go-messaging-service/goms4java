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
			System.out.println(1);
			service.connect();
			System.out.println(2);
			Thread.sleep(1000);
			service.send("{\"type\":\"register\",\"topics\":[\"golang\",\"news\"]}");
			System.out.println(3);
		}
		catch(Exception e)
		{
			System.out.println(4);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
