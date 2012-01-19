/**
 * 
 */
package org.sugarlang.type;

import org.sugarlang.Environment;
import org.sugarlang.dictionary.BaseType;

/**
 * A special meta-type that marks the stack but otherwise does nothing
 * @author bloggins
 */
public class TMark extends BaseType {
	
	@Override
	public String getDescription() {
		return "A special meta-type that marks the stack but otherwise does nothing";
	}

	/* (non-Javadoc)
	 * @see org.sugarlang.dictionary.IType#Evaluate(org.sugarlang.Environment)
	 */
	@Override
	public void Evaluate(Environment env) {
		// don't think we need this
	}

}
