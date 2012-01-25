/**
 * 
 */
package org.sugarlang.type;


/**
 * A special meta-type that marks the stack but otherwise does nothing
 * @author bloggins
 */
public class TMark extends BaseType {
	public TMark() throws TypeException	{
		seal();
	}
	
	@Override
	public String getDescription() {
		return "A special meta-type that marks the stack but otherwise does nothing";
	}
}
