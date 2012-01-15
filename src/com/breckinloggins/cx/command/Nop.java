/**
 * 
 */
package com.breckinloggins.cx.command;

import com.breckinloggins.cx.Environment;

/**
 * @author bloggins
 *
 */
public class Nop extends BaseCommand {

	@Override
	public String getDescription() {
		return "Does nothing.  Useful when a command is expected but nothing needs to be done.";
	}

	@Override
	public void execute(Environment env) {
	}
}
