package de.hauke_stieler.goms;

import de.hauke_stieler.goms.material.Error;
import de.hauke_stieler.goms.service.TcpConnectionService;

public class jgoms
{
	public static void main(String[] args)
	{
		System.out.println("connect");
		try (GoMessagingService service = new GoMessagingService(new TcpConnectionService("localhost", 55545)))
		{
			service.ErrorReceived.add(data -> error(data));
			
			Thread.sleep(100);
			
			System.out.println("register");
			service.register(data -> print(data), "golang", "news", "test");
			service.register(data -> print(data), "news");
			
			System.out.println("send");
			service.send("Test1", "golang", "news");
			service.send("Test2", "golang");
			service.send("Test3", "news");
			
			Thread.sleep(1000);
			System.out.println("close");
		}
		catch (Exception e)
		{
			System.out.println("error");
			e.printStackTrace();
		}
	}
	
	private static void error(Error error)
	{
		System.out.println("error: " + error.getErrorCode() + ": " + error.getErrorMessage());
	}
	
	private static void print(String data)
	{
		System.out.println("received: " + data);
	}
}
