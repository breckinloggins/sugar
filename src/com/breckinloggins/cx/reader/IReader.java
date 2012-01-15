package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;

public interface IReader {
	void setWriter(PrintStream writer);
	
	IReader read(StringReader sr, Environment env) throws IOException;
}
