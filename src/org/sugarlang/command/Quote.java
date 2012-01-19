/**
 * 
 */
package org.sugarlang.command;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;
import org.sugarlang.type.TQuote;

/**
 * Quotes the object on the top of the stack
 * @author bloggins
 */
public class Quote extends BaseCommand {

	@Override
	public String getDescription() {
		return "Quotes the object on the top of the stack";
	}

	@Override
	public void execute(Environment env) {
		env.evaluateStack();
		if (env.isStackEmpty())	{
			env.pushString("Cannot quote object, stack is empty");
			env.pushCommand("error");
			return;
		}
		
		Object o = env.pop();
		TQuote q = new TQuote();
		q.setInnter(o);
		env.push(q);
	}

}
