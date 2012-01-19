package org.sugarlang.command;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;
import org.sugarlang.type.TSymbol;


/**
 * Given a symbol on top of the stack, retrieves the object bound to that symbol and pushes it 
 * on the top of the stack.  The binding must exist
 * @author bloggins
 *
 */
public class Get extends BaseCommand {

	@Override
	public String getDescription() {
		return "Given a symbol on top of the stack, retrieves the object bound to that symbol and pushes it on the top of the stack.  The binding must exist";
	}

	@Override
	public void execute(Environment env) {
		
		env.evaluateStack();
		Object sym = env.pop();
		if (null == sym)	{
			env.pushString("Cannot execute get command, the stack is empty");
			env.pushCommand("error");
			return;
		}
		
		if (!(sym instanceof TSymbol))	{
			env.pushString("Cannot execute get command, object on the stack isn't a symbol");
			env.pushCommand("error");
			return;
		}
		
		Object val = env.getBoundObject((TSymbol)sym);
		if (null == val)	{
			env.pushString("The symbol \"" + ((TSymbol)sym).getName() + "\" is not bound");
			env.pushCommand("error");
			return;
		}
		
		env.push(val);
	}


}
