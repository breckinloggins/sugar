package com.breckinloggins.cx.reader;

import java.io.StringReader;
import com.breckinloggins.cx.Environment;

public class Terminator implements IReader {

	@Override
	public IReader read(StringReader sr, Environment env) {
		// The terminator never reads anything
		System.out.println("r(Terminator");
		return null;
	}

}
