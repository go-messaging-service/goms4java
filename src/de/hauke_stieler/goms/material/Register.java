package de.hauke_stieler.goms.material;

import java.util.Arrays;
import java.util.List;

import juard.contract.Contract;

public class Register
{
	@SuppressWarnings ("unused")
	private final String type = "register";
	
	@SuppressWarnings ("unused")
	private List<String> topics;
	
	public Register(String... topics)
	{
		Contract.NotNull(topics);
		
		this.topics = Arrays.asList(topics);
	}
}
