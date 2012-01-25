/**
 * 
 */
package org.sugarlang.op;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.base.IReader;
import org.sugarlang.type.TypeException;


/**
 * Executes the reader at the top of the stack
 * @author bloggins
 */
public class Read extends BaseOp {

	public Read() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Executes the reader at the top of the stack";
	}

	@Override
	public void executeInternal(Environment env) throws TypeException {
		env.evaluateStack();
		Object arg = env.pop();
		if (null == arg)	{
			env.pushError("Cannot execute reader. stack empty");
			return;
		}
		
		if (!(arg instanceof IReader))	{
			env.pushError("Argument \"" + arg.toString() + "\" is not a reader");
			return;
		}
		
		IReader reader = (IReader)arg;
		try {
			reader.read(env);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
