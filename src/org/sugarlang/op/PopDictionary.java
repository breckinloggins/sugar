/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;

/**
 * Pops the current dictionary and restores the parent dictionary
 * @author bloggins
 *
 */
public class PopDictionary extends BaseOp {

	public PopDictionary() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Pops the current dictionary and restores the parent dictionary";
	}

	/* (non-Javadoc)
	 * @see org.sugarlang.op.BaseOp#executeInternal(org.sugarlang.Environment)
	 */
	@Override
	protected void executeInternal(Environment env) throws Exception {
		env.popDictionary();
	}

}
