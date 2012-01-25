package org.sugarlang.reader;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;


public class Terminator extends BaseReader {
	
	public Terminator() throws TypeException {
		super();
	}

	@Override
	public String getDescription()	{
		return "Represents the end of a read-eval chain";
	}

	@Override
	public void readInternal(Environment env) {
		// The terminator never reads anything
		System.err.println("r(Terminator)");
	}

}
