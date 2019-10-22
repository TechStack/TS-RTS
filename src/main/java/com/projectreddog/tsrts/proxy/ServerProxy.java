package com.projectreddog.tsrts.proxy;

import net.minecraft.world.World;

public class ServerProxy implements IProxy {

	@Override
	public World getClientWorld() {
		throw new IllegalStateException("Only Run this on the Client!");
	}

	@Override
	public void init() {

	}

}
