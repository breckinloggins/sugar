package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;

public interface IReader {
	IReader read(StringReader sr, Environment env) throws IOException;
}
