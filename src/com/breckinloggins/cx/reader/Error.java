package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;
import com.breckinloggins.cx.Environment;

public class Error implements IReader {

	protected String _message;
	
	public Error()	{
		_message = "";
	}
	
	public void setMessage(String message)	{
		_message = message;
	}
	
	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		int ch = sr.read();
		if (ch != -1)	{
			System.out.println(_message + ": EOF");
		} else {
			System.out.println(_message + Character.toString((char)ch));
		}
		
		return new Terminator();
	}

}