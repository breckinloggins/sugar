/**
 * 
 */
package org.sugarlang.command;

import java.util.Stack;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;


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
		// [eval items] (either a single non-command or a command)
		// [if branch]
		// [if mark]
		// [else branch]
		// [else mark]
		
		if (null == env.peek())	{
			env.pushString("Cannot evaluate if.  Stack is empty");
			env.pushCommand("error");
			return;
		}
		
		// Determine truthiness, which will inevitably need some fine tuning
		env.evaluateStack();
		Object test = env.pop();
		
		Stack<Object> ifBranch = env.popToMark();
		Stack<Object> elseBranch = env.popToMark();
		
		if (test == null || test.toString() == "0" || test.toString().toLowerCase() == "false")	{
			
			// FALSE - push the else branch back on
			env.pushStack(elseBranch);
		} else {
			env.pushStack(ifBranch);
		}
		
	}

}
