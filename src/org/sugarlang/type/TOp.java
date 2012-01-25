/**
 * 
 */
package org.sugarlang.type;

/**
 * A virtual machine operation
 * @author bloggins
 *
 */
public class TOp extends BaseType {
	public TOp() throws TypeException	{
		seal();
	}
	
	@Override
	public String getDescription() {
		return "A virtual machine operation";
	}

	@Override
	public String toString() {
		return "Op";
	}

}
