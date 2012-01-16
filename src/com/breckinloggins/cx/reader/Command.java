package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;
import com.breckinloggins.cx.dictionary.ICommand;
import com.breckinloggins.cx.dictionary.IReader;

/**
 * Reader that takes the name of a command and executes it
 * @author bloggins
 */
public class Command extends BaseReader {

	@Override
	public String getDescription() {
		return "Takes the name of a command and executes that command";
	}

	@Override
	public IReader read(StringReader sr, Environment env) throws IOException {
		Name nameReader = (Name)env.getReader("name");
		IReader next = nameReader.read(sr, env);
		
		if (next instanceof Error)	{
			return next;
		}
		
		String alias = env.pop().getName();
		ICommand cmd = env.getCommand(alias);
		
		getWriter().print("r(Command): ");
		if (null == cmd)	{
			env.pushString("There is no command by the name " + alias);
			return env.getReader("error");
		} else {
			getWriter().println(alias);
		}
		
		cmd.execute(env);
		
		return env.getReader("discriminator");
	}

}
