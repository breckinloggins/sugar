package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseReader;
import org.sugarlang.dictionary.IOp;
import org.sugarlang.type.TMacro;
import org.sugarlang.type.TSymbol;


/**
 * Reader that takes the name of a command or macro and executes it
 * @author bloggins
 */
public class Command extends BaseReader {

	@Override
	public String getDescription() {
		return "Takes the name of a command (op, macro, etc.) and executes that command";
	}

	@Override
	public void read(Environment env) throws IOException {
		
		env.pushReader("symbol");
		env.pushOp("read");
		env.evaluateStack();
		
		if (env.isStackEmpty() || !(env.peek() instanceof TSymbol))	{
			return;
		}
		
		String alias = ((TSymbol)env.pop()).getName();
		
		Object o = env.getBoundObject(alias);
		if (null == o)	{
			env.pushString("The symbol \"" + alias + "\" is not bound");
			env.pushOp("error");
		}
		
		if (o instanceof IOp)	{
			// An error will be pushed if alias doesn't refer to a command
			env.pushOp(alias); 
		} else if (o instanceof TMacro)	{
			env.push(o);
			env.pushOp("execute");
		} else {
			env.pushString("Don't know how to execute object of type <" + o.getClass().getName() + "> for symbol \"" + alias + "\"");
			env.pushOp("error");
		}
		
		discardWhitespace(env);
		
		
	}
}
