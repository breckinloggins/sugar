package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;
import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;
import com.breckinloggins.cx.dictionary.IReader;

public class Reader extends BaseReader {
	@Override
	public String getDescription()	{
		return "Manually invokes a reader by the given name (e.g. '#list')";
	}

	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		int ch = sr.read();
		if (ch == -1)	{
			env.pushString("Unexpected EOF");
			return env.getReader("error");
		}
		
		Name nameReader = (Name)env.getReader("name");
		IReader next = nameReader.read(sr, env);
		
		if (next instanceof Error)	{
			return next;
		}
		
		String alias = env.pop().getName();
		next = env.getReader(alias);
		
		getWriter().print("r(Reader): ");
		if (null == next)	{
			env.pushString("There is no reader by the name \"" + alias + "\"");
			return env.getReader("error");
		}
		
		return next;
	}

}
