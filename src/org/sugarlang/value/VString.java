/**
 * 
 */
package org.sugarlang.value;

import org.sugarlang.type.BuiltinTypes;
import org.sugarlang.type.TypeException;

/**
 * @author bloggins
 *
 */
public class VString extends BaseValue {

	private String _val;
	
	/**
	 * Constructs a VString with a given String value
	 * @param val The String to use
	 * @throws TypeException Thrown if null is passed
	 */
	public VString(String val) throws TypeException	{
		setType(BuiltinTypes.String);
		
		if (null == val)	{
			throw new TypeException("Cannot create a null VString");
		}

		_val = val;
		seal();
	}
	
	/**
	 * Creates a new VString copy
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		try {
			return new VString(_val);
		} catch (TypeException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Checks whether the given VString is equal to this VString
	 */
	@Override
	public boolean equals(Object obj) {
		return null != obj && 
				obj instanceof VString &&
				toString().equals(((VString)obj).toString());
	}

	/**
	 * Compures the hash code
	 */
	@Override
	public int hashCode() {
		return "VString".hashCode() + _val.hashCode();
	}

	/**
	 * Gets the string value of this VString
	 */
	@Override
	public String toString() {
		return _val;
	}

}
