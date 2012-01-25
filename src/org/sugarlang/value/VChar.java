/**
 * 
 */
package org.sugarlang.value;

import org.sugarlang.type.TChar;
import org.sugarlang.type.TypeException;

/**
 * Represents a unicode character (or EOF)
 * @author bloggins
 */
public class VChar extends BaseValue {
	
	private int _c;
	
	public VChar(int c) throws TypeException	{
		
		setType(new TChar());
		
		_c = c;
		seal();
	}
	
	/**
	 * Gets the character
	 * @return The character
	 */
	public int getChar()	{
		return _c;
	}
	
	/**
	 * Creates a new VChar copy
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		try {
			return new VChar(_c);
		} catch (TypeException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Checks whether the given VChar is equal to this VChar
	 */
	@Override
	public boolean equals(Object obj) {
		return null != obj && 
				obj instanceof VChar &&
				_c == ((VChar)obj).getChar();
	}

	/**
	 * Compures the hash code
	 */
	@Override
	public int hashCode() {
		return "VChar".hashCode() + _c;
	}

	/**
	 * Gets the string value of this VChar
	 */
	@Override
	public String toString() {
		if (_c == 0)	{
			return "\0";
		}
		
		return Character.toString((char)_c);
	}

}
