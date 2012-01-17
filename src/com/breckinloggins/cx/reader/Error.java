package com.breckinloggins.cx.reader;

import java.io.IOException;
import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;
import com.breckinloggins.cx.dictionary.ICommand;

public class Error extends BaseReader {

	// TODO: This should optionally read a string once we have balanced pairs
	
	@Override
	public String getDescription()	{
		return "Reads no characters and emits an error message";
	}
	
	@Override
	public void read(Environment env) throws IOException {
		ICommand err = env.getCommand("error");
		err.execute(env);
		
		env.pushReader("terminator");
		env.pushCommand("read");
	}

}
