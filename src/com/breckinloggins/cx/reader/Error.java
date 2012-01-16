package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;
import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;
import com.breckinloggins.cx.dictionary.IReader;

public class Error extends BaseReader {

	protected String _message;
	
	public Error()	{
		_message = "";
	}
	
	public void setMessage(String message)	{
		_message = message;
	}
	
	@Override
	public String getDescription()	{
		return "Reads no characters and emits an error message";
	}
	
	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		int ch = sr.read();
		if (ch != -1)	{
			getWriter().println(_message + ": EOF");
		} else {
			getWriter().println(_message + Character.toString((char)ch));
		}
		
		return env.createReader("terminator");
	}

}
