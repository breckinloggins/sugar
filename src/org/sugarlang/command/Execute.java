/**
 * 
 */
package org.sugarlang.command;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;
import org.sugarlang.dictionary.ICommand;
import org.sugarlang.type.TMacro;
import org.sugarlang.type.TQuote;


/**
 * Pops an argument off the stack and executes the command by that name
 * @author bloggins
 */
public class Execute extends BaseCommand {

	@Override
	public String getDescription() {
		return "Pops an argument off the stack and executes it";
	}

	@Override
	public void execute(Environment env) {
		env.evaluateStack();
		if (env.isStackEmpty())	{
			env.pushString("Cannot execute a command because the stack is empty");
			env.pushCommand("error");
			return;
		}
		
		if (env.peek() instanceof TMacro)	{
			TMacro m = (TMacro)env.pop();
			while (!m.getStack().isEmpty())	{
				TQuote q = (TQuote)m.getStack().pop();
				env.push(q.getInner());
			}
		} else {
			String cmdName = env.pop().toString();
			ICommand cmd = env.getCommand(cmdName);
			if (null == cmd)	{
				env.pushString("ERROR: Command \"" + cmdName + "\" not found");
				env.pushCommand("error");
				return;
			}
			
			env.pushCommand(cmdName);
		}
	}
}
