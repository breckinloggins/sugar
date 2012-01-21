/**
 * 
 */
package org.sugarlang.dictionary;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;

/**
 * @author bloggins
 *
 */
public abstract class BaseOp implements IOp {

	private boolean _isSealed = false;
	
	@Override
	public IType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void seal() throws TypeException {
		throwIfSealed();
		_isSealed = true;
	}

	@Override
	public boolean isSealed() {
		return _isSealed;
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
		System.err.println("WARNING: The opcode \"" + getClass().getName() + "\" has nothing to do");
	}
	
	/**
	 * Throws a TypeException if the object is sealed
	 * @throws TypeException Thrown if object is sealed
	 */
	protected void throwIfSealed() throws TypeException	{
		if (_isSealed)	{
			throw new TypeException("Invalid operation; object is sealed");
		}
	}
}
