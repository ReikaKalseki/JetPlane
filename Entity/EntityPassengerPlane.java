/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.JetPlane.Entity;

import net.minecraft.world.World;

public class EntityPassengerPlane extends EntityAircraft {

	public EntityPassengerPlane(World par1World) {
		super(par1World);
	}

	@Override
	public int getInventorySize() {
		return 54;
	}

	@Override
	public int getMaxRiders() {
		return 24;
	}

	@Override
	public float getAgility() {
		return 0.5F;
	}

	@Override
	public float getStallSpeed() {
		return 0;
	}

	@Override
	public float getMaxSpeed() {
		return 3;
	}

	@Override
	public float getMaxClimbAngle() {
		return 30;
	}

}
