package de.hauke_stieler.goms;

public class jgoms
{
	public static void main(String[] args)
	{
		System.out.println("connect");
		try (GoMessagingService service = new GoMessagingService("localhost", 55545))
		{
			service.ErrorReceived.add(data -> error(data));
			
			Thread.sleep(100);
			
			System.out.println("register");
			service.register(data -> print(data), "golang", "news", "test");
			service.register(data -> print(data), "news");
			
			System.out.println("send");
			service.send("Hallo\n123", "golang", "news");
			
			Thread.sleep(1000);
			System.out.println("close");
		}
		catch (Exception e)
		{
			System.out.println("error");
			e.printStackTrace();
		}
	}
	
	private static void error(String data)
	{
		System.out.println("error: " + data);
	}
	
	private static void print(String data)
	{
		System.out.println("received: " + data);
	}
}
