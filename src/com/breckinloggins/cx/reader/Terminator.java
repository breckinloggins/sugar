package com.breckinloggins.cx.reader;

import java.io.StringReader;
import com.breckinloggins.cx.Environment;

public class Terminator extends BaseReader {

	@Override
	public IReader read(StringReader sr, Environment env) {
		// The terminator never reads anything
		getWriter().println("r(Terminator)");
		return null;
	}

}
