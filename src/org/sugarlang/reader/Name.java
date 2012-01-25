package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;
import org.sugarlang.value.VSymbol;


public class Name extends BaseReader {

	// TODO: REMOVEME.  The user will define what a "name" is in their world 
	
	public Name() throws TypeException {
		super();
	}

	@Override
	public String getDescription()	{
		return "Reads an identifier and pushes it onto the stack";
	}
	
	@Override
	public void readInternal(Environment env) throws IOException, TypeException {
		
		env.pushReader("symbol");
		env.pushOp("read");
		env.evaluateStack();
		
		if (!(env.peek() instanceof VSymbol))	{
			env.pushError("Argument on the stack isn't a symbol");
			return;
		}
		
		VSymbol sym = (VSymbol)env.pop();
				
		System.err.println("r(Name): " + sym.toString());
		env.push(sym);		
	}

}
