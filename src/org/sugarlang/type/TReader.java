/**
 * 
 */
package org.sugarlang.type;

/**
 * Syntax reader
 * @author bloggins
 */
public class TReader extends BaseType {
	
	public TReader() throws TypeException	{
		seal();
	}
	
	@Override
	public String getDescription() {
		return "Syntax reader";
	}
	
	@Override
	public String toString()	{
		return "Reader";
	}
}
