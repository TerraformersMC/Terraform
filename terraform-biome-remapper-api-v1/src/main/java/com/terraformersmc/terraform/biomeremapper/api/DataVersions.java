package com.terraformersmc.terraform.biomeremapper.api;

/**
 * Data versions are used to indicate the version of Minecraft upgrading to or
 * past which should result in a data fixer being applied.  The convenience
 * versions in this class are the unused data version immediately preceding the
 * first non-snapshot release (prerelease, release candidate, or release) of
 * the specified version number.  If there was no unused data version
 * immediately preceding the first non-snapshot release, the data version of
 * the release itself is provided instead.
 * <p/>
 * You are free to use any data version; see
 * <a href="https://minecraft.wiki/w/Data_version">the Minecraft Wiki</a>
 * for a list.
 */
@SuppressWarnings("unused")
public final class DataVersions {
	@SuppressWarnings("UnnecessaryReturnStatement")
	private DataVersions() {
		return;
	}

	public static final int V_26_1_2 = 4789;
	public static final int V_26_1_1 = 4787;
	public static final int V_26_1   = 4783;
	public static final int V_1_21_11= 4663;
	public static final int V_1_21_10= 4555;
	public static final int V_1_21_9 = 4548;
	public static final int V_1_21_8 = 4439;
	public static final int V_1_21_7 = 4436;
	public static final int V_1_21_6 = 4430;
	public static final int V_1_21_5 = 4320;
	public static final int V_1_21_4 = 4179;
	public static final int V_1_21_3 = 4082;
	public static final int V_1_21_2 = 4073;
	public static final int V_1_21_1 = 3954;
	public static final int V_1_21   = 3948;
	public static final int V_1_20_6 = 3838;
	public static final int V_1_20_5 = 3828;
	public static final int V_1_20_4 = 3699;
	public static final int V_1_20_3 = 3692;
	public static final int V_1_20_2 = 3572;
	public static final int V_1_20_1 = 3464;
	public static final int V_1_20   = 3454;
	public static final int V_1_19_4 = 3330;
	public static final int V_1_19_3 = 3211;
	public static final int V_1_19_2 = 3118;
	public static final int V_1_19_1 = 3107;
	public static final int V_1_19   = 3097;
	public static final int V_1_18_2 = 2970;
	public static final int V_1_18_1 = 2861;
	public static final int V_1_18   = 2846;
}
