package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;
//todo généraliser String
public class MessageX implements Message{
	
	private String m;
	
	public MessageX(){this.m = "";}
	
	public String getM(){
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	
	
	public String toString() {
		return "MessageX [m=" + m + "]";
	}
}
