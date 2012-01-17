/**
 * 
 */
package com.breckinloggins.cx.command;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseCommand;
import com.breckinloggins.cx.dictionary.IEntry;
import com.breckinloggins.cx.dictionary.IReader;

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
