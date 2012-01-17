package com.breckinloggins.cx.reader;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;

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
