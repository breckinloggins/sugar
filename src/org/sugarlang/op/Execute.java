/**
 * 
 */
package org.sugarlang.op;

import java.util.Stack;
import org.sugarlang.Environment;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VMacro;
import org.sugarlang.value.VQuote;


/**
 * Pops an argument off the stack and executes the callable entity by that name
 * @author bloggins
 */
public class Execute extends BaseOp {

	public Execute() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Pops an argument off the stack and executes it";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		env.evaluateStack();
		if (env.isStackEmpty())	{
			env.pushError("Cannot execute a command because the stack is empty");
			return;
		}
		
		if (env.peek() instanceof VMacro)	{
			VMacro m = (VMacro)env.pop();
			Stack<IValue> macroStack = new Stack<IValue>();
			for (Object o : m.getStackList())	{	
				VQuote q = (VQuote)o;
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
