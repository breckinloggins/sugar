package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseReader;
import org.sugarlang.dictionary.IReader;
import org.sugarlang.type.TSymbol;


public class Reader extends BaseReader {
	@Override
	public String getDescription()	{
		return "Manually invokes a reader by the given name (e.g. '#list')";
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
		IReader reader = env.getReader(alias);
		
		System.err.print("r(Reader): ");
		if (null == reader)	{
			env.pushString("There is no reader by the name " + alias);
			env.pushReader("error");
			env.pushCommand("read");
			return;
		} 
		
		discardWhitespace(env);
		
		env.pushReader(alias);
		env.pushCommand("read");
	}
}
