/**
 * 
 */
package org.sugarlang.dictionary;

import org.sugarlang.Environment;

/**
 * @author bloggins
 *
 */
public abstract class BaseCommand implements IEntry, ICommand {

	/*
	 * @see com.breckinloggins.cx.IEntry#getName()
	 */
	@Override
	public String getName() {
		return this.getClass().getName();
	}

	/*
	 * @see com.breckinloggins.cx.IEntry#getDescription()
	 */
	@Override
	public String getDescription() {
		return "(command)";
	}

	/*
	 * @see com.breckinloggins.cx.command.ICommand#execute(com.breckinloggins.cx.Environment)
	 */
	@Override
	public void execute(Environment env) {
		System.err.println("WARNING: The command \"" + getName() + "\" has nothing to do");
	}
}
