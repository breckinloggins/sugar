package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;
import com.breckinloggins.cx.Environment;

public class Reader implements IReader {

	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		int ch = sr.read();
		if (ch == -1)	{
			Error e = new Error();
			e.setMessage("Unexpected");
			return e;
		}
		
		Name nameReader = new Name();
		IReader next = nameReader.read(sr, env);
		
		if (next instanceof Error)	{
			return next;
		}
		
		String alias = nameReader.getName();
		next = env.createReader(alias);
		
		System.out.print("r(Reader): ");
		if (null == next)	{
			System.out.println("NOT FOUND FOR " + alias);
			Error e = new Error();
			e.setMessage("There is no reader by the name " + alias);
			return e;
		}
		
		return next;
	}

}
