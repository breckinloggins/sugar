/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VQuote;

/**
 * Quotes the object on the top of the stack
 * @author bloggins
 */
public class Quote extends BaseOp {

	public Quote() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Quotes the object on the top of the stack";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		if (env.isStackEmpty())	{
			env.pushError("Cannot quote object, stack is empty");
			return;
		}
		
		IValue v = env.pop();
		VQuote q = new VQuote(v);
		env.push(q);
	}

}
