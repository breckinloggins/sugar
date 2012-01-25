/**
 * 
 */
package org.sugarlang.value;

import org.sugarlang.type.BuiltinTypes;
import org.sugarlang.type.TypeException;

/**
 * Represents a whitespace character
 * @author bloggins
 *
 */
public class VWhitespace extends BaseValue {
	
	private char _c;
	
	/**
	 * Constructs a new Whitespace value.
	 * @param c The character to treat as whitespace
	 * @throws TypeException
	 */
	public VWhitespace(char c) throws TypeException	{
		
		setType(BuiltinTypes.Whitespace);
		
		_c = c;
		
		seal();
	}
	
	/**
	 * Do not use this method.  Whitespace values cannot be cloned
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException("Whitespace cannot be cloned");
	}

	/**
	 * Whitespace is always equal to all other whitespace
	 */
	@Override
	public boolean equals(Object obj) {
		return null != obj && 
				obj instanceof VWhitespace;
	}

	/**
	 * Compures the hash code
	 */
	@Override
	public int hashCode() {
		return "VWhitespace".hashCode();
	}

	/**
	 * Gets the string value of this VWhitespace
	 */
	@Override
	public String toString() {
		String strCh = Character.toString(_c);
		if ('\t' == _c)	{
			strCh = "\\t";
		} else if ('\n' == _c)	{
			strCh = "\\n";
		} else if ('\r' == _c)	{
			strCh = "\\r";
		}
		
		return "<whitespace: '"+ strCh + "'>";
	}

}
