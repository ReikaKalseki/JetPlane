/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2016
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.JetPlane.Render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import Reika.JetPlane.Entity.EntityAircraft;

public abstract class RenderPlane extends Render {

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {

		EntityAircraft ea = (EntityAircraft)entity;
		//ReikaJavaLibrary.pConsole(ea.getRoll()+":"+ea.getPitch()+":"+ea.getYaw());
		GL11.glTranslated(d0, d1, d2);
		//GL11.glRotatef(ea.getYaw(), 0, 1, 0);
		//GL11.glRotatef(ea.getPitch(), 0, 0, 1);
		//GL11.glRotatef(ea.getRoll(), 1, 0, 0);

		float heading = ea.getYaw();
		GL11.glRotatef(heading, 0, 1, 0);
		GL11.glRotatef(ea.getPitch(), 1, 0, 0);
		GL11.glRotatef(-ea.getRoll(), 0, 0, 1);

		Tessellator v5 = Tessellator.instance;
		v5.startDrawingQuads();
		v5.setColorOpaque(255, 0, 0);
		v5.addVertex(-2, 0, 2);
		v5.addVertex(2, 0, 2);
		v5.addVertex(2, 0, -2);
		v5.addVertex(-2, 0, -2);
		v5.draw();

		GL11.glRotatef(ea.getRoll(), 0, 0, 1);
		GL11.glRotatef(-ea.getPitch(), 1, 0, 0);
		GL11.glRotatef(-heading, 0, 1, 0);

		//GL11.glRotatef(-ea.getRoll(), 1, 0, 0);
		//GL11.glRotatef(-ea.getPitch(), 0, 0, 1);
		//GL11.glRotatef(-ea.getYaw(), 0, 1, 0);

		GL11.glTranslated(-d0, -d1, -d2);

	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}
