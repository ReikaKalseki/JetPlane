/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.JetPlane;

import java.net.URL;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Base.DragonAPIMod;
import Reika.DragonAPI.Instantiable.IO.ModLogger;
import Reika.DragonAPI.Libraries.ReikaRegistryHelper;
import Reika.JetPlane.Registry.PlaneType;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;

@Mod( modid = "JetPlane", name="JetPlane", version="beta", certificateFingerprint = "@GET_FINGERPRINT@", dependencies="required-after:DragonAPI")
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class JetPlane extends DragonAPIMod {

	@Instance("JetPlane")
	public static JetPlane instance = new JetPlane();

	public static ModLogger logger;

	@SidedProxy(clientSide="Reika.JetPlane.JetClient", serverSide="Reika.JetPlane.JetCommon")
	public static JetCommon proxy;

	@Override
	@EventHandler
	public void preload(FMLPreInitializationEvent evt) {

		logger = new ModLogger(instance, true, false, false);

		ReikaRegistryHelper.setupModData(instance, evt);
		ReikaRegistryHelper.setupVersionChecking(evt);
	}

	@Override
	@EventHandler
	public void load(FMLInitializationEvent event) {

		for (int i = 0; i < PlaneType.planeList.length; i++) {
			PlaneType p = PlaneType.planeList[i];
			int id = EntityRegistry.findGlobalUniqueEntityId();
			EntityRegistry.registerGlobalEntityID(p.entityClass, p.typeName, id);
			EntityRegistry.registerModEntity(p.entityClass, p.typeName, id, instance, 128, 20, true);
		}

		proxy.registerRenderers();
	}

	@Override
	@EventHandler
	public void postload(FMLPostInitializationEvent evt) {

	}

	@Override
	public String getDisplayName() {
		return "JetPlane";
	}

	@Override
	public String getModAuthorName() {
		return "Reika";
	}

	@Override
	public URL getDocumentationSite() {
		return DragonAPICore.getReikaForumPage(instance);
	}

	@Override
	public boolean hasWiki() {
		return false;
	}

	@Override
	public URL getWiki() {
		return null;
	}

	@Override
	public boolean hasVersion() {
		return false;
	}

	@Override
	public String getVersionName() {
		return null;
	}

	@Override
	public ModLogger getModLogger() {
		return logger;
	}

}
