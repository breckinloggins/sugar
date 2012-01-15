package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;
import com.breckinloggins.cx.Environment;

public class Name implements IReader {

	private String _name;
	
	public String getName()	{
		return _name;
	}
	
	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		sr.mark(0);
		int c = sr.read();
		if (c == -1)	{
			sr.reset();
			Error e = new Error();
			e.setMessage("Unexpected");
			return e;
		}
		
		char ch = (char)c;
		if (!Character.isLetter(ch) && ch != '_')	{
			sr.reset();
			Error e = new Error();
			e.setMessage("Unexpected Character");
			return e;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(ch);
		while (true)	{
			sr.mark(0);
			c = sr.read();
			if (c == -1)	{
				// It's up to upper level code to determine whether it's ok to 
				// have an EOF directly after a name
				System.out.println("r(Name): " + sb.toString());
				sr.reset();
				_name = sb.toString();
				return new Terminator();
			}
			
			ch = (char)c;
			if (Character.isLetterOrDigit(ch) || ch == '_')	{
				sb.append(ch);
			}
			else
			{
				sr.reset();
				break;
			}
		}
		
		// Optionally eat up some whitespace
		// TODO: needs to be set by environment or other way to turn this off and on
		new Whitespace().read(sr, env);
		
		System.out.println("r(Name): " + sb.toString());
		_name = sb.toString();
		
		return new Discriminator();
	}

}