package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;
import com.breckinloggins.cx.Environment;

public class EndPair extends BaseReader {
	@Override
	public String getDescription()	{
		return "Reads an ending sequence of a balanced pair (such as ')')";
	}
	
	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
