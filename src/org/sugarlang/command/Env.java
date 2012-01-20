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
		
		System.out.println("\nReader Bindings:");
		for (TSymbol sym : env.getBindingSymbols())	{
			Object o = env.getBoundObject(sym);
			if (!(o instanceof IReader))	{
				continue;
			}
			
			IReader reader = (IReader)o;
			if (reader instanceof IEntry)	{
				System.out.print(sym.getName() + " => [" + ((IEntry)reader).getDescription() + "]");
				System.out.println();	
			}
		}
		
		System.out.println("\nCommand Bindings:");
		for (TSymbol sym : env.getBindingSymbols())	{
			Object o = env.getBoundObject(sym);
			if (!(o instanceof ICommand))	{
				continue;
			}
			
			ICommand cmd = (ICommand)o;
			if (cmd instanceof IEntry)	{
				System.out.print(sym.getName() + " => [" + ((IEntry)cmd).getDescription() + "]");
				System.out.println();		
			}
		}
		
		System.out.println("\nOther Bindings:");
		for (TSymbol symbol : env.getBindingSymbols())	{
			Object o = env.getBoundObject(symbol);
			if (o instanceof ICommand || o instanceof IReader)	{
				continue;
			}
			
			System.out.print(symbol.getName() + " => " + o + " <" + o.getClass().getName() + ">");
			System.out.println();
		}
	}
}
