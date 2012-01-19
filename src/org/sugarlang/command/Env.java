/**
 * 
 */
package org.sugarlang.command;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;
import org.sugarlang.dictionary.ICommand;
import org.sugarlang.dictionary.IEntry;
import org.sugarlang.dictionary.IReader;
import org.sugarlang.type.TSymbol;


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
