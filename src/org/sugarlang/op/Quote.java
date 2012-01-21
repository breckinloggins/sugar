/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseOp;
import org.sugarlang.type.TQuote;

/**
 * Quotes the object on the top of the stack
 * @author bloggins
 */
public class Quote extends BaseOp {

	@Override
	public String getDescription() {
		return "Quotes the object on the top of the stack";
	}

	@Override
	public void execute(Environment env) {
		env.evaluateStack();
		if (env.isStackEmpty())	{
			env.pushString("Cannot quote object, stack is empty");
			env.pushOp("error");
			return;
		}
		
		Object o = env.pop();
		TQuote q = new TQuote(o);
		env.push(q);
	}

}
