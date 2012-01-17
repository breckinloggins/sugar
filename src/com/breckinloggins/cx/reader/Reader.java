package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.InputStream;
import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;

public class Reader extends BaseReader {
	@Override
	public String getDescription()	{
		return "Manually invokes a reader by the given name (e.g. '#list')";
	}

	@Override
	public void read(Environment env) throws IOException {
		
		InputStream sr = System.in;
		int ch = sr.read();
		if (ch == -1)	{
			env.pushString("Unexpected EOF");
			env.pushReader("error");
			env.pushCommand("read");
			return;
		}
		
		/*
		Name nameReader = (Name)env.getReader("name");
		IReader next = nameReader.read(sr, env);
		
		if (next instanceof Error)	{
			env.pushReader(next);
			return;
		}
		
		String alias = env.pop().getName();
		next = env.getReader(alias);
		
		System.err.print("r(Reader): ");
		if (null == next)	{
			env.pushString("There is no reader by the name \"" + alias + "\"");
			env.pushReader("error");
			return;
		}
		
		env.pushReader(next);
		*/
	}
}
