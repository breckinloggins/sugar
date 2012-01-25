/**
 * 
 */
package org.sugarlang.type;

import org.sugarlang.base.IType;

/**
 * @author bloggins
 *
 */
public abstract class BaseType implements IType {
	
	private boolean _isSealed = false;
	private IType _type;
	
	@Override
	public IType getType() {
		if (null == _type)	{
			_type = BuiltinTypes.Type;
		}
		return _type;
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
