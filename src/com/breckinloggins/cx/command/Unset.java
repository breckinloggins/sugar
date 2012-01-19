/**
 * 
 */
package com.breckinloggins.cx.command;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseCommand;
import com.breckinloggins.cx.type.TSymbol;

/**
 * Causes the symbol at the top of the stack to be unbound
 * @author bloggins
 */
public class Unset extends BaseCommand {

	@Override
	public String getDescription() {
		return "Causes the symbol at the top of the stack to be unbound";
	}

	@Override
	public void execute(Environment env) {
		
		env.evaluateStack();
		Object sym = env.pop();
		if (null == sym)	{
			env.pushString("Cannot unset, stack is empty");
			env.pushCommand("error");
			return;
		}
		
		if (!(sym instanceof TSymbol))	{
			env.pushString("Cannot unset, object on the stack isn't a symbol");
			env.pushCommand("error");
			return;
		}
		
		env.unsetBinding((TSymbol)sym);
	}

}
