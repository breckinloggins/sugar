/**
 * 
 */
package org.sugarlang.type;


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
