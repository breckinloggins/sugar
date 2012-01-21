/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseOp;

/**
 * Pops an item off the stack and pushes a reader by that name
 * @author bloggins
 * TODO: I see no reason why this can't be a macro as soon as we have error throwing and
 * typed is-testing
 */
public class Reader extends BaseOp {

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
			env.pushOp("error");
			return;
		}
		
		String readerName = readerEntry.toString();
		
		// pushReader will throw an error if readerName isn't a reader
		env.pushReader(readerName);
	}
	

}
