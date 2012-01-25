/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VSymbol;


/**
 * Causes the symbol at the top of the stack to be unbound
 * @author bloggins
 */
public class Unset extends BaseOp {

	public Unset() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Causes the symbol at the top of the stack to be unbound";
	}

	@Override
	public void executeInternal(Environment env) {
		
		env.evaluateStack();
		Object sym = env.pop();
		if (null == sym)	{
			env.pushError("Cannot unset, stack is empty");
			return;
		}
		
		if (!(sym instanceof VSymbol))	{
			env.pushError("Cannot unset, object on the stack isn't a symbol");
			return;
		}
		
		env.unsetBinding((VSymbol)sym);
	}

}
