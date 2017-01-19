package com.neodoo.docker.test.service;

import javax.enterprise.context.RequestScoped;

/**
 *
 * @author manuel.aznar@neodoo.es
 */
@RequestScoped
public class GreetingService {

	public String greet(String who) {
		return "Hello, " + who + "!";
	}
}