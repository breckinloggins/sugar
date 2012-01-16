package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;
import com.breckinloggins.cx.dictionary.IReader;

public class BeginPair extends BaseReader	{

	@Override
	public String getDescription()	{
		return "Reads a starting sequence of a balanced pair (such as '(')";
	}
	
	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
