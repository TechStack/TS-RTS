package com.projectreddog.tsrts.utilities;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import net.minecraft.state.EnumProperty;

public class TeamProperty extends EnumProperty<TeamEnum> {

	protected TeamProperty(String name, Collection<TeamEnum> values) {
		super(name, TeamEnum.class, values);
	}

	/**
	 * Create a new PropertyDirection with all directions that match the given Predicate
	 */
	public static TeamProperty create(String name, Predicate<TeamEnum> filter) {
		return create(name, Arrays.stream(TeamEnum.values()).filter(filter).collect(Collectors.toList()));
	}

	public static TeamProperty create(String p_196962_0_, TeamEnum... p_196962_1_) {
		return create(p_196962_0_, Lists.newArrayList(p_196962_1_));
	}

	/**
	 * Create a new PropertyDirection for the given direction values
	 */
	public static TeamProperty create(String name, Collection<TeamEnum> values) {
		return new TeamProperty(name, values);
	}

	public static TeamEnum getTeamForName(String name) {
		if (TeamEnum.BLUE.getName().equals(name)) {
			return TeamEnum.BLUE;
		} else if (TeamEnum.GREEN.getName().equals(name)) {
			return TeamEnum.GREEN;
		} else if (TeamEnum.RED.getName().equals(name)) {
			return TeamEnum.RED;
		} else if (TeamEnum.YELLOW.getName().equals(name)) {
			return TeamEnum.YELLOW;
		} else {
			return TeamEnum.BLUE;
		}
	}

}
