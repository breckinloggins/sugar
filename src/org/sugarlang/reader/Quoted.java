/**
 * 
 */
package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;

/**
 * Reads the next item (up to whitespace) and pushes it on the stack in quoted form
 * @author bloggins
 */
public class Quoted extends BaseReader {
	
	public Quoted() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Reads the next item (up to whitespace) and pushes it on the stack in quoted form";
	}

	@Override
	public void readInternal(Environment env) throws IOException, TypeException {
		// TODO: This could be much more featureful if combined with a new environment
		
		env.pushReader("symbol");
		env.pushOp("read");
		env.evaluateStack();
		
		// If we have something on the top of the stack, quote it
		if (!env.isStackEmpty())	{
			env.pushOp("quote");
		}
	}

}
