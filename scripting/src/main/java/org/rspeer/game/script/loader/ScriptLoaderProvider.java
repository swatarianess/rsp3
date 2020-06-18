package org.rspeer.game.script.loader;

import org.rspeer.commons.Configuration;
import org.rspeer.game.script.loader.local.LocalScriptLoader;

public class ScriptLoaderProvider {

    public ScriptProvider getLoader() {
        // tood add remote loader
        return new LocalScriptLoader(Configuration.Paths.SCRIPTS_LOCATION);
    }

}
