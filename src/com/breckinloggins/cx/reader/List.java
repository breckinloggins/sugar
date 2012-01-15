/**
 * 
 */
package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;

/**
 * @author bloggins
 *
 */
public class List extends BaseReader {

	@Override
	public String getDescription()	{
		return "Reads no characters and outputs a list of available readers";
	}
	
	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		
		for (String alias : env.getReaderAliases())	{
			IReader reader = env.createReader(alias);
			getWriter().print(alias + " [" + reader.getDescription() + "]");
			getWriter().println();
		}
		
		return env.createReader("discriminator");
	}
}
