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

import net.minecraft.world.World;
import Reika.JetPlane.Registry.PlaneType;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class JetClient extends JetCommon {

	@Override
	public void registerSounds() {

	}

	@Override
	public void registerRenderers() {
		for (int i = 0; i < PlaneType.planeList.length; i++) {
			PlaneType p = PlaneType.planeList[i];
			RenderingRegistry.registerEntityRenderingHandler(p.entityClass, p.getRenderer());
		}
	}

	// Override any other methods that need to be handled differently client side.

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}

}
