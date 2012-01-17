/**
 * 
 */
package com.breckinloggins.cx.command;

import java.util.Stack;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseCommand;

/**
 * Evaluates the item at the top of the stack.  If true, leaves the if branch at the top of
 * the stack to be evaluated, if false, leaves the else branch
 * @author bloggins
 */
public class If extends BaseCommand {

	@Override
	public String getDescription() {
		return "Evaluates the item at the top of the stack.  If true, leaves the if branch at the top of the stack to be evaluated.  If false, leaves the else branch";
	}

	@Override
	public void execute(Environment env) {
		// The stack structure (TOP on top)
		// [0..n] 			eval items
		// [n+1]			x = # items in if branch
		// [n+2]			y = # items in else branch
		// [n+3..n+3+x]		if branch
		// [n+4+x..n+4+x+y]	else branch
		
		if (null == env.peek())	{
			env.pushString("Cannot evaluate if.  Stack is empty");
			env.pushCommand("error");
			return;
		}
		
		// Determine truthiness, which will inevitably need some fine tuning
		env.evaluateStack();
		Object test = env.pop();
		
		env.evaluateStack();
		Object ifCount = env.pop();
		if (null == ifCount || !(ifCount instanceof Integer))	{
			env.pushString("If stack structure is incorrect, invalid if branch count");
			env.pushCommand("error");
			return;
		}
		
		env.evaluateStack();
		Object elseCount = env.pop();
		if (null == elseCount || !(elseCount instanceof Integer))	{
			env.pushString("If stack structure is incorrect, invalid else branch count");
			env.pushCommand("error");
			return;
		}
		
		int ifs = ((Integer)ifCount);
		int elses = ((Integer)elseCount);
		
		if (test == null || test.toString() == "0" || test.toString().toLowerCase() == "false")	{
			
			// FALSE - pop off the if branch leaving only the else branch
			while (ifs-- > 0)	{
				if (null == env.pop())
				{
					env.pushString("If stack structure is incorrect, if branch count greater than stack size");
					env.pushCommand("error");
					return;
				}
			}
		} else {
			
			// FALSE - pop off the else branch leaving only the if branch
			Stack<Object> ifStack = new Stack<Object>();
			
			// Preserve the if branch
			while (ifs-- > 0)	{
				Object pop = env.pop();
				if (null == pop)	{
					env.pushString("If stack structure is incorrect, if branch count greater than stack size");
					env.pushCommand("error");
					return;
				}
				
				ifStack.push(pop);
			}
			
			// Discard the else branch
			while (elses-- > 0)	{
				if (null == env.pop())
				{
					env.pushString("If stack structure is incorrect, else branch count greater than stack size");
					env.pushCommand("error");
					return;
				}
			}
			
			// Restore the if branch
			while (!ifStack.isEmpty())	{
				env.push(ifStack.pop());
			}
		}
		
	}

}
