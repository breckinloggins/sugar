package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.base.IOp;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VMacro;
import org.sugarlang.value.VSymbol;


/**
 * Reader that takes the name of a command or macro and executes it
 * @author bloggins
 */
public class Command extends BaseReader {

	public Command() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Takes the name of a command (op, macro, etc.) and executes that command";
	}

	@Override
	public void readInternal(Environment env) throws IOException, TypeException {
		
		env.pushReader("symbol");
		env.pushOp("read");
		env.evaluateStack();
		
		if (env.isStackEmpty() || !(env.peek() instanceof VSymbol))	{
			env.pushError("Command reader expected a symbol");
			return;
		}
		
		String alias = ((VSymbol)env.pop()).toString();
		
		IValue v = env.getBoundObject(alias);
		if (null == v)	{
			env.pushError("The symbol \"" + alias + "\" is not bound");
		}
		
		if (v instanceof IOp)	{
			// An error will be pushed if alias doesn't refer to a command
			env.pushOp(alias); 
		} else if (v instanceof VMacro)	{
			env.push(v);
			env.pushOp("execute");
		} else {
			env.pushError("Don't know how to execute object of type <" + v.getType().toString() + "> for symbol \"" + alias + "\"");
		}
		
		discardWhitespace(env);
		
		
	}
}
