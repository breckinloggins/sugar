/**
 * 
 */
package com.breckinloggins.cx.command;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseCommand;
import com.breckinloggins.cx.dictionary.ICommand;
import com.breckinloggins.cx.dictionary.IEntry;
import com.breckinloggins.cx.dictionary.IReader;
import com.breckinloggins.cx.type.TSymbol;

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
		
		System.out.println("\nStack:");
		env.printStack(System.out);
		
		System.out.println("\nReaders:");
		for (String alias : env.getReaderAliases())	{
			IReader reader = env.getReader(alias);
			if (reader instanceof IEntry)	{
				System.out.print(alias + " [" + ((IEntry)reader).getDescription() + "]");
				System.out.println();	
			}
		}
		
		System.out.println("\nCommands:");
		for (String alias : env.getCommandAliases())	{
			ICommand cmd = env.getCommand(alias);
			if (cmd instanceof IEntry)	{
				System.out.print(alias + " [" + ((IEntry)cmd).getDescription() + "]");
				System.out.println();		
			}
		}
		
		System.out.println("\nBindings:");
		for (TSymbol symbol : env.getBindingSymbols())	{
			Object o = env.getBoundObject(symbol);
			System.out.print(symbol.getName() + " => " + o + " <" + o.getClass().getName() + ">");
			System.out.println();
		}
	}
}
