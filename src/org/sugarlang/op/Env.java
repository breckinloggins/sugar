/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseOp;
import org.sugarlang.dictionary.IOp;
import org.sugarlang.dictionary.IEntry;
import org.sugarlang.dictionary.IReader;
import org.sugarlang.type.TSymbol;


/**
 * @author bloggins
 *
 */
public class Env extends BaseOp {

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
		
		System.out.println("\nOpcode Bindings:");
		for (TSymbol sym : env.getBindingSymbols())	{
			Object o = env.getBoundObject(sym);
			if (!(o instanceof IOp))	{
				continue;
			}
			
			IOp op = (IOp)o;
			if (op instanceof IEntry)	{
				System.out.print(sym.getName() + " => [" + ((IEntry)op).getDescription() + "]");
				System.out.println();		
			}
		}
		
		System.out.println("\nOther Bindings:");
		for (TSymbol symbol : env.getBindingSymbols())	{
			Object o = env.getBoundObject(symbol);
			if (o instanceof IOp || o instanceof IReader)	{
				continue;
			}
			
			System.out.print(symbol.getName() + " => " + o + " <" + o.getClass().getName() + ">");
			System.out.println();
		}
	}
}
