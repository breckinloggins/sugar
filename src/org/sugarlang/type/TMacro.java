/**
 * 
 */
package org.sugarlang.type;

/**
 * Type representing a stack of quoted instructions, the unquoted form of which can be 
 * placed on the interpreter's stack and evaluated
 * @author bloggins 
 */
public class TMacro extends BaseType {

	public TMacro() throws TypeException	{
		seal();
	}
	
	@Override
	public String getDescription() {
		return "Type representing a stack of quoted instructions, the unquoted form of which can be placed on the interpreter's stack and evaluated";
	}

	@Override
	public String toString() {
		return "Macro";
	}

}
