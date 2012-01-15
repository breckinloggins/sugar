package com.breckinloggins.cx.reader;

import java.io.IOException;
import java.io.StringReader;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.command.ICommand;

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
		Name nameReader = (Name)env.createReader("name");
		IReader next = nameReader.read(sr, env);
		
		if (next instanceof Error)	{
			return next;
		}
		
		String alias = env.pop();
		ICommand cmd = env.createCommand(alias);
		
		getWriter().print("r(Command): ");
		if (null == cmd)	{
			getWriter().println("NOT FOUND FOR " + alias);
			Error e = (Error)env.createReader("error");
			e.setMessage("There is no command by the name " + alias);
			return e;
		} else {
			getWriter().println(alias);
		}
		
		cmd.execute(env);
		
		return env.createReader("discriminator");
	}

}
