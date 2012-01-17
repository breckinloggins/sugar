/**
 * 
 */
package com.breckinloggins.cx.dictionary;

import com.breckinloggins.cx.Environment;

/**
 * Represents a type
 * @author bloggins
 */
public interface IType extends IEntry {
	
	/**
	 * Evaluates this type to produce a value and pushes that value onto the stack
	 * @param env
	 */
	void Evaluate(Environment env);
}
