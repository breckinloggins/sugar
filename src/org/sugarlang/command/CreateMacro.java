/**
 * 
 */
package org.sugarlang.command;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;
import org.sugarlang.type.TMacro;
import org.sugarlang.type.TQuote;

/**
 * Pops zero or more quoted items off the stack and pushes a TMacro containing those items on the stack
 * @author bloggins
 */
public class CreateMacro extends BaseCommand {

	@Override
	public String getDescription() {
		return "Pops zero or more quoted items off the stack and pushes a TMacro containing those items on the stack";
	}

	@Override
	public void execute(Environment env) {
		TMacro m = new TMacro();
		
		while(!env.isStackEmpty())	{
			if (env.peek() instanceof TQuote)	{
				m.getStack().push(env.pop());
			} else {
				break;
			}
		}
		
		env.push(m);
	}

}
