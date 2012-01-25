/**
 * 
 */
package org.sugarlang.op;

import java.util.Stack;

import org.sugarlang.Environment;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TNull;
import org.sugarlang.type.TypeException;


/**
 * Evaluates the item at the top of the stack.  If true, leaves the if branch at the top of
 * the stack to be evaluated, if false, leaves the else branch
 * @author bloggins
 */
public class If extends BaseOp {

	public If() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Evaluates the item at the top of the stack.  If true, leaves the if branch at the top of the stack to be evaluated.  If false, leaves the else branch";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		// The stack structure (TOP on top)
		// [eval items] (either a single non-command or a command)
		// [if branch]
		// [if mark]
		// [else branch]
		// [else mark]
		
		if (null == env.peek())	{
			env.pushError("Cannot evaluate if.  Stack is empty");
			return;
		}
		
		// Determine truthiness, which will inevitably need some fine tuning
		env.evaluateStack();
		Object test = env.pop();
		
		Stack<IValue> ifBranch = env.popToMark();
		Stack<IValue> elseBranch = env.popToMark();
		
		if (test == null || test instanceof TNull || test.toString() == "0" || test.toString().toLowerCase() == "false")	{
			
			// FALSE - push the else branch back on
			env.pushStack(elseBranch);
		} else {
			env.pushStack(ifBranch);
		}
		
	}

}
