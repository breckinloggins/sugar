/**
 * 
 */
package org.sugarlang.dictionary;

import org.sugarlang.type.TypeException;

/**
 * @author bloggins
 *
 */
public abstract class BaseType implements IType {
	
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

	/* (non-Javadoc)
	 * @see com.breckinloggins.cx.dictionary.IEntry#getDescription()
	 */
	@Override
	public String getDescription() {
		return "(type)";
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
