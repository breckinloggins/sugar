/**
 * 
 */
package org.sugarlang.op;

import java.util.Stack;
import org.sugarlang.Environment;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TMark;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VMacro;
import org.sugarlang.value.VQuote;

/**
 * Pops zero or more quoted items off the stack and pushes a TMacro containing those items on the stack
 * @author bloggins
 */
public class CreateMacro extends BaseOp {

	public CreateMacro() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Pops zero or more quoted items off the stack (up to mark) and pushes a TMacro containing those items on the stack";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		Stack<IValue> macroStack = new Stack<IValue>();
		
		while(!env.isStackEmpty())	{
			if (env.peek() instanceof VQuote)	{
				macroStack.push(env.pop());
			} else if (env.peek() instanceof TMark)	{
				env.pop();
				break;
			} else {
				break;
			}
		}
		
		env.push(new VMacro(macroStack));
	}

}
