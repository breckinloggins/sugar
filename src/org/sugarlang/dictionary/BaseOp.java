/**
 * 
 */
package org.sugarlang.dictionary;

import org.sugarlang.Environment;

/**
 * @author bloggins
 *
 */
public abstract class BaseOp implements IEntry, IOp {

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
		return "(opcode)";
	}

	/*
	 * @see com.breckinloggins.cx.command.IOp#execute(com.breckinloggins.cx.Environment)
	 */
	@Override
	public void execute(Environment env) {
		System.err.println("WARNING: The opcode \"" + getName() + "\" has nothing to do");
	}
}
