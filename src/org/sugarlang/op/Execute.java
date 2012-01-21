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
 * Pops an argument off the stack and executes the callable entity by that name
 * @author bloggins
 */
public class Execute extends BaseOp {

	@Override
	public String getDescription() {
		return "Pops an argument off the stack and executes it";
	}

	@Override
	public void execute(Environment env) {
		env.evaluateStack();
		if (env.isStackEmpty())	{
			env.pushString("Cannot execute a command because the stack is empty");
			env.pushOp("error");
			return;
		}
		
		if (env.peek() instanceof TMacro)	{
			TMacro m = (TMacro)env.pop();
			Stack<Object> macroStack = new Stack<Object>();
			for (Object o : m.getStackList())	{	
				TQuote q = (TQuote)o;
				macroStack.push(q.getInner());
			}
			
			// Transfer the stack to the environment
			while (!macroStack.isEmpty())	{
				env.push(macroStack.pop());
			}
		} else {
			String opName = env.pop().toString();
			
			// pushOp will handle the error if opName isn't an op
			env.pushOp(opName);
		}
	}
}
