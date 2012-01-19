package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseReader;

public class Error extends BaseReader {

	// TODO: This should optionally read a string once we have balanced pairs
	
	@Override
	public String getDescription()	{
		return "Reads no characters and emits an error message";
	}
	
	@Override
	public void read(Environment env) throws IOException {
		env.pushString("READER ERROR");
		env.pushCommand("error");
	}

}
