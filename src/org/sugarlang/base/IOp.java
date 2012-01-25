/**
 * 
 */
package org.sugarlang.base;

import org.sugarlang.Environment;

/**
 * @author bloggins
 * Represents a built-in operation that can be performed.  Ops always represent side-effects and
 * are purely stack based.  That is, ops don't have named arguments, although op READERS
 * often implement this semantic.  Stack arguments are always on the given environment's stack.
 * 
 * Note to implementors: all op classes should be stateless.  Any state must be stored in the environment.
 */
public interface IOp extends IValue {
	
	/**
	 * Executes the op within the current environment
	 * @param env The environment in which to execute the op.  The environment's stack
	 * may be affected by this op
	 */
	void execute(Environment env);
}
