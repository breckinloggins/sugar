/**
 * 
 */
package org.sugarlang.op;

import org.sugarlang.Environment;
import org.sugarlang.type.TypeException;

/**
 * Creates a new dictionary and pushes it as the current dictionary
 * @author bloggins
 *
 */
public class PushDictionary extends BaseOp {

	public PushDictionary() throws TypeException {
		super();
	}

	@Override
	public String getDescription() {
		return "Creates a new dictionary and pushes it as the current dictionary";
	}

	/* (non-Javadoc)
	 * @see org.sugarlang.op.BaseOp#executeInternal(org.sugarlang.Environment)
	 */
	@Override
	protected void executeInternal(Environment env) throws Exception {
		env.pushDictionary();
	}

}
