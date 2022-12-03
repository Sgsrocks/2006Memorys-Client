// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class Animation {

	public static void unpackConfig(StreamLoader streamLoader)
	{
		Buffer stream = new Buffer(streamLoader.getDataForName("seq.dat"));
		int length = stream.readUShort();
		if(anims == null)
			anims = new Animation[length];
		for(int j = 0; j < length; j++)
		{
			if(anims[j] == null)
				anims[j] = new Animation();
			anims[j].readValues(stream);
		}
	}

	public int method258(int i)
	{
		int j = durations[i];
		if(j == 0)
		{
			Class36 class36 = Class36.forId(primaryFrames[i]);
			if(class36 != null)
				j = durations[i] = class36.anInt636;
		}
		if(j == 0)
			j = 1;
		return j;
	}

	private void readValues(Buffer stream) {
		int i;
		while ((i = stream.readUnsignedByte()) != 0) {

			if (i == 1) {
				anInt352 = stream.readUShort();
				primaryFrames = new int[anInt352];
				secondaryFrames = new int[anInt352];
				durations = new int[anInt352];

				for (int j = 0; j < anInt352; j++)
					durations[j] = stream.readUShort();


				for (int j = 0; j < anInt352; j++) {
					primaryFrames[j] = stream.readUShort();
					secondaryFrames[j] = -1;
				}

				for (int j = 0; j < anInt352; j++) {
					primaryFrames[j] += stream.readUShort() << 16;
				}
			} else if (i == 2)
				anInt356 = stream.readUShort();
			else if (i == 3) {
				int k = stream.readUnsignedByte();
				anIntArray357 = new int[k + 1];
				for (int l = 0; l < k; l++)
					anIntArray357[l] = stream.readUnsignedByte();
				anIntArray357[k] = 9999999;
			} else if (i == 4) {
				aBoolean358 = true;
			} else if (i == 5) {
				anInt359 = stream.readUnsignedByte();
			} else if (i == 6) {
				anInt360 = stream.readUShort();
			} else if (i == 7) {
				anInt361 = stream.readUShort();
			} else if (i == 8) {
				anInt362 = stream.readUnsignedByte();
			} else if (i == 9) {
				anInt363 = stream.readUnsignedByte();
			} else if (i == 10) {
				anInt364 = stream.readUnsignedByte();
			} else if (i == 11) {
				anInt365 = stream.readUnsignedByte();
			} else if (i == 12) {
				int len = stream.readUnsignedByte();

				for (int i1 = 0; i1 < len; i1++) {
					stream.readUShort();
				}

				for (int i1 = 0; i1 < len; i1++) {
					stream.readUShort();
				}
			} else if (i == 13) {
				int var3 = stream.readUnsignedByte();
				frameSounds = new int[var3];
				for (int var4 = 0; var4 < var3; ++var4) {
					frameSounds[var4] = stream.read24BitInt();
					if (0 != frameSounds[var4]) {
						int var6 = frameSounds[var4] >> 8;
						int var8 = frameSounds[var4] >> 4 & 7;
						int var9 = frameSounds[var4] & 15;
						frameSounds[var4] = var6;
					}
				}
			} else if (i == 14) {
				skeletalId = stream.readInt();
			} else if (i == 15) {
				int count = stream.readUShort();
				skeletalsoundEffect = new int[count];
				skeletalsoundRange = new int[count];
				for (int index = 0; index < count; ++index) {
					skeletalsoundEffect[index] = stream.readUShort();
					skeletalsoundRange[index] = stream.read24BitInt();
				}
			} else if (i == 16) {
				skeletalRangeBegin = stream.readUShort();
				skeletalRangeEnd = stream.readUShort();
			} else if (i == 17) {
				int count = stream.readUnsignedByte();
				unknown = new int[count];
				for (int index = 0; index < count; ++index) {
					unknown[index] = stream.readUnsignedByte();
				}
			}
		}
		if (anInt352 == 0) {
			anInt352 = 1;
			primaryFrames = new int[1];
			primaryFrames[0] = -1;
			secondaryFrames = new int[1];
			secondaryFrames[0] = -1;
			durations = new int[1];
			durations[0] = -1;
		}
		if (anInt363 == -1)
			if (anIntArray357 != null)
				anInt363 = 2;
			else
				anInt363 = 0;
		if (anInt364 == -1) {
			if (anIntArray357 != null) {
				anInt364 = 2;
				return;
			}
			anInt364 = 0;
		}
	}

	private Animation()
	{
		anInt356 = -1;
		aBoolean358 = false;
		anInt359 = 5;
		anInt360 = -1;
		anInt361 = -1;
		anInt362 = 99;
		anInt363 = -1;
		anInt364 = -1;
		anInt365 = 2;
	}

	public static Animation anims[];
	public int anInt352;
	public int primaryFrames[];
	public int secondaryFrames[];
	private int[] durations;
	public int anInt356;
	public int anIntArray357[];
	public boolean aBoolean358;
	public int anInt359;
	public int anInt360;
	public int anInt361;
	public int anInt362;
	public int anInt363;
	public int anInt364;
	public int frameSounds[];
	private int skeletalRangeBegin = -1;
	private int skeletalRangeEnd = -1;
	private int skeletalId = -1;
	private int[] skeletalsoundEffect;
	private int[] unknown;
	private int[] skeletalsoundRange;
	public int anInt365;
	public static int anInt367;
}
