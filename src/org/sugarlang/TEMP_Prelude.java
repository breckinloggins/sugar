package org.sugarlang;

/**
 * A temporary set of code that is run in the interpreter on startup
 * @author bloggins
 *
 */
public class TEMP_Prelude {
	
	public static String normalCode = 
	"!`"+
	"#\n"+
	"`symbol\n"+
	"`!reader\n"+
	"`!read\n"+
	"`!reader\n"+
	"`!read\n"+
	"!createmacro\n"+
	"!set\n";
	
	//public static String code = "";
	public static String code = normalCode;
}
