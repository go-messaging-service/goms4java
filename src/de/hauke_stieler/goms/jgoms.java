package de.hauke_stieler.goms;

public class jgoms
{
	public static void main(String[] args)
	{
		GoMessagingService service = new GoMessagingService("localhost", 55545);

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
