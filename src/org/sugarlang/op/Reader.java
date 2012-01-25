/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;

/**
 * Pops an item off the stack and pushes a reader by that name
 * @author bloggins
 * TODO: I see no reason why this can't be a macro as soon as we have error throwing and
 * typed is-testing
 */
public class Reader extends BaseOp {

	public Reader() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Pops an item off the stack and pushes a reader by that name";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		
		env.evaluateStack();
		Object readerEntry = env.pop();
		if (null == readerEntry)	{
			env.pushError("The stack is empty");
			return;
		}
		
		String readerName = readerEntry.toString();
		
		// pushReader will throw an error if readerName isn't a reader
		env.pushReader(readerName);
	}
	

}
