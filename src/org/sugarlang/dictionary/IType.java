/**
 * 
 */
package org.sugarlang.dictionary;

import org.sugarlang.Environment;

/**
 * Represents a type
 * @author bloggins
 */
public interface IType extends IEntry {
	
	/**
	 * Evaluates this type to produce a value and pushes that value onto the stack
	 * @param env
	 * TODO: We need to get rid of this
	 */
	void Evaluate(Environment env);
}
