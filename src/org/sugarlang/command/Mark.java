/**
 * 
 */
package org.sugarlang.command;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseCommand;

/**
 * Pushes a stack marker on the stack
 * @author bloggins
 */
public class Mark extends BaseCommand {

	@Override
	public String getDescription() {
		return "Pushes a stack marker on the stack";
	}

	@Override
	public void execute(Environment env) {
		env.pushMark();
	}

	
}
