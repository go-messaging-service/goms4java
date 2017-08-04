package de.hauke_stieler.goms;

public class jgoms
{
	public static void main(String[] args)
	{
		System.out.println("connect");
		try (GoMessagingService service = new GoMessagingService("localhost", 55545))
		{
			Thread.sleep(100);
			
			System.out.println("send (register)");
			service.register("golang", "news");
			
			System.out.println("send (data)");
			service.send("Hallo123", "golang", "news");
			
			Thread.sleep(100);
			System.out.println("close");
		}
		catch (Exception e)
		{
			System.out.println("error");
			e.printStackTrace();
		}
	}
}
