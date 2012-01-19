/**
 * 
 */
package org.sugarlang.command;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;
import org.sugarlang.dictionary.IEntry;
import org.sugarlang.dictionary.IReader;


/**
 * Pops an item off the stack and pushes a reader by that name
 * @author bloggins
 *
 */
public class Reader extends BaseCommand {

	@Override
	public String getDescription() {
		return "Pops an item off the stack and pushes a reader by that name";
	}

	@Override
	public void execute(Environment env) {
		
		env.evaluateStack();
		Object readerEntry = env.pop();
		if (null == readerEntry)	{
			env.pushString("The stack is empty");
			env.getCommand("error").execute(env);
			return;
		}
		
		String readerName = readerEntry.toString();
		IReader reader = env.getReader(readerName);
		if (null == reader)	{
			env.pushString("There is no reader by the name \"" + readerName + "\"");
			env.getCommand("error").execute(env);
			return;
		}
		
		if (!(reader instanceof IEntry))	{
			env.pushString("Reader \"" + readerName + "\" is not a dictionary entry");
			env.getCommand("error").execute(env);
			return;
		}
		
		env.push((IEntry)reader);
	}
	

}
