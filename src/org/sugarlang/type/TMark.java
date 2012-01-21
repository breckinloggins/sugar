/**
 * 
 */
package org.sugarlang.type;

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
}
