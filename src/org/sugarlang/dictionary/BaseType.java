/**
 * 
 */
package org.sugarlang.dictionary;

/**
 * @author bloggins
 *
 */
public abstract class BaseType implements IType {

	/*
	 * @see com.breckinloggins.cx.dictionary.IEntry#getName()
	 */
	@Override
	public String getName() {
		return this.getClass().getName();
	}

	/* (non-Javadoc)
	 * @see com.breckinloggins.cx.dictionary.IEntry#getDescription()
	 */
	@Override
	public String getDescription() {
		return "(type)";
	}

}
