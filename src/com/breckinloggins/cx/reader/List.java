/**
 * 
 */
package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.IEntry;
import com.breckinloggins.cx.command.ICommand;

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
		
		getWriter().println("\nReaders:");
		for (String alias : env.getReaderAliases())	{
			IReader reader = env.createReader(alias);
			if (reader instanceof IEntry)	{
				getWriter().print(alias + " [" + ((IEntry)reader).getDescription() + "]");
				getWriter().println();	
			}
		}
		
		getWriter().println("\nCommands:");
		for (String alias : env.getCommandAliases())	{
			ICommand cmd = env.createCommand(alias);
			if (cmd instanceof IEntry)	{
				getWriter().print(alias + " [" + ((IEntry)cmd).getDescription() + "]");
				getWriter().println();		
			}
		}
		
		return env.createReader("discriminator");
	}
}
