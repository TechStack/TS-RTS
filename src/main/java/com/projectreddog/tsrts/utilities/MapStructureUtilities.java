package com.projectreddog.tsrts.utilities;

import com.projectreddog.tsrts.TSRTS;
import com.projectreddog.tsrts.utilities.data.MapStructureData;

import net.minecraft.util.math.BlockPos;

public class MapStructureUtilities {
	private static boolean changed = true;

	public static void Remove(BlockPos pos) {
		TSRTS.Structures.remove(pos);
		markChanged();
	}

	public static void Add(BlockPos pos, MapStructureData msd) {
		TSRTS.Structures.put(pos, msd);
		markChanged();
	}

	public static boolean isChanged() {
		return changed;
	}

	public static void markChanged() {
		changed = true;
	}

	public static void markUnchanged() {
		changed = false;
	}
}
