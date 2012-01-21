package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseReader;
import org.sugarlang.dictionary.ICommand;
import org.sugarlang.type.TMacro;
import org.sugarlang.type.TSymbol;


/**
 * Reader that takes the name of a command or macro and executes it
 * @author bloggins
 */
public class Command extends BaseReader {

	@Override
	public String getDescription() {
		return "Takes the name of a command and executes that command";
	}

	@Override
	public void read(Environment env) throws IOException {
		
		env.pushReader("symbol");
		env.pushCommand("read");
		env.evaluateStack();
		
		if (env.isStackEmpty() || !(env.peek() instanceof TSymbol))	{
			return;
		}
		
		String alias = ((TSymbol)env.pop()).getName();
		
		Object o = env.getBoundObject(alias);
		if (null == o)	{
			env.pushString("The symbol \"" + alias + "\" is not bound");
			env.pushCommand("error");
		}
		
		if (o instanceof ICommand)	{
			// An error will be pushed if alias doesn't refer to a command
			env.pushCommand(alias); 
		} else if (o instanceof TMacro)	{
			env.push(o);
			env.pushCommand("execute");
		} else {
			env.pushString("Don't know how to execute object of type <" + o.getClass().getName() + "> for symbol \"" + alias + "\"");
			env.pushCommand("error");
		}
		
		discardWhitespace(env);
		
		
	}
}
