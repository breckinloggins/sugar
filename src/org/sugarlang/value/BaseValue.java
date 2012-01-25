/**
 * 
 */
package org.sugarlang.value;

import org.sugarlang.base.IType;
import org.sugarlang.base.IValue;
import org.sugarlang.type.TypeException;

/**
 * Base class for all typed values
 * @author bloggins
 */
public class BaseValue implements IValue {

	private boolean _isSealed = false;
	
	private IType _type = null;
	
	/* (non-Javadoc)
	 * @see org.sugarlang.base.ISealable#seal()
	 */
	@Override
	public void seal() throws TypeException {
		throwIfSealed();
		
		if (null == _type)	{
			throw new TypeException("Value object cannot be sealed without a type");
		}
		
		_isSealed = true;
	}

	/* (non-Javadoc)
	 * @see org.sugarlang.base.ISealable#isSealed()
	 */
	@Override
	public boolean isSealed() {
		return _isSealed;
	}

	/* (non-Javadoc)
	 * @see org.sugarlang.base.IValue#getType()
	 */
	@Override
	public IType getType() {
		return _type;
	}
	
	/**
	 * Sets the type for this value
	 * @param type The type to set
	 * @throws TypeException Thrown if this value has already been sealed
	 */
	protected void setType(IType type) throws TypeException	{
		throwIfSealed();
		_type = type;
	}

	/* (non-Javadoc)
	 * @see org.sugarlang.base.IValue#getDescription()
	 */
	@Override
	public String getDescription() {
		return toString() + " <" + getType().getDescription() + ">";
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
