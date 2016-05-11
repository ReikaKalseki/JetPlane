/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.JetPlane.Entity;

import net.minecraft.world.World;

public class EntityCargoPlane extends EntityAircraft {

	public EntityCargoPlane(World par1World) {
		super(par1World);
	}

	@Override
	public int getInventorySize() {
		return 324;
	}

	@Override
	public int getMaxRiders() {
		return 2;
	}

	@Override
	public float getAgility() {
		return 0.25F;
	}

	@Override
	public float getStallSpeed() {
		return 0;
	}

	@Override
	public float getMaxSpeed() {
		return 2;
	}

	@Override
	public float getMaxClimbAngle() {
		return 30;
	}

}
