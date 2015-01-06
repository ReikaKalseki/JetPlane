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

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import Reika.JetPlane.PlaneInventory;

public abstract class EntityAircraft extends Entity {

	private float roll;
	private float pitch;
	private float yaw;
	private float throttle;

	private float heading;
	private float velocity;

	private final PlaneInventory inventory = new PlaneInventory(this, this.getInventorySize());
	private final ArrayList<EntityPlayer> riders = new ArrayList();

	public EntityAircraft(World par1World) {
		super(par1World);
	}

	public abstract int getInventorySize();

	public abstract int getMaxRiders();

	/** Maximum angle change per tick */
	public abstract float getAgility();

	public abstract float getStallSpeed();

	public abstract float getMaxSpeed();

	public abstract float getMaxClimbAngle();


	//---------------------------FLIGHT PHYSICS-----------------------------------------

	public final void setRotation(float roll, float pitch, float yaw) {
		this.roll = roll;
		this.pitch = pitch;
		this.yaw = yaw;
	}

	public final float getRoll() {
		float roll = dataWatcher.getWatchableObjectFloat(29);
		this.roll = roll;
		return roll;
	}

	public final float getPitch() {
		float pitch = dataWatcher.getWatchableObjectFloat(30);
		this.pitch = pitch;
		return pitch;
	}

	public final float getYaw() {
		float yaw = dataWatcher.getWatchableObjectFloat(31);
		this.yaw = yaw;
		return yaw;
	}

	public final void pullUp() {
		if (pitch < this.getMaxClimbAngle())
			pitch += this.getAgility();
	}

	public final void noseDown() {
		pitch -= this.getAgility();
	}

	public final void rollLeft() {
		roll -= this.getAgility();
	}

	public final void rollRight() {
		roll += this.getAgility();
	}

	public final void yawLeft() {
		yaw -= this.getAgility();
	}

	public final void yawRight() {
		yaw += this.getAgility();
	}

	public final void decreaseThrottle() {
		throttle -= 0.1;
		if (throttle < 0)
			throttle = 0;
	}

	public final void increaseThrottle() {
		throttle += 0.1;
		if (throttle > 1)
			throttle = 1;
	}

	public final float getGroundSpeed() {
		return velocity*(float)Math.cos(Math.toRadians(pitch));
	}

	public final float getMotionX() {
		return this.getGroundSpeed()*(float)Math.sin(Math.toRadians(heading));
	}

	public final float getMotionY() {
		return velocity*(float)Math.sin(Math.toRadians(pitch));
	}

	public final float getMotionZ() {
		return this.getGroundSpeed()*(float)Math.cos(Math.toRadians(180-heading));
	}

	private void applyAcceleration() {
		velocity *= 0.99;
		velocity += throttle*0.0625F;
		if (velocity > this.getMaxSpeed())
			velocity = this.getMaxSpeed();
	}

	private void updateAngles() {
		if (velocity < this.getStallSpeed()) {
			if (pitch > -70) { //nosedive
				pitch--;
			}
		}
		yaw -= 4*Math.sin(Math.toRadians(roll));

		heading = -yaw;

		dataWatcher.updateObject(29, roll);
		dataWatcher.updateObject(30, pitch);
		dataWatcher.updateObject(31, yaw);
	}

	private void readKeyboardInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_PRIOR)) {
			this.increaseThrottle();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NEXT)) {
			this.decreaseThrottle();
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.noseDown();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.pullUp();
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.rollLeft();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.rollRight();
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_GRAVE)) {
			this.setDead();
		}

		this.extraKeyTests();
	}

	protected void extraKeyTests() {

	}

	public void crash() {

	}


	//---------------------------STANDARD ENTITY CODE-----------------------------------------

	@Override
	protected void entityInit() {
		dataWatcher.addObject(29, 0.0F);
		dataWatcher.addObject(30, 0.0F);
		dataWatcher.addObject(31, 0.0F);
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if (!worldObj.isRemote) {
			this.readKeyboardInput();
			this.applyAcceleration();
			this.updateAngles();

			motionX = this.getMotionX();
			motionY = this.getMotionY();
			motionZ = this.getMotionZ();
			velocityChanged = true;
		}
		this.moveEntity(motionX, motionY, motionZ);

		for (int i = 0; i < riders.size(); i++) {
			EntityPlayer ep = riders.get(i);
			ep.rotationYaw = 180-this.getYaw();
			ep.rotationPitch = 30-this.getPitch();
		}

		//ReikaJavaLibrary.pConsole(riders, Side.SERVER);

		//ReikaJavaLibrary.pConsole(velocity+":"+roll+":"+pitch+":"+yaw, Side.SERVER);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		roll = nbt.getFloat("aroll");
		pitch = nbt.getFloat("apitch");
		yaw = nbt.getFloat("ayaw");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setFloat("aroll", roll);
		nbt.setFloat("apitch", pitch);
		nbt.setFloat("ayaw", yaw);
	}

	@Override
	public final boolean interactFirst(EntityPlayer ep)
	{
		if (riders.size() < this.getMaxRiders() && !this.isPlayerRiding(ep)) {
			ep.mountEntity(this);
			riders.add(ep);
		}
		return false;
	}

	@Override
	public final boolean canBeCollidedWith()
	{
		return true;
	}

	@Override
	public final String getCommandSenderName()
	{
		return super.getCommandSenderName();
	}

	public boolean isPlayerRiding(EntityPlayer ep) {
		return riders.contains(ep);
	}


	//---------------------------RIDING CODE OVERRIDES-----------------------------------------

	@Override
	protected void fall(float par1)
	{

	}

	@Override
	public void applyEntityCollision(Entity entity)
	{
		boolean flag = entity instanceof EntityPlayer;
		if (flag) {
			flag = this.isPlayerRiding((EntityPlayer)entity);
		}

		if (!flag) {
			super.applyEntityCollision(entity);
		}
	}

}
