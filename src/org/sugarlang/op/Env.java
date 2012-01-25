/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.base.IOp;
import org.sugarlang.base.IReader;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VSymbol;


/**
 * @author bloggins
 *
 */
public class Env extends BaseOp {

	public Env() throws TypeException {
		super();
	}

	@Override
	public String getDescription()	{
		return "Prints info about the current environment";
	}
	
	@Override
	public void executeInternal(Environment env) throws TypeException {
		
		System.out.println("\nStack:");
		env.printStack(System.out);
		
		System.out.println("\nReader Bindings:");
		for (VSymbol sym : env.getBindingSymbols())	{
			Object o = env.getBoundObject(sym);
			if (!(o instanceof IReader))	{
				continue;
			}
			
			IReader reader = (IReader)o;
			if (reader instanceof IValue)	{
				System.out.print(sym.toString() + " => [" + ((IValue)reader).getDescription() + "]");
				System.out.println();	
			}
		}
		
		System.out.println("\nOpcode Bindings:");
		for (VSymbol sym : env.getBindingSymbols())	{
			Object o = env.getBoundObject(sym);
			if (!(o instanceof IOp))	{
				continue;
			}
			
			IOp op = (IOp)o;
			if (op instanceof IValue)	{
				System.out.print(sym.toString() + " => [" + ((IValue)op).getDescription() + "]");
				System.out.println();		
			}
		}
		
		System.out.println("\nOther Bindings:");
		for (VSymbol symbol : env.getBindingSymbols())	{
			IValue v = env.getBoundObject(symbol);
			if (v instanceof IOp || v instanceof IReader)	{
				continue;
			}
			
			String sym = symbol.toString();
			if (" ".equals(sym))	{
				sym = "' '";
			} else if ("\r".equals(sym))	{
				sym = "\\r";
			} else if ("\n".equals(sym)) {
				sym = "\\n";
			} else if ("\t".equals(sym)) {
				sym = "\\t";
			}
			
			System.out.print(sym + " => " + v + " <" + v.getType().toString() + ">");
			System.out.println();
		}
	}
}
