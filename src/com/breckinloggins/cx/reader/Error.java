package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;
import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;
import com.breckinloggins.cx.dictionary.ICommand;
import com.breckinloggins.cx.dictionary.IReader;

public class Error extends BaseReader {

	// TODO: This should optionally read a string once we have balanced pairs
	
	@Override
	public String getDescription()	{
		return "Reads no characters and emits an error message";
	}
	
	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		ICommand err = env.getCommand("error");
		err.execute(env);
		
		return env.getReader("terminator");
	}

}
