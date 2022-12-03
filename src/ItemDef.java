public final class ItemDef {

	private boolean searchable;

	public static void nullLoader() {
		mruNodes2 = null;
		mruNodes1 = null;
		streamIndices = null;
		cache = null;
		stream = null;
	}

	public boolean method192(int j) {
		int k = primaryMaleHeadPiece;
		int l = secondaryMaleHeadPiece;
		if(j == 1)
		{
			k = primaryFemaleHeadPiece;
			l = secondaryFemaleHeadPiece;
		}
		if(k == -1)
			return true;
		boolean flag = true;
		if(!Model.method463(k))
			flag = false;
		if(l != -1 && !Model.method463(l))
			flag = false;
		return flag;
	}

	public static void unpackConfig(StreamLoader streamLoader)	 {
		stream = new Buffer(streamLoader.getDataForName("obj.dat"));
		Buffer stream = new Buffer(streamLoader.getDataForName("obj.idx"));
		totalItems = stream.readUShort();
		streamIndices = new int[totalItems + 15000];
		int i = 2;
		for(int j = 0; j < totalItems; j++) {
			streamIndices[j] = i;
			i += stream.readUShort();
		}
		cache = new ItemDef[10];
		for(int k = 0; k < 10; k++) {
			cache[k] = new ItemDef();
		}
	}

	public Model method194(int j) {
		int k = primaryMaleHeadPiece;
		int l = secondaryMaleHeadPiece;
		if(j == 1) {
			k = primaryFemaleHeadPiece;
			l = secondaryFemaleHeadPiece;
		}
		if(k == -1)
			return null;
		Model model = Model.method462(k);
		if(l != -1) {
			Model model_1 = Model.method462(l);
			Model aclass30_sub2_sub4_sub6s[] = {
					model, model_1
			};
			model = new Model(2, aclass30_sub2_sub4_sub6s);
		}
	   if (originalModelColors != null) {
			for (int i1 = 0; i1 < originalModelColors.length; i1++)
				model.method476(originalModelColors[i1], modifiedModelColors[i1]);

		}
		return model;
	}

	public boolean method195(int j) {
		int k = primaryMaleModel;
		int l = secondaryMaleModel;
		int i1 = tertiaryMaleEquipmentModel;
		if(j == 1) {
			k = primaryFemaleModel;
			l = secondaryFemaleModel;
			i1 = tertiaryFemaleEquipmentModel;
		}
		if(k == -1)
			return true;
		boolean flag = true;
		if(!Model.method463(k))
			flag = false;
		if(l != -1 && !Model.method463(l))
			flag = false;
		if(i1 != -1 && !Model.method463(i1))
			flag = false;
		return flag;
	}

	public Model method196(int i) {
		int j = primaryMaleModel;
		int k = secondaryMaleModel;
		int l = tertiaryMaleEquipmentModel;
		if(i == 1) {
			j = primaryFemaleModel;
			k = secondaryFemaleModel;
			l = tertiaryFemaleEquipmentModel;
		}
		if(j == -1)
			return null;
		Model model = Model.method462(j);
		if(k != -1)
			if(l != -1) {
				Model model_1 = Model.method462(k);
				Model model_3 = Model.method462(l);
				Model aclass30_sub2_sub4_sub6_1s[] = {
						model, model_1, model_3
				};
				model = new Model(3, aclass30_sub2_sub4_sub6_1s);
			} else {
				Model model_2 = Model.method462(k);
				Model aclass30_sub2_sub4_sub6s[] = {
						model, model_2
				};
				model = new Model(2, aclass30_sub2_sub4_sub6s);
			}
		if(i == 0 && maleTranslation != 0)
			model.method475(0, maleTranslation, 0);
		if(i == 1 && femaleTranslation != 0)
			model.method475(0, femaleTranslation, 0);
		if (originalModelColors != null) {
			for (int i1 = 0; i1 < originalModelColors.length; i1++)
				model.method476(originalModelColors[i1], modifiedModelColors[i1]);

		}
		return model;
	}

	
	public void setDefaults() {
		modelId = 0;
		name = null;
		description = null;
		originalModelColors = null;
		modifiedModelColors = null;
		modifiedTextureColors = null;
		originalTextureColors = null;
		spriteScale = 2000;
		spritePitch = 0;
		spriteCameraRoll = 0;
		spriteCameraYaw = 0;
		spriteTranslateX = 0;
		spriteTranslateY = 0;
		stackable = false;
		value = 1;
		membersObject = false;
		groundActions = null;
		itemActions = null;
		primaryMaleModel = -1;
		secondaryMaleModel = -1;
		maleTranslation = 0;
		primaryFemaleModel = -1;
		secondaryFemaleModel = -1;
		femaleTranslation = 0;
		tertiaryMaleEquipmentModel = -1;
		tertiaryFemaleEquipmentModel = -1;
		primaryMaleHeadPiece = -1;
		secondaryMaleHeadPiece = -1;
		primaryFemaleHeadPiece = -1;
		secondaryFemaleHeadPiece = -1;
		stackIDs = null;
		stackAmounts = null;
		certID = -1;
		certTemplateID = -1;
		groundScaleX = 128;
		groundScaleY = 128;
		groundScaleZ = 128;
		ambient = 0;
		contrast = 0;
		team = 0;
		notedId = -1;
		unnotedId = -1;
		placeholderId = -1;
		placeholderTemplateId = -1;
		searchable = false;
	}

	public static ItemDef forID(int i) {
		for(int j = 0; j < 10; j++) {
			if (cache[j].id == i) {
				return cache[j];
			}
		}
		if (i == -1)
			i = 0;
		cacheIndex = (cacheIndex + 1) % 10;
		ItemDef itemDef = cache[cacheIndex];
		if (i >= streamIndices.length)
			i = 0;
		stream.currentOffset = streamIndices[i];
		itemDef.id = i;
		itemDef.setDefaults();
		itemDef.readValues(stream);
		if (itemDef.certTemplateID != -1) {
			itemDef.toNote();
		}

		if (itemDef.notedId != -1) {
			itemDef.method2789(forID(itemDef.notedId), forID(itemDef.unnotedId));
		}

		if (itemDef.placeholderTemplateId != -1) {
			itemDef.method2790(forID(itemDef.placeholderTemplateId), forID(itemDef.placeholderId));
		}

		if(!isMembers && itemDef.membersObject) {
			itemDef.name = "Members Object";
			itemDef.description = "Login to a members' server to use this object.".getBytes();
			itemDef.groundActions = null;
			itemDef.itemActions = null;
			itemDef.team = 0;
		}
		return itemDef;
	}

	public void actionData(int a, String b) {
		itemActions = new String[5];
		itemActions[a] = b;
	}
	public void totalColors(int total) {
	   originalModelColors = new int[total];	   
	   modifiedModelColors = new int[total];
	}
	public void colors(int id, int original, int modified) {
		originalModelColors[id] = original;
		modifiedModelColors[id] = modified;
	}
	public void itemData(String n, String d) {
		name = n;
		description = d.getBytes();
	}
	void method2789(ItemDef var1, ItemDef var2) {
		modelId = var1.modelId * 1;
		spriteScale = var1.spriteScale * 1;
		spritePitch = 1 * var1.spritePitch;
		spriteCameraRoll = 1 * var1.spriteCameraRoll;
		spriteCameraYaw = 1 * var1.spriteCameraYaw;
		spriteTranslateX = 1 * var1.spriteTranslateX;
		spriteTranslateY = var1.spriteTranslateY * 1;
		originalModelColors = var2.originalModelColors;
		modifiedModelColors = var2.modifiedModelColors;
		originalTextureColors = var2.originalTextureColors;
		modifiedTextureColors = var2.modifiedTextureColors;
		name = var2.name;
		membersObject = var2.membersObject;
		stackable = var2.stackable;
		primaryMaleModel = 1 * var2.primaryMaleModel;
		secondaryMaleModel = 1 * var2.secondaryMaleModel;
		tertiaryMaleEquipmentModel = 1 * var2.tertiaryMaleEquipmentModel;
		primaryFemaleModel = var2.primaryFemaleModel * 1;
		secondaryFemaleModel = var2.secondaryFemaleModel * 1;
		tertiaryFemaleEquipmentModel = 1 * var2.tertiaryFemaleEquipmentModel;
		primaryMaleHeadPiece = 1 * var2.primaryMaleHeadPiece;
		secondaryMaleHeadPiece = var2.secondaryMaleHeadPiece * 1;
		primaryFemaleHeadPiece = var2.primaryFemaleHeadPiece * 1;
		secondaryFemaleHeadPiece = var2.secondaryFemaleHeadPiece * 1;
		team = var2.team * 1;
		groundActions = var2.groundActions;
		itemActions = new String[5];
		//equipActions = new String[5];
		if (null != var2.itemActions) {
			for (int var4 = 0; var4 < 4; ++var4) {
				itemActions[var4] = var2.itemActions[var4];
			}
		}

		itemActions[4] = "Discard";
		value = 0;
	}

	void method2790(ItemDef var1, ItemDef var2) {
		modelId = var1.modelId * 1;
		spriteScale = 1 * var1.spriteScale;
		spritePitch = var1.spritePitch * 1;
		spriteCameraRoll = var1.spriteCameraRoll * 1;
		spriteCameraYaw = var1.spriteCameraYaw * 1;
		spriteTranslateX = 1 * var1.spriteTranslateX;
		spriteTranslateY = var1.spriteTranslateY * 1;
		originalModelColors = var1.originalModelColors;
		modifiedModelColors = var1.modifiedModelColors;
		originalTextureColors = var1.originalTextureColors;
		modifiedTextureColors = var1.modifiedTextureColors;
		stackable = var1.stackable;
		name = var2.name;
		value = 0;
	}

	public void models(int mID, int mE, int fE, int mE2, int fE2) {
		modelId = mID;
		primaryMaleModel = mE;
		primaryFemaleModel = fE;
		secondaryMaleModel = mE2;
		secondaryFemaleModel = fE2;
	}
	public void modelData(int mZ, int mR1, int mR2, int mO1, int mO2) {
		spriteScale = mZ;
		spritePitch = mR1;
		spriteCameraRoll = mR2;
		spriteTranslateX = mO1;
		spriteTranslateY = mO2;
	}

	private void toNote() {
		ItemDef itemDef = forID(certTemplateID);
		modelId = itemDef.modelId;
		spriteScale = itemDef.spriteScale;
		spritePitch = itemDef.spritePitch;
		spriteCameraRoll = itemDef.spriteCameraRoll;

		spriteCameraYaw = itemDef.spriteCameraYaw;
		spriteTranslateX = itemDef.spriteTranslateX;
		spriteTranslateY = itemDef.spriteTranslateY;
		modifiedModelColors = itemDef.modifiedModelColors;
		originalModelColors = itemDef.originalModelColors;
		ItemDef itemDef_1 = forID(certID);
		name = itemDef_1.name;
		membersObject = itemDef_1.membersObject;
		value = itemDef_1.value;
		String s = "a";
		if (itemDef_1.name != null) {
			char c = itemDef_1.name.charAt(0);
			if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')
				s = "an";
		}
		description = ("Swap this note at any bank for " + s + " " + itemDef_1.name + ".").getBytes();
		stackable = true;
	}


	public static Sprite getSprite(int i, int j, int k) {
		if(k == 0) {
			Sprite sprite = (Sprite) mruNodes1.insertFromCache(i);
			if(sprite != null && sprite.anInt1445 != j && sprite.anInt1445 != -1) {
				sprite.unlink();
				sprite = null;
			}
			if(sprite != null)
				return sprite;
		}
		ItemDef itemDef = forID(i);
		if(itemDef.stackIDs == null)
			j = -1;
		if(j > 1) {
			int i1 = -1;
			for(int j1 = 0; j1 < 10; j1++)
				if(j >= itemDef.stackAmounts[j1] && itemDef.stackAmounts[j1] != 0)
					i1 = itemDef.stackIDs[j1];
			if(i1 != -1)
				itemDef = forID(i1);
		}
		Model model = itemDef.method201(1);
		if(model == null)
			return null;
		Sprite sprite = null;
		if(itemDef.certTemplateID != -1) {
			sprite = getSprite(itemDef.certID, 10, -1);
			if(sprite == null)
				return null;
		} else if (itemDef.notedId != -1) {
			sprite = getSprite(itemDef.unnotedId, j, -1);
			if (sprite == null)
				return null;
		} else if (itemDef.placeholderTemplateId != -1) {
			sprite = getSprite(itemDef.placeholderId, j, -1);
			if (sprite == null)
				return null;
		}
		Sprite sprite2 = new Sprite(32, 32);
		int k1 = Texture.textureInt1;
		int l1 = Texture.textureInt2;
		int ai[] = Texture.anIntArray1472;
		int ai1[] = DrawingArea.pixels;
		int i2 = DrawingArea.width;
		int j2 = DrawingArea.height;
		int k2 = DrawingArea.topX;
		int l2 = DrawingArea.bottomX;
		int i3 = DrawingArea.topY;
		int j3 = DrawingArea.bottomY;
		Texture.aBoolean1464 = false;
		DrawingArea.initDrawingArea(32, 32, sprite2.myPixels);
		DrawingArea.drawPixels(32, 0, 0, 0, 32);
		Texture.method364();
		if (itemDef.placeholderTemplateId != -1) {
			int l5 = sprite.anInt1444;
			int j6 = sprite.anInt1445;
			sprite.anInt1444 = 32;
			sprite.anInt1445 = 32;
			sprite.drawSprite(0, 0);
			sprite.anInt1444 = l5;
			sprite.anInt1445 = j6;
		}
		int k3 = itemDef.spriteScale;
		if(k == -1)
			k3 = (int)((double)k3 * 1.5D);
		if(k > 0)
			k3 = (int)((double)k3 * 1.04D);
		int l3 = Texture.anIntArray1470[itemDef.spritePitch] * k3 >> 16;
		int i4 = Texture.anIntArray1471[itemDef.spritePitch] * k3 >> 16;
		model.method482(itemDef.spriteCameraRoll, itemDef.spriteCameraYaw, itemDef.spritePitch, itemDef.spriteTranslateX, l3 + model.modelHeight / 2 + itemDef.spriteTranslateY, i4 + itemDef.spriteTranslateY);
		for(int i5 = 31; i5 >= 0; i5--) {
			for(int j4 = 31; j4 >= 0; j4--)
				if(sprite2.myPixels[i5 + j4 * 32] == 0)
					if(i5 > 0 && sprite2.myPixels[(i5 - 1) + j4 * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if(j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if(i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else if(j4 < 31 && sprite2.myPixels[i5 + (j4 + 1) * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
		}
		if(k > 0) {
			for(int j5 = 31; j5 >= 0; j5--) {
				for(int k4 = 31; k4 >= 0; k4--)
					if(sprite2.myPixels[j5 + k4 * 32] == 0)
						if(j5 > 0 && sprite2.myPixels[(j5 - 1) + k4 * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;
						else if(k4 > 0 && sprite2.myPixels[j5 + (k4 - 1) * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;
						else if(j5 < 31 && sprite2.myPixels[j5 + 1 + k4 * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;
						else if(k4 < 31 && sprite2.myPixels[j5 + (k4 + 1) * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;
			}
		} else if(k == 0) {
			for(int k5 = 31; k5 >= 0; k5--) {
				for(int l4 = 31; l4 >= 0; l4--)
					if(sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0 && sprite2.myPixels[(k5 - 1) + (l4 - 1) * 32] > 0)
						sprite2.myPixels[k5 + l4 * 32] = 0x302020;
			}
		}
		if (itemDef.notedId != -1) {
			int l5 = sprite.anInt1444;
			int j6 = sprite.anInt1445;
			sprite.anInt1444 = 32;
			sprite.anInt1445 = 32;
			sprite.drawSprite(0, 0);
			sprite.anInt1444 = l5;
			sprite.anInt1445 = j6;
		}
		if(itemDef.certTemplateID != -1) {
			int l5 = sprite.anInt1444;
			int j6 = sprite.anInt1445;
			sprite.anInt1444 = 32;
			sprite.anInt1445 = 32;
			sprite.drawSprite(0, 0);
			sprite.anInt1444 = l5;
			sprite.anInt1445 = j6;
		}
		if(k == 0)
			mruNodes1.removeFromCache(sprite2, i);
		DrawingArea.initDrawingArea(j2, i2, ai1);
		DrawingArea.setDrawingArea(j3, k2, l2, i3);
		Texture.textureInt1 = k1;
		Texture.textureInt2 = l1;
		Texture.anIntArray1472 = ai;
		Texture.aBoolean1464 = true;
		if(itemDef.stackable)
			sprite2.anInt1444 = 33;
		else
			sprite2.anInt1444 = 32;
		sprite2.anInt1445 = j;
		return sprite2;
	}

	public Model method201(int i) {
		if(stackIDs != null && i > 1) {
			int j = -1;
			for(int k = 0; k < 10; k++)
				if(i >= stackAmounts[k] && stackAmounts[k] != 0)
					j = stackIDs[k];
			if(j != -1)
				return forID(j).method201(1);
		}
		Model model = (Model) mruNodes2.insertFromCache(id);
		if(model != null)
			return model;
		model = Model.method462(modelId);
		if(model == null)
			return null;
		if(groundScaleX != 128 || groundScaleY != 128 || groundScaleZ != 128)
			model.method478(groundScaleX, groundScaleZ, groundScaleY);
		if (modifiedModelColors != null) {
			for (int l = 0; l < modifiedModelColors.length; l++)
				model.method476(modifiedModelColors[l], originalModelColors[l]);

		}
		model.method479(64 + ambient, 768 + contrast, -50, -10, -50, true);
		model.fits_on_single_square = true;
		mruNodes2.removeFromCache(model, id);
		return model;
	}

	public Model method202(int i) {
		if(stackIDs != null && i > 1) {
			int j = -1;
			for(int k = 0; k < 10; k++)
				if(i >= stackAmounts[k] && stackAmounts[k] != 0)
					j = stackIDs[k];
			if(j != -1)
				return forID(j).method202(1);
		}
		Model model = Model.method462(modelId);
		if(model == null)
			return null;
		if (modifiedModelColors != null) {
			for (int l = 0; l < modifiedModelColors.length; l++)
				model.method476(modifiedModelColors[l], originalModelColors[l]);

		}
		return model;
	}

	private void readValues(Buffer stream) {
		while (true) {
			int opcode = stream.readUnsignedByte();
			if (opcode == 0)
				return;
			if (opcode == 1)
				modelId = stream.readUShort();
			else if (opcode == 2)
				name = stream.readString();
			else if (opcode == 3)
				description = stream.readBytes();
			else if (opcode == 4)
				spriteScale = stream.readUShort();
			else if (opcode == 5)
				spritePitch = stream.readUShort();
			else if (opcode == 6)
				spriteCameraRoll = stream.readUShort();
			else if (opcode == 7) {
				spriteTranslateX = stream.readUShort();
				if (spriteTranslateX > 32767)
					spriteTranslateX -= 0x10000;
			} else if (opcode == 8) {
				spriteTranslateY = stream.readUShort();
				if (spriteTranslateY > 32767)
					spriteTranslateY -= 0x10000;
			} else if (opcode == 11)
				stackable = true;
			else if (opcode == 12)
				value = stream.readDWord();
			else if(opcode == 13)
				this.field2142 = stream.readSignedByte();
			else if(opcode == 14)
				this.field2157 = stream.readSignedByte();
			else if (opcode == 16)
				membersObject = true;
			else if (opcode == 23) {
				primaryMaleModel = stream.readUShort();
				maleTranslation = stream.readSignedByte();
				if (primaryMaleModel == 65535)
					primaryMaleModel = -1;
			} else if (opcode == 24)
				secondaryMaleModel = stream.readUShort();
			else if (opcode == 25) {
				primaryFemaleModel = stream.readUShort();
				femaleTranslation = stream.readSignedByte();
				if (primaryFemaleModel == 65535)
					primaryFemaleModel = -1;
			} else if (opcode == 26)
				secondaryFemaleModel = stream.readUShort();
			else if(opcode == 27)
				this.field2158 = stream.readSignedByte();
			else if (opcode >= 30 && opcode < 35) {
				if (groundActions == null)
					groundActions = new String[5];
				groundActions[opcode - 30] = stream.readString();
				if (groundActions[opcode - 30].equalsIgnoreCase("hidden"))
					groundActions[opcode - 30] = null;
			} else if (opcode >= 35 && opcode < 40) {
				if (itemActions == null)
					itemActions = new String[5];
				itemActions[opcode - 35] = stream.readString();
			} else if (opcode == 40) {
				int size = stream.readUnsignedByte();
				originalModelColors = new int[size];
				modifiedModelColors = new int[size];
				for (int index = 0; index < size; index++) {
					originalModelColors[index] = stream.readUShort();
					modifiedModelColors[index] = stream.readUShort();
				}
			} else if (opcode == 41) {
				int size = stream.readUnsignedByte();
				originalTextureColors = new short[size];
				modifiedTextureColors = new short[size];
				for (int index = 0; index < size; index++) {
					originalTextureColors[index] = (short) stream.readUShort();
					modifiedTextureColors[index] = (short) stream.readUShort();
				}
			}else if (opcode == 42) {
				stream.readUnsignedByte();
			} else if (opcode == 65) {
				searchable = true;
			} else if (opcode == 75){
				this.field2182 = stream.readSignedWord();
			} else if (opcode == 78)
				tertiaryMaleEquipmentModel = stream.readUShort();
			else if (opcode == 79)
				tertiaryFemaleEquipmentModel = stream.readUShort();
			else if (opcode == 90)
				primaryMaleHeadPiece = stream.readUShort();
			else if (opcode == 91)
				primaryFemaleHeadPiece = stream.readUShort();
			else if (opcode == 92)
				secondaryMaleHeadPiece = stream.readUShort();
			else if (opcode == 93)
				secondaryFemaleHeadPiece = stream.readUShort();
			else if (opcode == 94)
				stream.readUShort();
			else if (opcode == 95)
				spriteCameraYaw = stream.readUShort();
			else if (opcode == 97)
				certID = stream.readUShort();
			else if (opcode == 98)
				certTemplateID = stream.readUShort();
			else if (opcode >= 100 && opcode < 110) {
				if (stackIDs == null) {
					stackIDs = new int[10];
					stackAmounts = new int[10];
				}
				stackIDs[opcode - 100] = stream.readUShort();
				stackAmounts[opcode - 100] = stream.readUShort();
			} else if (opcode == 110)
				groundScaleX = stream.readUShort();
			else if (opcode == 111)
				groundScaleY = stream.readUShort();
			else if (opcode == 112)
				groundScaleZ = stream.readUShort();
			else if (opcode == 113)
				ambient = stream.readSignedByte();
			else if (opcode == 114)
				contrast = stream.readSignedByte() * 5;
			else if (opcode == 115)
				team = stream.readUnsignedByte();
			else if (opcode == 139)
				unnotedId = stream.readUShort();
			else if (opcode == 140)
				notedId = stream.readUShort();
			else if (opcode == 148)
				placeholderId = stream.readUShort();
			else if (opcode == 149)
				placeholderTemplateId = stream.readUShort();
			else {
				// System.out.println("Error loading item " + id + ", opcode " + opcode);
			}
		}
	}

	public int unnotedId, notedId, placeholderId, placeholderTemplateId;

	public ItemDef() {
		id = -1;
	}

	public byte femaleTranslation;
	public int value;
	public int[] modifiedModelColors;
	public int id;
	static MRUNodes mruNodes1 = new MRUNodes(100);
	public static MRUNodes mruNodes2 = new MRUNodes(50);
	public int[] originalModelColors;
	public boolean membersObject;
	public int tertiaryFemaleEquipmentModel;
	public int certTemplateID;
	public int secondaryFemaleModel;
	public int primaryMaleModel;
	public int secondaryMaleHeadPiece;
	public int groundScaleX;
	public String groundActions[];
	public int spriteTranslateX;
	public String name;
	public static ItemDef[] cache;
	public int secondaryFemaleHeadPiece;
	public int modelId;
	public int primaryMaleHeadPiece;
	public boolean stackable;
	public byte description[];
	public int certID;
	public static int cacheIndex;
	public int spriteScale;
	public static boolean isMembers = true;
	public static Buffer stream;
	public int contrast;
	public int tertiaryMaleEquipmentModel;
	public int secondaryMaleModel;
	public String itemActions[];
	public int spritePitch;
	public int groundScaleZ;
	public int groundScaleY;
	public int[] stackIDs;
	public int spriteTranslateY;
	public static int[] streamIndices;
	public int ambient;
	public int primaryFemaleHeadPiece;
	public int spriteCameraRoll;
	public int primaryFemaleModel;
	public int[] stackAmounts;
	public int team;
	public static int totalItems;
	public int spriteCameraYaw;
	public int field2142 = -1;
	public int field2157 = -1;
	public int field2158 = -1;
	private short[] originalTextureColors;
	private short[] modifiedTextureColors;
	private int field2182;
	public byte maleTranslation;
	public int anInt164;
	public int anInt199;
	public int anInt188;
}