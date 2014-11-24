
package io.ivy.lambdacraft;

import javax.script.*;

import net.canarymod.Canary;

import net.canarymod.commandsys.CommandListener;
import net.canarymod.plugin.Plugin;
import net.canarymod.plugin.PluginListener;

import net.canarymod.commandsys.Command;
import net.canarymod.commandsys.CommandListener;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.commandsys.CommandDependencyException;

import org.armedbear.lisp.*;
import org.armedbear.lisp.scripting.*;

public class LambdaCraft extends Plugin implements PluginListener, CommandListener {

    private ScriptEngine lisp_engine;
    
    @Override
    public boolean enable() {
        // TODO Auto-generated method stub

        getLogman().info("Attempting to register the interpreter.");


        ScriptEngineManager script_manager = new ScriptEngineManager();
        script_manager.registerEngineExtension("lisp", new AbclScriptEngineFactory());
        lisp_engine = script_manager.getEngineByExtension("lisp");

        if (lisp_engine != null) {
            getLogman().info("Looks good so far...");
        }
        getLogman().info(lisp_engine.get("*package*"));
        
        try {
            Canary.commands().registerCommands(this, this, false);
        } catch(CommandDependencyException e) {
            Canary.log.info(e);
        }
        
        return true;
    }
	
    @Override
    public void disable() {
        // TODO Auto-generated method stub
    }



    /* Commands */


    @Command( aliases = { "eval" },
              description = "Evaluate a lisp expression.",
              permissions = {""},
              toolTip = "/eval <lisp expression>",
              min = 1)
    public void evalCommand( MessageReceiver sender, String[] args) {
        String player_name = sender.getName();

        LispObject response = interop.eval(args[1]);

        sender.notice(response.toString());
    }
}

