package org.sugarlang.reader;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseReader;


public class Terminator extends BaseReader {
	
	@Override
	public String getDescription()	{
		return "Represents the end of a read-eval chain";
	}

	@Override
	public void read(Environment env) {
		// The terminator never reads anything
		System.err.println("r(Terminator)");
	}

}
