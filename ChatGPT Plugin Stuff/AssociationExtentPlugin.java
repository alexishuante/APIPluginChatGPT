package org.tzi.use.gui.plugins;

import org.tzi.use.runtime.IPluginRuntime;
import org.tzi.use.runtime.impl.Plugin;
import org.json.JSONObject;

/**
 * This is the main class of the Association Extent Plugin.
 * 
 * @author Roman Asendorf
 */
public class AssociationExtentPlugin extends Plugin {

	final protected String PLUGIN_ID = "useAssociationExtendPlugin";

	public String getName() {
		return this.PLUGIN_ID;
	}

	public void run(IPluginRuntime pluginRuntime) throws Exception {
		// Nothing to initialize
	}
}
