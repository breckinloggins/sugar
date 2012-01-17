/**
 * 
 */
package com.breckinloggins.cx.command;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseCommand;

/**
 * Writes the contents of the current stack to stdout
 * @author bloggins
 */
public class Stack extends BaseCommand {

	@Override
	public String getDescription() {
		return "Writes the contents of the current stack to stdout";
	}

	@Override
	public void execute(Environment env) {
		env.printStack();
	}

}
