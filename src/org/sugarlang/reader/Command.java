package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseReader;
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
		
		// An error will be pushed if alias doesn't refer to a command
		env.pushCommand(alias); 
		
		discardWhitespace(env);
		
		
	}
}
