/**
 * 
 */
package org.sugarlang.op;

import java.util.Stack;
import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseOp;
import org.sugarlang.type.TMacro;
import org.sugarlang.type.TQuote;

/**
 * Pops zero or more quoted items off the stack and pushes a TMacro containing those items on the stack
 * @author bloggins
 */
public class CreateMacro extends BaseOp {

	@Override
	public String getDescription() {
		return "Pops zero or more quoted items off the stack and pushes a TMacro containing those items on the stack";
	}

	@Override
	public void execute(Environment env) {
		Stack<Object> macroStack = new Stack<Object>();
		
		while(!env.isStackEmpty())	{
			if (env.peek() instanceof TQuote)	{
				macroStack.push(env.pop());
			} else {
				break;
			}
		}
		
		env.push(new TMacro(macroStack));
	}

}
