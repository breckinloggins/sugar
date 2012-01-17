package com.breckinloggins.cx.reader;

import java.io.IOException;

import com.breckinloggins.cx.Environment;
import com.breckinloggins.cx.dictionary.BaseReader;
import com.breckinloggins.cx.dictionary.ICommand;

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
	public void read(Environment env) throws IOException {
		Name nameReader = (Name)env.getReader("name");
		nameReader.read(env);
		
		String alias = env.pop().toString();
		ICommand cmd = env.getCommand(alias);
		
		System.err.print("r(Command): ");
		if (null == cmd)	{
			env.pushString("There is no command by the name " + alias);
			env.pushReader("error");
			env.pushCommand("read");
			return;
		} else {
			System.err.println(alias);
		}
		
		cmd.execute(env);
		
		env.pushReader("discriminator");
		env.pushCommand("read");
	}
}
