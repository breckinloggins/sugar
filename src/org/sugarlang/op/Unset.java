/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseOp;
import org.sugarlang.type.TSymbol;


/**
 * Causes the symbol at the top of the stack to be unbound
 * @author bloggins
 */
public class Unset extends BaseOp {

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
			env.pushOp("error");
			return;
		}
		
		if (!(sym instanceof TSymbol))	{
			env.pushString("Cannot unset, object on the stack isn't a symbol");
			env.pushOp("error");
			return;
		}
		
		env.unsetBinding((TSymbol)sym);
	}

}
