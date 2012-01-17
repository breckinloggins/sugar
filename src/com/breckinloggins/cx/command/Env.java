/**
 * 
 */
package com.breckinloggins.cx.command;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseCommand;
import com.breckinloggins.cx.dictionary.ICommand;
import com.breckinloggins.cx.dictionary.IEntry;
import com.breckinloggins.cx.dictionary.IReader;

/**
 * @author bloggins
 *
 */
public class Env extends BaseCommand {

	@Override
	public String getDescription()	{
		return "Prints info about the current environment";
	}
	
	@Override
	public void execute(Environment env) {
		
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
	}
}
