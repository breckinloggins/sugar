/**
 * 
 */
package org.sugarlang.reader;

import java.io.IOException;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseReader;

/**
 * Reads the next item (up to whitespace) and pushes it on the stack in quoted form
 * @author bloggins
 */
public class Quoted extends BaseReader {

	@Override
	public String getDescription() {
		return "Reads the next item (up to whitespace) and pushes it on the stack in quoted form";
	}

	@Override
	public void read(Environment env) throws IOException {
		// TODO: This could be much more featureful if combined with a new environment
		
		env.pushReader("discriminator");
		env.pushCommand("read");
		env.evaluateStack();
		
		// If we have something on the top of the stack, quote it
		if (!env.isStackEmpty())	{
			env.pushCommand("quote");
		}
	}

}
