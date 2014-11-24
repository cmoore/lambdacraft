
package io.ivy.lambdacraft;

import net.canarymod.commandsys.CommandListener;
import net.canarymod.plugin.Plugin;
import net.canarymod.plugin.PluginListener;

import org.armedbear.lisp.*;


public class LambdaCraft extends Plugin implements PluginListener, CommandListener {

	private Interpreter interop;
	
	@Override
	public boolean enable() {
		// TODO Auto-generated method stub
		
		interop = Interpreter.createInstance();
		
		// Load the tools.
		interop.eval("(load \"tools.lisp\")");
		
		return true;
	}
	
	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}

}
