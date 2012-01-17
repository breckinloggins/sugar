/**
 * 
 */
package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;
import com.breckinloggins.cx.dictionary.ICommand;
import com.breckinloggins.cx.dictionary.IEntry;
import com.breckinloggins.cx.dictionary.IReader;

/**
 * @author bloggins
 *
 */
public class Info extends BaseReader {

	@Override
	public String getDescription()	{
		return "Reads no characters and outputs a list of available readers";
	}
	
	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		
		getWriter().println("\nStack:");
		env.printStack();
		
		getWriter().println("\nReaders:");
		for (String alias : env.getReaderAliases())	{
			IReader reader = env.getReader(alias);
			if (reader instanceof IEntry)	{
				getWriter().print(alias + " [" + ((IEntry)reader).getDescription() + "]");
				getWriter().println();	
			}
		}
		
		getWriter().println("\nCommands:");
		for (String alias : env.getCommandAliases())	{
			ICommand cmd = env.getCommand(alias);
			if (cmd instanceof IEntry)	{
				getWriter().print(alias + " [" + ((IEntry)cmd).getDescription() + "]");
				getWriter().println();		
			}
		}
		
		return env.getReader("discriminator");
	}
}
