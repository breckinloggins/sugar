/**
 * 
 */
package org.sugarlang.type;

/**
 * @author bloggins
 *
 */
public class BuiltinTypes {
	public static TType Type;
	public static TChar Char;
	public static TMacro Macro;
	public static TNull Null;
	public static TOp Op;
	public static TQuote Quote;
	public static TReader Reader;
	public static TString String;
	public static TSymbol Symbol;
	
	/**
	 * Initializes the static builtin types class
	 * @throws TypeException
	 */
	public static void initialize() throws TypeException	{
		BuiltinTypes.Type = new TType();
		BuiltinTypes.Char = new TChar();
		BuiltinTypes.Macro = new TMacro();
		BuiltinTypes.Null = new TNull();
		BuiltinTypes.Op = new TOp();
		BuiltinTypes.Quote = new TQuote();
		BuiltinTypes.Reader = new TReader();
		BuiltinTypes.String = new TString();
		BuiltinTypes.Symbol = new TSymbol();
	}
}
