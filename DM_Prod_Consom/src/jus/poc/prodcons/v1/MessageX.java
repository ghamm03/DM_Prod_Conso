package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;

public class MessageX implements Message{
	
	private MessageX m;
	
	public MessageX getM(){
		return m;
	}

	public void setM(MessageX m) {
		this.m = m;
	}

	public MessageX(MessageX message){m = message;}

	public String toString() {
		return "MessageX [m=" + m + "]";
	}
}
