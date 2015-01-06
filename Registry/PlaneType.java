/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.JetPlane.Registry;

import java.util.HashMap;

import Reika.JetPlane.JetPlane;
import Reika.JetPlane.Entity.EntityAircraft;
import Reika.JetPlane.Entity.EntityCargoPlane;
import Reika.JetPlane.Entity.EntityFighterJet;
import Reika.JetPlane.Entity.EntityPassengerPlane;
import Reika.JetPlane.Render.RenderPlane;

public enum PlaneType {

	CARGO("Cargo Aircraft", EntityCargoPlane.class, "RenderCargoPlane"),
	PASSENGER("Passenger Jet", EntityPassengerPlane.class, "RenderPassengerPlane"),
	FIGHTER("Fighter Jet", EntityFighterJet.class, "RenderFighterJet");

	public final Class entityClass;
	public final String typeName;
	public final String renderClass;

	private static final HashMap<PlaneType, RenderPlane> renderMap = new HashMap();

	public static final PlaneType[] planeList = values();

	private PlaneType(String name, Class<? extends EntityAircraft> c, String render) {
		entityClass = c;
		typeName = name;
		renderClass = render;
	}

	public RenderPlane getRenderer() {
		RenderPlane r = renderMap.get(this);
		if (r == null) {
			r = this.createRenderer();
			renderMap.put(this, r);
		}
		return r;
	}

	private RenderPlane createRenderer() {
		try {
			Class c = Class.forName("Reika.JetPlane.Render."+renderClass);
			return (RenderPlane)c.newInstance();
		}
		catch (Exception e) {
			JetPlane.logger.logError("Could not create render for "+this);
			e.printStackTrace();
			return null;
		}
	}

}
