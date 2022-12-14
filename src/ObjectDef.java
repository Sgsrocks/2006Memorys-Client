// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class ObjectDef
{

	public static ObjectDef forID(int i)
	{
		for(int j = 0; j < 20; j++)
			if(cache[j].type == i)
				return cache[j];

		cacheIndex = (cacheIndex + 1) % 20;
		ObjectDef class46 = cache[cacheIndex];
		stream.currentOffset = streamIndices[i];
		class46.type = i;
		class46.setDefaults();
		class46.readValues(stream);
		return class46;
	}

	private void setDefaults()
	{
		anIntArray773 = null;
		anIntArray776 = null;
		name = null;
		description = null;
		modifiedModelColors = null;
		originalModelColors = null;
		anInt744 = 1;
		anInt761 = 1;
		solid = true;
		aBoolean757 = true;
		hasActions = false;
		contouredGround = false;
		delayShading = false;
		aBoolean764 = false;
		animation = -1;
		anInt775 = 16;
		ambientLighting = 0;
		lightDiffusion = 0;
		actions = null;
		anInt746 = -1;
		anInt758 = -1;
		aBoolean751 = false;
		aBoolean779 = true;
		thickness = 128;
		height = 128;
		width = 128;
		anInt768 = 0;
		anInt738 = 0;
		anInt745 = 0;
		anInt783 = 0;
		aBoolean736 = false;
		aBoolean766 = false;
		anInt760 = -1;
		anInt774 = -1;
		anInt749 = -1;
		childrenIDs = null;
	}

	public void method574(OnDemandFetcher class42_sub1)
	{
		if(anIntArray773 == null)
			return;
		for(int j = 0; j < anIntArray773.length; j++)
			class42_sub1.method560(anIntArray773[j] & 0xffff, 0);
	}

	public static void nullLoader()
	{
		mruNodes1 = null;
		mruNodes2 = null;
		streamIndices = null;
		cache = null;
stream = null;
	}

	public static void unpackConfig(StreamLoader streamLoader)
	{
		stream = new Buffer(streamLoader.getDataForName("loc.dat"));
		Buffer stream = new Buffer(streamLoader.getDataForName("loc.idx"));
		int totalObjects = stream.readUShort();
		streamIndices = new int[totalObjects];
		int i = 2;
		for(int j = 0; j < totalObjects; j++)
		{
			streamIndices[j] = i;
			i += stream.readUShort();
		}

		cache = new ObjectDef[20];
		for(int k = 0; k < 20; k++)
			cache[k] = new ObjectDef();

	}

	public boolean method577(int i)
	{
		if(anIntArray776 == null)
		{
			if(anIntArray773 == null)
				return true;
			if(i != 10)
				return true;
			boolean flag1 = true;
			for(int k = 0; k < anIntArray773.length; k++)
				flag1 &= Model.method463(anIntArray773[k] & 0xffff);

			return flag1;
		}
		for(int j = 0; j < anIntArray776.length; j++)
			if(anIntArray776[j] == i)
				return Model.method463(anIntArray773[j] & 0xffff);

		return true;
	}

	public Model method578(int i, int j, int k, int l, int i1, int j1, int k1)
	{
		Model model = method581(i, k1, j);
		if(model == null)
			return null;
		if(contouredGround || delayShading)
			model = new Model(contouredGround, delayShading, model);
		if(contouredGround)
		{
			int l1 = (k + l + i1 + j1) / 4;
			for(int i2 = 0; i2 < model.verticesCount; i2++)
			{
				int j2 = model.verticesX[i2];
				int k2 = model.verticesZ[i2];
				int l2 = k + ((l - k) * (j2 + 64)) / 128;
				int i3 = j1 + ((i1 - j1) * (j2 + 64)) / 128;
				int j3 = l2 + ((i3 - l2) * (k2 + 64)) / 128;
				model.verticesY[i2] += j3 - l1;
			}

			model.method467();
		}
		return model;
	}

	public boolean method579()
	{
		if(anIntArray773 == null)
			return true;
		boolean flag1 = true;
		for(int i = 0; i < anIntArray773.length; i++)
			flag1 &= Model.method463(anIntArray773[i] & 0xffff);
			return flag1;
	}

	public ObjectDef method580()
	{
		int i = -1;
		if(anInt774 != -1)
		{
			VarBit varBit = VarBit.cache[anInt774];
			int j = varBit.anInt648;
			int k = varBit.anInt649;
			int l = varBit.anInt650;
			int i1 = client.anIntArray1232[l - k];
			i = clientInstance.variousSettings[j] >> k & i1;
		} else
		if(anInt749 != -1)
			i = clientInstance.variousSettings[anInt749];
		if(i < 0 || i >= childrenIDs.length || childrenIDs[i] == -1)
			return null;
		else
			return forID(childrenIDs[i]);
	}

	private Model method581(int j, int k, int l)
	{
		Model model = null;
		long l1;
		if(anIntArray776 == null)
		{
			if(j != 10)
				return null;
			l1 = (long)((type << 6) + l) + ((long)(k + 1) << 32);
			Model model_1 = (Model) mruNodes2.insertFromCache(l1);
			if(model_1 != null)
				return model_1;
			if(anIntArray773 == null)
				return null;
			boolean flag1 = aBoolean751 ^ (l > 3);
			int k1 = anIntArray773.length;
			for(int i2 = 0; i2 < k1; i2++)
			{
				int l2 = anIntArray773[i2];
				if(flag1)
					l2 += 0x10000;
				model = (Model) mruNodes1.insertFromCache(l2);
				if(model == null)
				{
					model = Model.method462(l2 & 0xffff);
					if(model == null)
						return null;
					if(flag1)
						model.method477();
					mruNodes1.removeFromCache(model, l2);
				}
				if(k1 > 1)
					aModelArray741s[i2] = model;
			}

			if(k1 > 1)
				model = new Model(k1, aModelArray741s);
		} else
		{
			int i1 = -1;
			for(int j1 = 0; j1 < anIntArray776.length; j1++)
			{
				if(anIntArray776[j1] != j)
					continue;
				i1 = j1;
				break;
			}

			if(i1 == -1)
				return null;
			l1 = (long)((type << 6) + (i1 << 3) + l) + ((long)(k + 1) << 32);
			Model model_2 = (Model) mruNodes2.insertFromCache(l1);
			if(model_2 != null)
				return model_2;
			int j2 = anIntArray773[i1];
			boolean flag3 = aBoolean751 ^ (l > 3);
			if(flag3)
				j2 += 0x10000;
			model = (Model) mruNodes1.insertFromCache(j2);
			if(model == null)
			{
				model = Model.method462(j2 & 0xffff);
				if(model == null)
					return null;
				if(flag3)
					model.method477();
				mruNodes1.removeFromCache(model, j2);
			}
		}
		boolean flag;
		flag = thickness != 128 || height != 128 || width != 128;
		boolean flag2;
		flag2 = anInt738 != 0 || anInt745 != 0 || anInt783 != 0;
		Model model_3 = new Model(modifiedModelColors == null, Class36.method532(k), l == 0 && k == -1 && !flag && !flag2, model);
		if(k != -1)
		{
			model_3.method469();
			model_3.method470(k);
			model_3.faceGroups = null;
			model_3.vertexGroups = null;
		}
		while(l-- > 0) 
			model_3.method473();
		if(modifiedModelColors != null)
		{
			for(int k2 = 0; k2 < modifiedModelColors.length; k2++)
				model_3.method476(modifiedModelColors[k2], originalModelColors[k2]);

		}
		if(flag)
			model_3.method478(thickness, width, height);
		if(flag2)
			model_3.method475(anInt738, anInt745, anInt783);
		model_3.method479(64 + ambientLighting, 768 + lightDiffusion * 5, -50, -10, -50, !delayShading);
		if(anInt760 == 1)
			model_3.itemDropHeight = model_3.modelHeight;
		mruNodes2.removeFromCache(model_3, l1);
		return model_3;
	}

	public void readValues(Buffer stream) {
		int flag = -1;
		do {
			int type = stream.readUnsignedByte();
			if (type == 0)
				break;
			if (type == 1) {
				int len = stream.readUnsignedByte();
				if (len > 0) {
					if (anIntArray773 == null || lowMem) {
						anIntArray776 = new int[len];
						anIntArray773 = new int[len];
						for (int k1 = 0; k1 < len; k1++) {
							anIntArray773[k1] = stream.readUShort();
							anIntArray776[k1] = stream.readUnsignedByte();
						}
					} else {
						stream.currentOffset += len * 3;
					}
				}
			} else if (type == 2)
				name = stream.readString();
			else if (type == 3)
				description = stream.readBytes();
			else if (type == 5) {
				int len = stream.readUnsignedByte();
				if (len > 0) {
					if (anIntArray773 == null || lowMem) {
						anIntArray776 = null;
						anIntArray773 = new int[len];
						for (int l1 = 0; l1 < len; l1++)
							anIntArray773[l1] = stream.readUShort();
					} else {
						stream.currentOffset += len * 2;
					}
				}
			} else if (type == 14)
				anInt744 = stream.readUnsignedByte();
			else if (type == 15)
				anInt761 = stream.readUnsignedByte();
			else if (type == 17)
				solid = false;
			else if (type == 18)
				aBoolean757 = false;
			else if (type == 19)
				hasActions = (stream.readUnsignedByte() == 1);
			else if (type == 21)
				contouredGround = true;
			else if (type == 22)
				delayShading = true;
			else if (type == 23)
				aBoolean764 = true;
			else if (type == 24) { // Object Animations
				animation = stream.readUShort();
				if (animation == 65535)
					animation = -1;
			} else if (type == 28)
				anInt775 = stream.readUnsignedByte();
			else if (type == 29)
				ambientLighting = stream.readSignedByte();
			else if (type == 39)
				lightDiffusion = stream.readSignedByte();
			else if (type >= 30 && type < 39) {
				if (actions == null)
					actions = new String[10];
				actions[type - 30] = stream.readString();
				if (actions[type - 30].equalsIgnoreCase("hidden"))
					actions[type - 30] = null;
			} else if (type == 40) {
				int i1 = stream.readUnsignedByte();
				modifiedModelColors = new int[i1];
				originalModelColors = new int[i1];
				for (int i2 = 0; i2 < i1; i2++) {
					modifiedModelColors[i2] = stream.readUShort();
					originalModelColors[i2] = stream.readUShort();
				}
			} else if (type == 41) {
				int i1 = stream.readUnsignedByte();
				originalTexture = new short[i1];
				modifiedTexture = new short[i1];
				for (int i2 = 0; i2 < i1; i2++) {
					originalTexture[i2] = (short) stream.readUShort();
					modifiedTexture[i2] = (short) stream.readUShort();
				}
			} else if (type == 61) {
				opcode61 = stream.readUShort();
			} else if (type == 62)
				aBoolean751 = true;
			else if (type == 64)
				aBoolean779 = false;
			else if (type == 65)
				thickness = stream.readUShort();
			else if (type == 66)
				height = stream.readUShort();
			else if (type == 67)
				width = stream.readUShort();
			else if (type == 68)
				anInt758 = stream.readUShort();
			else if (type == 69)
				anInt768 = stream.readUnsignedByte();
			else if (type == 70)
				anInt738 = stream.readSignedWord();
			else if (type == 71)
				anInt745 = stream.readSignedWord();
			else if (type == 72)
				anInt783 = stream.readSignedWord();
			else if (type == 73)
				aBoolean736 = true;
			else if (type == 74)
				aBoolean766 = true;
			else if (type == 75)
				anInt760 = stream.readUnsignedByte();
			else if (type == 77 || type == 92) {
				anInt774 = stream.readUShort();
				if (anInt774 == 65535)
					anInt774 = -1;
				anInt749 = stream.readUShort();
				if (anInt749 == 65535)
					anInt749 = -1;
				int var3 = -1;
				if(type == 92) {
					var3 = stream.readUShort();
				}
				int j1 = stream.readUnsignedByte();
				childrenIDs = new int[j1 + 2];
				for (int j2 = 0; j2 <= j1; j2++) {
					childrenIDs[j2] = stream.readUShort();
					if (childrenIDs[j2] == 65535)
						childrenIDs[j2] = -1;
				}

				childrenIDs[j1 + 1] = var3;
			} else if(type == 78) {//TODO Figure out what these do in OSRS
				//First short = ambient sound
				stream.skip(3);
			} else if(type == 79) {
				stream.skip(5);
				int count = stream.readSignedByte();
				stream.skip(2 * count);
			} else if(type == 81) {
				stream.skip(1);//Clip type?
			} else if (type == 82) {
				anInt746 = stream.readUShort();//AreaType
			} else if(type == 89) {
				field3621 = false;
			} else if(type == 249) {
				int var1 = stream.readUnsignedByte();
				for(int var2 = 0;var2<var1;var2++) {
					boolean b = stream.readUnsignedByte() == 1;
					stream.skip(3);
					if(b) {
						stream.readString();
					} else {
						stream.readDWord();
					}
				}
			}
		} while (true);
		if (flag == -1 && name != "null" && name != null) {
			hasActions = anIntArray773 != null && (anIntArray776 == null || anIntArray776[0] == 10);
			if (actions != null)
				hasActions = true;
		}
		if (aBoolean766) {
			solid = false;
			aBoolean757 = false;
		}
		if (anInt760 == -1)
			anInt760 = solid ? 1 : 0;
	}

	private ObjectDef()
	{
		type = -1;
	}

	public boolean aBoolean736;
	private byte ambientLighting;
	private int anInt738;
	public String name;
	private int width;
	private static final Model[] aModelArray741s = new Model[4];
	private byte lightDiffusion;
	public int anInt744;
	private int anInt745;
	public int anInt746;
	private int[] originalModelColors;
	private int thickness;
	public int anInt749;
	private boolean aBoolean751;
	public static boolean lowMem;
	private static Buffer stream;
	public int type;
	private static int[] streamIndices;
	public boolean aBoolean757;
	public int anInt758;
	public int childrenIDs[];
	private int anInt760;
	public int anInt761;
	public boolean contouredGround;
	public boolean aBoolean764;
	public static client clientInstance;
	private boolean aBoolean766;
	public boolean solid;
	public int anInt768;
	private boolean delayShading;
	private static int cacheIndex;
	private int height;
	private int[] anIntArray773;
	public int anInt774;
	private short[] originalTexture;
	private short[] modifiedTexture;
	private int opcode61;
	public int anInt775;
	private int[] anIntArray776;
	public byte description[];
	public boolean hasActions;
	public boolean aBoolean779;
	public static MRUNodes mruNodes2 = new MRUNodes(30);
	public int animation;
	private static ObjectDef[] cache;
	private int anInt783;
	private int[] modifiedModelColors;
	public static MRUNodes mruNodes1 = new MRUNodes(500);
	public String actions[];
	public boolean field3621;
}
