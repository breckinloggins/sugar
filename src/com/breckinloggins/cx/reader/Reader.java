package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;
import com.breckinloggins.cx.Environment;

public class Reader extends BaseReader {
	@Override
	public String getDescription()	{
		return "Manually invokes a reader by the given name (e.g. '#list')";
	}

	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		int ch = sr.read();
		if (ch == -1)	{
			Error e = (Error)env.createReader("error");
			e.setMessage("Unexpected");
			return e;
		}
		
		Name nameReader = (Name)env.createReader("name");
		IReader next = nameReader.read(sr, env);
		
		if (next instanceof Error)	{
			return next;
		}
		
		String alias = env.pop();
		next = env.createReader(alias);
		
		getWriter().print("r(Reader): ");
		if (null == next)	{
			getWriter().println("NOT FOUND FOR " + alias);
			Error e = (Error)env.createReader("error");
			e.setMessage("There is no reader by the name " + alias);
			return e;
		}
		
		return next;
	}

}
