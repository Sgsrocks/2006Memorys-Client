// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class VarBit {

	public static void unpackConfig(StreamLoader streamLoader)
	{
		Buffer stream = new Buffer(streamLoader.getDataForName("varbit.dat"));
		int cacheSize = stream.readUShort();
		if(cache == null)
			cache = new VarBit[cacheSize];
		for(int j = 0; j < cacheSize; j++) {
			if (cache[j] == null)
				cache[j] = new VarBit();
			cache[j].readValues(stream);
		}
		if(stream.currentOffset != stream.buffer.length)
			System.out.println("varbit load mismatch");
	}

	private void readValues(Buffer stream)
	{
 stream.readUnsignedByte();
				anInt648 = stream.readUShort();
				anInt649 = stream.readUnsignedByte();
				anInt650 = stream.readUnsignedByte();
	}

	private VarBit()
	{
		aBoolean651 = false;
	}

	public static VarBit cache[];
	public int anInt648;
	public int anInt649;
	public int anInt650;
	private boolean aBoolean651;
}
