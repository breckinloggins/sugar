/**
 * 
 */
package org.sugarlang.command;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;

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
			env.pushCommand("error");
			return;
		}
		
		String readerName = readerEntry.toString();
		
		// pushReader will throw an error if readerName isn't a reader
		env.pushReader(readerName);
	}
	

}
