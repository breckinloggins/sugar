/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VQuote;

/**
 * Unquotes the quoted item at the top of the stack and pushes the unquoted item
 * @author bloggins
 */
public class Unquote extends BaseOp {

	public Unquote() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Unquotes the quoted item at the top of the stack and pushes the unquoted item";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		env.evaluateStack();
		if (env.isStackEmpty())	{
			env.pushError("Cannot unquote, stack is empty");
			return;
		}
		
		if (!(env.peek() instanceof VQuote))	{
			env.pushError("Cannot unquote, object on the stack isn't quoted");
			return;
		}
		
		VQuote q = (VQuote)env.pop();
		env.push(q.getInner());
	}
}
