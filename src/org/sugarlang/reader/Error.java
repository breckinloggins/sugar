package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseReader;
import org.sugarlang.dictionary.ICommand;


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
