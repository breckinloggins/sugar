/**
 * 
 */
package org.sugarlang.type;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseType;

/**
 * Quotes an item to prevent it from being immediately evaluated
 * @author bloggins
 */
public class TQuote extends BaseType {

	private Object _inner;
	
	/**
	 * Constructs a quoted object with the given inner object
	 * @param inner
	 */
	public TQuote(Object inner)	{
		_inner = inner;
	}
	
	/**
	 * Gets the quoted object
	 * @return The object quoted
	 */
	public Object getInner()	{
		return _inner;
	}
		
	@Override
	public String getName() {
		return "TQuote`"+getInner().toString()+" <" + getInner().getClass().getName() + ">`";
	}

	@Override
	public String getDescription() {
		return "Quotes an item to prevent it from being immediately evaluated";
	}
	
	/* (non-Javadoc)
	 * @see org.sugarlang.dictionary.IType#Evaluate(org.sugarlang.Environment)
	 */
	@Override
	public void Evaluate(Environment env) {
		// probably don't need this
	}

	@Override
	public String toString()	{
		return getName();
	}
}
