/**
 * 
 */
package org.sugarlang.value;

import org.sugarlang.base.IValue;
import org.sugarlang.type.BuiltinTypes;
import org.sugarlang.type.TypeException;

/**
 * A quoted value
 * @author bloggins
 */
public class VQuote extends BaseValue {
	
	private IValue _inner;
	
	public VQuote(IValue inner) throws TypeException	{
		setType(BuiltinTypes.Quote);
		
		if (null == inner)	{
			throw new TypeException("Cannot quote null.  Perhaps you meant TNull?");
		}
		
		_inner = inner;
		seal();
	}
	
	/**
	 * Gets the quoted value
	 * @return The value quoted
	 */
	public IValue getInner()	{
		return _inner;
	}
	
	/**
	 * Creates a new VQuote copy
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		try {
			return new VQuote(_inner);
		} catch (TypeException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Checks whether the given VQuote is equal to this VQuote
	 */
	@Override
	public boolean equals(Object obj) {
		return null != obj && 
				obj instanceof VQuote &&
				_inner.equals(((VQuote)obj).getInner());
	}

	/**
	 * Computes the hash code
	 */
	@Override
	public int hashCode() {
		return "VQuote".hashCode() + _inner.hashCode();
	}
	
	@Override
	public String toString()	{
		return "Quote`"+getInner().toString()+" <" + getInner().getType().toString() + ">`";
	}
}
