/**
 * 
 */
package org.sugarlang.value;

import org.sugarlang.type.BuiltinTypes;
import org.sugarlang.type.TypeException;

/**
 * Represents symbol values
 * @author bloggins
 */
public class VSymbol extends BaseValue {

	private String _val;
	
	/**
	 * Creates a new symbol with the given value
	 * @param val The value for which to create the value
	 * @throws TypeException Thrown if val is null
	 */
	public VSymbol(String val) throws TypeException {
		setType(BuiltinTypes.Symbol);
		
		if (null == val)	{
			throw new TypeException("Cannot create a null VSymbol");
		}
		
		_val = val;
		seal();
	}
	
	/**
	 * Creates a new VSymbol 
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		try {
			return new VSymbol(_val);
		} catch (TypeException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Checks whether the given symbol is equal to this one
	 */
	@Override
	public boolean equals(Object obj) {
		return null != obj && 
				obj instanceof VSymbol &&
				toString().equals(((VSymbol)obj).toString());
	}

	/**
	 * Calculates the hash for this symbol
	 */
	@Override
	public int hashCode() {
		return "VSymbol".hashCode()+_val.hashCode();
	}

	/**
	 * Gets this symbol's string representation
	 */
	@Override
	public String toString() {
		return _val;
	}

}
