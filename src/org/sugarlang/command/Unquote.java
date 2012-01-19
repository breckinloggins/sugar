/**
 * 
 */
package org.sugarlang.command;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;
import org.sugarlang.type.TQuote;

/**
 * Unquotes the quoted item at the top of the stack and pushes the unquoted item
 * @author bloggins
 */
public class Unquote extends BaseCommand {

	@Override
	public String getDescription() {
		return "Unquotes the quoted item at the top of the stack and pushes the unquoted item";
	}

	@Override
	public void execute(Environment env) {
		env.evaluateStack();
		if (env.isStackEmpty())	{
			env.pushString("Cannot unquote, stack is empty");
			env.pushCommand("error");
			return;
		}
		
		if (!(env.peek() instanceof TQuote))	{
			env.pushString("Cannot unquote, object on the stack isn't quoted");
			env.pushCommand("error");
			return;
		}
		
		TQuote q = (TQuote)env.pop();
		env.push(q.getInner());
	}
}
