package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseReader;
import org.sugarlang.dictionary.ICommand;
import org.sugarlang.type.TSymbol;


/**
 * Reader that takes the name of a command and executes it
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
		ICommand cmd = env.getCommand(alias);
		
		System.err.print("r(Command): ");
		if (null == cmd)	{
			env.pushString("There is no command by the name " + alias);
			env.pushReader("error");
			env.pushCommand("read");
			return;
		} 
		
		discardWhitespace(env);
		
		env.pushCommand(alias);
	}
}
