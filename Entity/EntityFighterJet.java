/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.JetPlane.Entity;

import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import Reika.JetPlane.PlaneInventory;

public class EntityFighterJet extends EntityAircraft {

	private PlaneInventory ammo = new PlaneInventory(this, 8);

	public EntityFighterJet(World par1World) {
		super(par1World);
	}

	@Override
	protected void extraKeyTests() {
		super.extraKeyTests();

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if (this.hasAmmunition())
				this.fire();
		}
	}

	private void fire() {

	}

	private boolean hasAmmunition() {
		return false;
	}

	@Override
	public int getInventorySize() {
		return 4;
	}

	@Override
	public int getMaxRiders() {
		return 1;
	}

	@Override
	public float getAgility() {
		return 2F;
	}

	@Override
	public float getStallSpeed() {
		return 0;
	}

	@Override
	public float getMaxSpeed() {
		return 5;
	}

	@Override
	public float getMaxClimbAngle() {
		return 90;
	}

}
