
package io.ivy.lambdacraft;

import java.io.InputStreamReader;

import javax.script.*;

import net.canarymod.plugin.Plugin;
import net.canarymod.plugin.PluginListener;
import net.canarymod.tasks.ServerTask;
import net.canarymod.tasks.TaskOwner;
import net.canarymod.commandsys.*;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.Canary;
import net.canarymod.api.inventory.recipes.CraftingRecipe;
import net.canarymod.api.inventory.recipes.RecipeRow;
import net.canarymod.api.inventory.Item;
import net.canarymod.hook.Dispatcher;
import net.canarymod.plugin.PluginListener;
import net.canarymod.hook.Hook;

import org.armedbear.lisp.*;
import org.armedbear.lisp.scripting.*;

public class LambdaCraft extends Plugin implements PluginListener, CommandListener {

    private ScriptEngine lisp_engine;
    
    @Override
    public boolean enable() {
        // TODO Auto-generated method stub

        getLogman().info("Attempting to register the interpreter.");
        getLogman().info("This normally takes a few seconds.");


        ScriptEngineManager script_manager = new ScriptEngineManager();
        script_manager.registerEngineExtension("lisp", new AbclScriptEngineFactory());
        lisp_engine = script_manager.getEngineByExtension("lisp");

        if (lisp_engine != null) {
            getLogman().info("Engine created.");
        }
        try {
            Invocable inv = (Invocable) this.lisp_engine;
            InputStreamReader reader = new InputStreamReader(getClass()
                                                             .getClassLoader()
                                                             .getResourceAsStream("boot.lisp"));
            this.lisp_engine.eval(reader);
            
            getLogman().info(this.lisp_engine.eval("(banner)"));
            inv.invokeFunction("startup", this, lisp_engine, getClass().getClassLoader());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ScriptException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
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

    public static interface IDispatcher {
        public void execute(PluginListener listener, Hook hook);
    }
    
    public Dispatcher getDispatcher(final IDispatcher impl) {
        return new Dispatcher() {
            public void execute(PluginListener listener, Hook hook) {
                impl.execute(listener, hook);
            }
        };
    }

    public class LambdaCraftTask extends ServerTask {
        private Runnable runnable = null;
        public LambdaCraftTask(Runnable runnable, TaskOwner owner, long delay, boolean continuous) {
            super(owner, delay, continuous);
            this.runnable = runnable;
        }
        @Override
        public void run() {
            this.runnable.run();
        }
    }

    public ServerTask createServerTask(Runnable runnable, long delay, boolean continuous) {
        return new LambdaCraftTask(runnable, this, delay, continuous);
    }

    public void executeCommand( MessageReceiver sender, String[] args) {
        boolean result = false;
        String lisp_code = "";
        Object lisp_result = null;

        try {
            lisp_result = ((Invocable) this.lisp_engine).invokeFunction("command-line", sender, args);
        } catch (Exception se) {
            se.printStackTrace();
        }

        if (lisp_result != null) {
            sender.notice(lisp_result.toString());
            return;
        }
        return;
    }
    
    

    /* Commands */


    @Command( aliases = { "eval" },
              description = "Evaluate a lisp expression.",
              permissions = {""},
              toolTip = "/eval <lisp expression>",
              min = 1)
    public void evalCommand( MessageReceiver sender, String[] args) {
        executeCommand(sender, args);
    }
}

