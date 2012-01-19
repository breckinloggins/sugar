/**
 * 
 */
package com.breckinloggins.cx.command;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseCommand;
import com.breckinloggins.cx.type.TSymbol;

/**
 * Sets a binding in the current dictionary
 * @author bloggins
 */
public class Set extends BaseCommand {

	@Override
	public String getDescription() {
		return "Sets a binding in the current dictionary (top-1: symbol, top: object)";
	}

	@Override
	public void execute(Environment env) {
		env.evaluateStack();
		Object o = env.pop();
		if (null == o)	{
			env.pushString("Cannot set binding object, stack is empty");
			env.pushCommand("error");
			return;
		}
		
		env.evaluateStack();
		Object sym = env.pop();
		if (null == sym)	{
			env.pushString("Cannot set binding symbol, stack is empty");
			env.pushCommand("error");
			return;
		}
		
		if (!(sym instanceof TSymbol))	{
			env.pushString("Cannot set binding, object on the stack isn't a symbol");
			env.pushCommand("error");
			return;
		}
		
		env.setBinding((TSymbol)sym, o);
	}

}
