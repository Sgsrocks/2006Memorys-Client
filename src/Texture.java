// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

final class Texture extends DrawingArea {

	public static void nullLoader()
	{
		anIntArray1468 = null;
		anIntArray1468 = null;
		anIntArray1470 = null;
		anIntArray1471 = null;
		anIntArray1472 = null;
		textures = null;
		textureIsTransparant = null;
		averageTextureColours = null;
		textureRequestPixelBuffer = null;
		texturesPixelBuffer = null;
		textureLastUsed = null;
		hslToRgb = null;
		currentPalette = null;
	}

	public static void method364()
	{
		anIntArray1472 = new int[DrawingArea.height];
		for(int j = 0; j < DrawingArea.height; j++)
			anIntArray1472[j] = DrawingArea.width * j;

		textureInt1 = DrawingArea.width / 2;
		textureInt2 = DrawingArea.height / 2;
	}

	public static void method365(int j, int k)
	{
	   anIntArray1472 = new int[k];
		for(int l = 0; l < k; l++)
			anIntArray1472[l] = j * l;

		textureInt1 = j / 2;
		textureInt2 = k / 2;
	}

	public static void method366()
	{
		textureRequestPixelBuffer = null;
		for(int j = 0; j < textureAmount; j++)
			texturesPixelBuffer[j] = null;

	}

	public static void method367() {
		if (textureRequestPixelBuffer == null) {
			textureRequestBufferPointer = 20;
			textureRequestPixelBuffer = new int[textureRequestBufferPointer][0x10000];
			for (int k = 0; k < textureAmount; k++) {
				texturesPixelBuffer[k] = null;
			}
		}
	}


	public static void method368(StreamLoader streamLoader)
	{
		textureCount = 0;
		for(int j = 0; j < textureAmount; j++)
			try
			{
				textures[j] = new Background(streamLoader, String.valueOf(j), 0);
				if(lowMem && textures[j].anInt1456 == 128)
					textures[j].method356();
				else
					textures[j].method357();
				textureCount++;
			}
			catch(Exception _ex) { }

	}

	public static int method369(int i)
	{
		if(averageTextureColours[i] != 0)
			return averageTextureColours[i];
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = currentPalette[i].length;
		for(int k1 = 0; k1 < j1; k1++)
		{
			k += currentPalette[i][k1] >> 16 & 0xff;
			l += currentPalette[i][k1] >> 8 & 0xff;
			i1 += currentPalette[i][k1] & 0xff;
		}

		int l1 = (k / j1 << 16) + (l / j1 << 8) + i1 / j1;
		l1 = method373(l1, 1.3999999999999999D);
		if(l1 == 0)
			l1 = 1;
		averageTextureColours[i] = l1;
		return l1;
	}

	public static void method370(int i)
	{
		if(texturesPixelBuffer[i] == null)
			return;
		textureRequestPixelBuffer[textureRequestBufferPointer++] = texturesPixelBuffer[i];
		texturesPixelBuffer[i] = null;
	}

	private static int[] getTexturePixels(int i)
	{
		textureLastUsed[i] = lastTextureRetrievalCount++;
		if(texturesPixelBuffer[i] != null)
			return texturesPixelBuffer[i];
		int ai[];
		if(textureRequestBufferPointer > 0)
		{
			ai = textureRequestPixelBuffer[--textureRequestBufferPointer];
			textureRequestPixelBuffer[textureRequestBufferPointer] = null;
		} else
		{
			int j = 0;
			int k = -1;
			for(int l = 0; l < textureCount; l++)
				if(texturesPixelBuffer[l] != null && (textureLastUsed[l] < j || k == -1))
				{
					j = textureLastUsed[l];
					k = l;
				}

			ai = texturesPixelBuffer[k];
			texturesPixelBuffer[k] = null;
		}
		texturesPixelBuffer[i] = ai;
		Background background = textures[i];
		int ai1[] = currentPalette[i];
		if(lowMem)
		{
			textureIsTransparant[i] = false;
			for(int i1 = 0; i1 < 4096; i1++)
			{
				int i2 = ai[i1] = ai1[background.aByteArray1450[i1]] & 0xf8f8ff;
				if(i2 == 0)
					textureIsTransparant[i] = true;
				ai[4096 + i1] = i2 - (i2 >>> 3) & 0xf8f8ff;
				ai[8192 + i1] = i2 - (i2 >>> 2) & 0xf8f8ff;
				ai[12288 + i1] = i2 - (i2 >>> 2) - (i2 >>> 3) & 0xf8f8ff;
			}

		} else
		{
			if(background.anInt1452 == 64)
			{
				for(int j1 = 0; j1 < 128; j1++)
				{
					for(int j2 = 0; j2 < 128; j2++)
						ai[j2 + (j1 << 7)] = ai1[background.aByteArray1450[(j2 >> 1) + ((j1 >> 1) << 6)]];

				}

			} else
			{
				for(int k1 = 0; k1 < 16384; k1++)
					ai[k1] = ai1[background.aByteArray1450[k1]];

			}
			textureIsTransparant[i] = false;
			for(int l1 = 0; l1 < 16384; l1++)
			{
				ai[l1] &= 0xf8f8ff;
				int k2 = ai[l1];
				if(k2 == 0)
					textureIsTransparant[i] = true;
				ai[16384 + l1] = k2 - (k2 >>> 3) & 0xf8f8ff;
				ai[32768 + l1] = k2 - (k2 >>> 2) & 0xf8f8ff;
				ai[49152 + l1] = k2 - (k2 >>> 2) - (k2 >>> 3) & 0xf8f8ff;
			}

		}
		return ai;
	}

	public static void method372(double d)
	{
		d += Math.random() * 0.029999999999999999D - 0.014999999999999999D;
		int j = 0;
		for(int k = 0; k < 512; k++)
		{
			double d1 = (double)(k / 8) / 64D + 0.0078125D;
			double d2 = (double)(k & 7) / 8D + 0.0625D;
			for(int k1 = 0; k1 < 128; k1++)
			{
				double d3 = (double)k1 / 128D;
				double d4 = d3;
				double d5 = d3;
				double d6 = d3;
				if(d2 != 0.0D)
				{
					double d7;
					if(d3 < 0.5D)
						d7 = d3 * (1.0D + d2);
					else
						d7 = (d3 + d2) - d3 * d2;
					double d8 = 2D * d3 - d7;
					double d9 = d1 + 0.33333333333333331D;
					if(d9 > 1.0D)
						d9--;
					double d10 = d1;
					double d11 = d1 - 0.33333333333333331D;
					if(d11 < 0.0D)
						d11++;
					if(6D * d9 < 1.0D)
						d4 = d8 + (d7 - d8) * 6D * d9;
					else
					if(2D * d9 < 1.0D)
						d4 = d7;
					else
					if(3D * d9 < 2D)
						d4 = d8 + (d7 - d8) * (0.66666666666666663D - d9) * 6D;
					else
						d4 = d8;
					if(6D * d10 < 1.0D)
						d5 = d8 + (d7 - d8) * 6D * d10;
					else
					if(2D * d10 < 1.0D)
						d5 = d7;
					else
					if(3D * d10 < 2D)
						d5 = d8 + (d7 - d8) * (0.66666666666666663D - d10) * 6D;
					else
						d5 = d8;
					if(6D * d11 < 1.0D)
						d6 = d8 + (d7 - d8) * 6D * d11;
					else
					if(2D * d11 < 1.0D)
						d6 = d7;
					else
					if(3D * d11 < 2D)
						d6 = d8 + (d7 - d8) * (0.66666666666666663D - d11) * 6D;
					else
						d6 = d8;
				}
				int l1 = (int)(d4 * 256D);
				int i2 = (int)(d5 * 256D);
				int j2 = (int)(d6 * 256D);
				int k2 = (l1 << 16) + (i2 << 8) + j2;
				k2 = method373(k2, d);
				if(k2 == 0)
					k2 = 1;
				hslToRgb[j++] = k2;
			}

		}

		for(int l = 0; l < textureAmount; l++)
			if(textures[l] != null)
			{
				int ai[] = textures[l].anIntArray1451;
				currentPalette[l] = new int[ai.length];
				for(int j1 = 0; j1 < ai.length; j1++)
				{
					currentPalette[l][j1] = method373(ai[j1], d);
					if((currentPalette[l][j1] & 0xf8f8ff) == 0 && j1 != 0)
						currentPalette[l][j1] = 1;
				}

			}

		for(int i1 = 0; i1 < textureAmount; i1++)
			method370(i1);

	}

	private static int method373(int i, double d)
	{
		double d1 = (double)(i >> 16) / 256D;
		double d2 = (double)(i >> 8 & 0xff) / 256D;
		double d3 = (double)(i & 0xff) / 256D;
		d1 = Math.pow(d1, d);
		d2 = Math.pow(d2, d);
		d3 = Math.pow(d3, d);
		int j = (int)(d1 * 256D);
		int k = (int)(d2 * 256D);
		int l = (int)(d3 * 256D);
		return (j << 16) + (k << 8) + l;
	}

	public static void drawShadedTriangle(int i, int j, int k, int l, int i1, int j1, int k1, int l1,
										  int i2)
	{
		int j2 = 0;
		int k2 = 0;
		if(j != i)
		{
			j2 = (i1 - l << 16) / (j - i);
			k2 = (l1 - k1 << 15) / (j - i);
		}
		int l2 = 0;
		int i3 = 0;
		if(k != j)
		{
			l2 = (j1 - i1 << 16) / (k - j);
			i3 = (i2 - l1 << 15) / (k - j);
		}
		int j3 = 0;
		int k3 = 0;
		if(k != i)
		{
			j3 = (l - j1 << 16) / (i - k);
			k3 = (k1 - i2 << 15) / (i - k);
		}
		if(i <= j && i <= k)
		{
			if(i >= DrawingArea.bottomY)
				return;
			if(j > DrawingArea.bottomY)
				j = DrawingArea.bottomY;
			if(k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if(j < k)
			{
				j1 = l <<= 16;
				i2 = k1 <<= 15;
				if(i < 0)
				{
					j1 -= j3 * i;
					l -= j2 * i;
					i2 -= k3 * i;
					k1 -= k2 * i;
					i = 0;
				}
				i1 <<= 16;
				l1 <<= 15;
				if(j < 0)
				{
					i1 -= l2 * j;
					l1 -= i3 * j;
					j = 0;
				}
				if(i != j && j3 < j2 || i == j && j3 > l2)
				{
					k -= j;
					j -= i;
					for(i = anIntArray1472[i]; --j >= 0; i += DrawingArea.width)
					{
						method375(DrawingArea.pixels, i, j1 >> 16, l >> 16, i2 >> 7, k1 >> 7);
						j1 += j3;
						l += j2;
						i2 += k3;
						k1 += k2;
					}

					while(--k >= 0) 
					{
						method375(DrawingArea.pixels, i, j1 >> 16, i1 >> 16, i2 >> 7, l1 >> 7);
						j1 += j3;
						i1 += l2;
						i2 += k3;
						l1 += i3;
						i += DrawingArea.width;
					}
					return;
				}
				k -= j;
				j -= i;
				for(i = anIntArray1472[i]; --j >= 0; i += DrawingArea.width)
				{
					method375(DrawingArea.pixels, i, l >> 16, j1 >> 16, k1 >> 7, i2 >> 7);
					j1 += j3;
					l += j2;
					i2 += k3;
					k1 += k2;
				}

				while(--k >= 0) 
				{
					method375(DrawingArea.pixels, i, i1 >> 16, j1 >> 16, l1 >> 7, i2 >> 7);
					j1 += j3;
					i1 += l2;
					i2 += k3;
					l1 += i3;
					i += DrawingArea.width;
				}
				return;
			}
			i1 = l <<= 16;
			l1 = k1 <<= 15;
			if(i < 0)
			{
				i1 -= j3 * i;
				l -= j2 * i;
				l1 -= k3 * i;
				k1 -= k2 * i;
				i = 0;
			}
			j1 <<= 16;
			i2 <<= 15;
			if(k < 0)
			{
				j1 -= l2 * k;
				i2 -= i3 * k;
				k = 0;
			}
			if(i != k && j3 < j2 || i == k && l2 > j2)
			{
				j -= k;
				k -= i;
				for(i = anIntArray1472[i]; --k >= 0; i += DrawingArea.width)
				{
					method375(DrawingArea.pixels, i, i1 >> 16, l >> 16, l1 >> 7, k1 >> 7);
					i1 += j3;
					l += j2;
					l1 += k3;
					k1 += k2;
				}

				while(--j >= 0) 
				{
					method375(DrawingArea.pixels, i, j1 >> 16, l >> 16, i2 >> 7, k1 >> 7);
					j1 += l2;
					l += j2;
					i2 += i3;
					k1 += k2;
					i += DrawingArea.width;
				}
				return;
			}
			j -= k;
			k -= i;
			for(i = anIntArray1472[i]; --k >= 0; i += DrawingArea.width)
			{
				method375(DrawingArea.pixels, i, l >> 16, i1 >> 16, k1 >> 7, l1 >> 7);
				i1 += j3;
				l += j2;
				l1 += k3;
				k1 += k2;
			}

			while(--j >= 0) 
			{
				method375(DrawingArea.pixels, i, l >> 16, j1 >> 16, k1 >> 7, i2 >> 7);
				j1 += l2;
				l += j2;
				i2 += i3;
				k1 += k2;
				i += DrawingArea.width;
			}
			return;
		}
		if(j <= k)
		{
			if(j >= DrawingArea.bottomY)
				return;
			if(k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if(i > DrawingArea.bottomY)
				i = DrawingArea.bottomY;
			if(k < i)
			{
				l = i1 <<= 16;
				k1 = l1 <<= 15;
				if(j < 0)
				{
					l -= j2 * j;
					i1 -= l2 * j;
					k1 -= k2 * j;
					l1 -= i3 * j;
					j = 0;
				}
				j1 <<= 16;
				i2 <<= 15;
				if(k < 0)
				{
					j1 -= j3 * k;
					i2 -= k3 * k;
					k = 0;
				}
				if(j != k && j2 < l2 || j == k && j2 > j3)
				{
					i -= k;
					k -= j;
					for(j = anIntArray1472[j]; --k >= 0; j += DrawingArea.width)
					{
						method375(DrawingArea.pixels, j, l >> 16, i1 >> 16, k1 >> 7, l1 >> 7);
						l += j2;
						i1 += l2;
						k1 += k2;
						l1 += i3;
					}

					while(--i >= 0) 
					{
						method375(DrawingArea.pixels, j, l >> 16, j1 >> 16, k1 >> 7, i2 >> 7);
						l += j2;
						j1 += j3;
						k1 += k2;
						i2 += k3;
						j += DrawingArea.width;
					}
					return;
				}
				i -= k;
				k -= j;
				for(j = anIntArray1472[j]; --k >= 0; j += DrawingArea.width)
				{
					method375(DrawingArea.pixels, j, i1 >> 16, l >> 16, l1 >> 7, k1 >> 7);
					l += j2;
					i1 += l2;
					k1 += k2;
					l1 += i3;
				}

				while(--i >= 0) 
				{
					method375(DrawingArea.pixels, j, j1 >> 16, l >> 16, i2 >> 7, k1 >> 7);
					l += j2;
					j1 += j3;
					k1 += k2;
					i2 += k3;
					j += DrawingArea.width;
				}
				return;
			}
			j1 = i1 <<= 16;
			i2 = l1 <<= 15;
			if(j < 0)
			{
				j1 -= j2 * j;
				i1 -= l2 * j;
				i2 -= k2 * j;
				l1 -= i3 * j;
				j = 0;
			}
			l <<= 16;
			k1 <<= 15;
			if(i < 0)
			{
				l -= j3 * i;
				k1 -= k3 * i;
				i = 0;
			}
			if(j2 < l2)
			{
				k -= i;
				i -= j;
				for(j = anIntArray1472[j]; --i >= 0; j += DrawingArea.width)
				{
					method375(DrawingArea.pixels, j, j1 >> 16, i1 >> 16, i2 >> 7, l1 >> 7);
					j1 += j2;
					i1 += l2;
					i2 += k2;
					l1 += i3;
				}

				while(--k >= 0) 
				{
					method375(DrawingArea.pixels, j, l >> 16, i1 >> 16, k1 >> 7, l1 >> 7);
					l += j3;
					i1 += l2;
					k1 += k3;
					l1 += i3;
					j += DrawingArea.width;
				}
				return;
			}
			k -= i;
			i -= j;
			for(j = anIntArray1472[j]; --i >= 0; j += DrawingArea.width)
			{
				method375(DrawingArea.pixels, j, i1 >> 16, j1 >> 16, l1 >> 7, i2 >> 7);
				j1 += j2;
				i1 += l2;
				i2 += k2;
				l1 += i3;
			}

			while(--k >= 0) 
			{
				method375(DrawingArea.pixels, j, i1 >> 16, l >> 16, l1 >> 7, k1 >> 7);
				l += j3;
				i1 += l2;
				k1 += k3;
				l1 += i3;
				j += DrawingArea.width;
			}
			return;
		}
		if(k >= DrawingArea.bottomY)
			return;
		if(i > DrawingArea.bottomY)
			i = DrawingArea.bottomY;
		if(j > DrawingArea.bottomY)
			j = DrawingArea.bottomY;
		if(i < j)
		{
			i1 = j1 <<= 16;
			l1 = i2 <<= 15;
			if(k < 0)
			{
				i1 -= l2 * k;
				j1 -= j3 * k;
				l1 -= i3 * k;
				i2 -= k3 * k;
				k = 0;
			}
			l <<= 16;
			k1 <<= 15;
			if(i < 0)
			{
				l -= j2 * i;
				k1 -= k2 * i;
				i = 0;
			}
			if(l2 < j3)
			{
				j -= i;
				i -= k;
				for(k = anIntArray1472[k]; --i >= 0; k += DrawingArea.width)
				{
					method375(DrawingArea.pixels, k, i1 >> 16, j1 >> 16, l1 >> 7, i2 >> 7);
					i1 += l2;
					j1 += j3;
					l1 += i3;
					i2 += k3;
				}

				while(--j >= 0) 
				{
					method375(DrawingArea.pixels, k, i1 >> 16, l >> 16, l1 >> 7, k1 >> 7);
					i1 += l2;
					l += j2;
					l1 += i3;
					k1 += k2;
					k += DrawingArea.width;
				}
				return;
			}
			j -= i;
			i -= k;
			for(k = anIntArray1472[k]; --i >= 0; k += DrawingArea.width)
			{
				method375(DrawingArea.pixels, k, j1 >> 16, i1 >> 16, i2 >> 7, l1 >> 7);
				i1 += l2;
				j1 += j3;
				l1 += i3;
				i2 += k3;
			}

			while(--j >= 0) 
			{
				method375(DrawingArea.pixels, k, l >> 16, i1 >> 16, k1 >> 7, l1 >> 7);
				i1 += l2;
				l += j2;
				l1 += i3;
				k1 += k2;
				k += DrawingArea.width;
			}
			return;
		}
		l = j1 <<= 16;
		k1 = i2 <<= 15;
		if(k < 0)
		{
			l -= l2 * k;
			j1 -= j3 * k;
			k1 -= i3 * k;
			i2 -= k3 * k;
			k = 0;
		}
		i1 <<= 16;
		l1 <<= 15;
		if(j < 0)
		{
			i1 -= j2 * j;
			l1 -= k2 * j;
			j = 0;
		}
		if(l2 < j3)
		{
			i -= j;
			j -= k;
			for(k = anIntArray1472[k]; --j >= 0; k += DrawingArea.width)
			{
				method375(DrawingArea.pixels, k, l >> 16, j1 >> 16, k1 >> 7, i2 >> 7);
				l += l2;
				j1 += j3;
				k1 += i3;
				i2 += k3;
			}

			while(--i >= 0) 
			{
				method375(DrawingArea.pixels, k, i1 >> 16, j1 >> 16, l1 >> 7, i2 >> 7);
				i1 += j2;
				j1 += j3;
				l1 += k2;
				i2 += k3;
				k += DrawingArea.width;
			}
			return;
		}
		i -= j;
		j -= k;
		for(k = anIntArray1472[k]; --j >= 0; k += DrawingArea.width)
		{
			method375(DrawingArea.pixels, k, j1 >> 16, l >> 16, i2 >> 7, k1 >> 7);
			l += l2;
			j1 += j3;
			k1 += i3;
			i2 += k3;
		}

		while(--i >= 0) 
		{
			method375(DrawingArea.pixels, k, j1 >> 16, i1 >> 16, i2 >> 7, l1 >> 7);
			i1 += j2;
			j1 += j3;
			l1 += k2;
			i2 += k3;
			k += DrawingArea.width;
		}
	}

	private static void method375(int ai[], int i, int l, int i1, int j1, int k1)
	{
		int j;//was parameter
		int k;//was parameter
		if(aBoolean1464)
		{
			int l1;
			if(aBoolean1462)
			{
				if(i1 - l > 3)
					l1 = (k1 - j1) / (i1 - l);
				else
					l1 = 0;
				if(i1 > DrawingArea.centerX)
					i1 = DrawingArea.centerX;
				if(l < 0)
				{
					j1 -= l * l1;
					l = 0;
				}
				if(l >= i1)
					return;
				i += l;
				k = i1 - l >> 2;
				l1 <<= 2;
			} else
			{
				if(l >= i1)
					return;
				i += l;
				k = i1 - l >> 2;
				if(k > 0)
					l1 = (k1 - j1) * anIntArray1468[k] >> 15;
				else
					l1 = 0;
			}
			if(anInt1465 == 0)
			{
				while(--k >= 0) 
				{
					j = hslToRgb[j1 >> 8];
					j1 += l1;
					ai[i++] = j;
					ai[i++] = j;
					ai[i++] = j;
					ai[i++] = j;
				}
				k = i1 - l & 3;
				if(k > 0)
				{
					j = hslToRgb[j1 >> 8];
					do
						ai[i++] = j;
					while(--k > 0);
					return;
				}
			} else
			{
				int j2 = anInt1465;
				int l2 = 256 - anInt1465;
				while(--k >= 0) 
				{
					j = hslToRgb[j1 >> 8];
					j1 += l1;
					j = ((j & 0xff00ff) * l2 >> 8 & 0xff00ff) + ((j & 0xff00) * l2 >> 8 & 0xff00);
					ai[i++] = j + ((ai[i] & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j2 >> 8 & 0xff00);
					ai[i++] = j + ((ai[i] & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j2 >> 8 & 0xff00);
					ai[i++] = j + ((ai[i] & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j2 >> 8 & 0xff00);
					ai[i++] = j + ((ai[i] & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j2 >> 8 & 0xff00);
				}
				k = i1 - l & 3;
				if(k > 0)
				{
					j = hslToRgb[j1 >> 8];
					j = ((j & 0xff00ff) * l2 >> 8 & 0xff00ff) + ((j & 0xff00) * l2 >> 8 & 0xff00);
					do
						ai[i++] = j + ((ai[i] & 0xff00ff) * j2 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j2 >> 8 & 0xff00);
					while(--k > 0);
				}
			}
			return;
		}
		if(l >= i1)
			return;
		int i2 = (k1 - j1) / (i1 - l);
		if(aBoolean1462)
		{
			if(i1 > DrawingArea.centerX)
				i1 = DrawingArea.centerX;
			if(l < 0)
			{
				j1 -= l * i2;
				l = 0;
			}
			if(l >= i1)
				return;
		}
		i += l;
		k = i1 - l;
		if(anInt1465 == 0)
		{
			do
			{
				ai[i++] = hslToRgb[j1 >> 8];
				j1 += i2;
			} while(--k > 0);
			return;
		}
		int k2 = anInt1465;
		int i3 = 256 - anInt1465;
		do
		{
			j = hslToRgb[j1 >> 8];
			j1 += i2;
			j = ((j & 0xff00ff) * i3 >> 8 & 0xff00ff) + ((j & 0xff00) * i3 >> 8 & 0xff00);
			ai[i++] = j + ((ai[i] & 0xff00ff) * k2 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * k2 >> 8 & 0xff00);
		} while(--k > 0);
	}

	public static void drawFlatTriangle(int i, int j, int k, int l, int i1, int j1, int k1)
	{
		int l1 = 0;
		if(j != i)
			l1 = (i1 - l << 16) / (j - i);
		int i2 = 0;
		if(k != j)
			i2 = (j1 - i1 << 16) / (k - j);
		int j2 = 0;
		if(k != i)
			j2 = (l - j1 << 16) / (i - k);
		if(i <= j && i <= k)
		{
			if(i >= DrawingArea.bottomY)
				return;
			if(j > DrawingArea.bottomY)
				j = DrawingArea.bottomY;
			if(k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if(j < k)
			{
				j1 = l <<= 16;
				if(i < 0)
				{
					j1 -= j2 * i;
					l -= l1 * i;
					i = 0;
				}
				i1 <<= 16;
				if(j < 0)
				{
					i1 -= i2 * j;
					j = 0;
				}
				if(i != j && j2 < l1 || i == j && j2 > i2)
				{
					k -= j;
					j -= i;
					for(i = anIntArray1472[i]; --j >= 0; i += DrawingArea.width)
					{
						method377(DrawingArea.pixels, i, k1, j1 >> 16, l >> 16);
						j1 += j2;
						l += l1;
					}

					while(--k >= 0) 
					{
						method377(DrawingArea.pixels, i, k1, j1 >> 16, i1 >> 16);
						j1 += j2;
						i1 += i2;
						i += DrawingArea.width;
					}
					return;
				}
				k -= j;
				j -= i;
				for(i = anIntArray1472[i]; --j >= 0; i += DrawingArea.width)
				{
					method377(DrawingArea.pixels, i, k1, l >> 16, j1 >> 16);
					j1 += j2;
					l += l1;
				}

				while(--k >= 0) 
				{
					method377(DrawingArea.pixels, i, k1, i1 >> 16, j1 >> 16);
					j1 += j2;
					i1 += i2;
					i += DrawingArea.width;
				}
				return;
			}
			i1 = l <<= 16;
			if(i < 0)
			{
				i1 -= j2 * i;
				l -= l1 * i;
				i = 0;
			}
			j1 <<= 16;
			if(k < 0)
			{
				j1 -= i2 * k;
				k = 0;
			}
			if(i != k && j2 < l1 || i == k && i2 > l1)
			{
				j -= k;
				k -= i;
				for(i = anIntArray1472[i]; --k >= 0; i += DrawingArea.width)
				{
					method377(DrawingArea.pixels, i, k1, i1 >> 16, l >> 16);
					i1 += j2;
					l += l1;
				}

				while(--j >= 0) 
				{
					method377(DrawingArea.pixels, i, k1, j1 >> 16, l >> 16);
					j1 += i2;
					l += l1;
					i += DrawingArea.width;
				}
				return;
			}
			j -= k;
			k -= i;
			for(i = anIntArray1472[i]; --k >= 0; i += DrawingArea.width)
			{
				method377(DrawingArea.pixels, i, k1, l >> 16, i1 >> 16);
				i1 += j2;
				l += l1;
			}

			while(--j >= 0) 
			{
				method377(DrawingArea.pixels, i, k1, l >> 16, j1 >> 16);
				j1 += i2;
				l += l1;
				i += DrawingArea.width;
			}
			return;
		}
		if(j <= k)
		{
			if(j >= DrawingArea.bottomY)
				return;
			if(k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if(i > DrawingArea.bottomY)
				i = DrawingArea.bottomY;
			if(k < i)
			{
				l = i1 <<= 16;
				if(j < 0)
				{
					l -= l1 * j;
					i1 -= i2 * j;
					j = 0;
				}
				j1 <<= 16;
				if(k < 0)
				{
					j1 -= j2 * k;
					k = 0;
				}
				if(j != k && l1 < i2 || j == k && l1 > j2)
				{
					i -= k;
					k -= j;
					for(j = anIntArray1472[j]; --k >= 0; j += DrawingArea.width)
					{
						method377(DrawingArea.pixels, j, k1, l >> 16, i1 >> 16);
						l += l1;
						i1 += i2;
					}

					while(--i >= 0) 
					{
						method377(DrawingArea.pixels, j, k1, l >> 16, j1 >> 16);
						l += l1;
						j1 += j2;
						j += DrawingArea.width;
					}
					return;
				}
				i -= k;
				k -= j;
				for(j = anIntArray1472[j]; --k >= 0; j += DrawingArea.width)
				{
					method377(DrawingArea.pixels, j, k1, i1 >> 16, l >> 16);
					l += l1;
					i1 += i2;
				}

				while(--i >= 0) 
				{
					method377(DrawingArea.pixels, j, k1, j1 >> 16, l >> 16);
					l += l1;
					j1 += j2;
					j += DrawingArea.width;
				}
				return;
			}
			j1 = i1 <<= 16;
			if(j < 0)
			{
				j1 -= l1 * j;
				i1 -= i2 * j;
				j = 0;
			}
			l <<= 16;
			if(i < 0)
			{
				l -= j2 * i;
				i = 0;
			}
			if(l1 < i2)
			{
				k -= i;
				i -= j;
				for(j = anIntArray1472[j]; --i >= 0; j += DrawingArea.width)
				{
					method377(DrawingArea.pixels, j, k1, j1 >> 16, i1 >> 16);
					j1 += l1;
					i1 += i2;
				}

				while(--k >= 0) 
				{
					method377(DrawingArea.pixels, j, k1, l >> 16, i1 >> 16);
					l += j2;
					i1 += i2;
					j += DrawingArea.width;
				}
				return;
			}
			k -= i;
			i -= j;
			for(j = anIntArray1472[j]; --i >= 0; j += DrawingArea.width)
			{
				method377(DrawingArea.pixels, j, k1, i1 >> 16, j1 >> 16);
				j1 += l1;
				i1 += i2;
			}

			while(--k >= 0) 
			{
				method377(DrawingArea.pixels, j, k1, i1 >> 16, l >> 16);
				l += j2;
				i1 += i2;
				j += DrawingArea.width;
			}
			return;
		}
		if(k >= DrawingArea.bottomY)
			return;
		if(i > DrawingArea.bottomY)
			i = DrawingArea.bottomY;
		if(j > DrawingArea.bottomY)
			j = DrawingArea.bottomY;
		if(i < j)
		{
			i1 = j1 <<= 16;
			if(k < 0)
			{
				i1 -= i2 * k;
				j1 -= j2 * k;
				k = 0;
			}
			l <<= 16;
			if(i < 0)
			{
				l -= l1 * i;
				i = 0;
			}
			if(i2 < j2)
			{
				j -= i;
				i -= k;
				for(k = anIntArray1472[k]; --i >= 0; k += DrawingArea.width)
				{
					method377(DrawingArea.pixels, k, k1, i1 >> 16, j1 >> 16);
					i1 += i2;
					j1 += j2;
				}

				while(--j >= 0) 
				{
					method377(DrawingArea.pixels, k, k1, i1 >> 16, l >> 16);
					i1 += i2;
					l += l1;
					k += DrawingArea.width;
				}
				return;
			}
			j -= i;
			i -= k;
			for(k = anIntArray1472[k]; --i >= 0; k += DrawingArea.width)
			{
				method377(DrawingArea.pixels, k, k1, j1 >> 16, i1 >> 16);
				i1 += i2;
				j1 += j2;
			}

			while(--j >= 0) 
			{
				method377(DrawingArea.pixels, k, k1, l >> 16, i1 >> 16);
				i1 += i2;
				l += l1;
				k += DrawingArea.width;
			}
			return;
		}
		l = j1 <<= 16;
		if(k < 0)
		{
			l -= i2 * k;
			j1 -= j2 * k;
			k = 0;
		}
		i1 <<= 16;
		if(j < 0)
		{
			i1 -= l1 * j;
			j = 0;
		}
		if(i2 < j2)
		{
			i -= j;
			j -= k;
			for(k = anIntArray1472[k]; --j >= 0; k += DrawingArea.width)
			{
				method377(DrawingArea.pixels, k, k1, l >> 16, j1 >> 16);
				l += i2;
				j1 += j2;
			}

			while(--i >= 0) 
			{
				method377(DrawingArea.pixels, k, k1, i1 >> 16, j1 >> 16);
				i1 += l1;
				j1 += j2;
				k += DrawingArea.width;
			}
			return;
		}
		i -= j;
		j -= k;
		for(k = anIntArray1472[k]; --j >= 0; k += DrawingArea.width)
		{
			method377(DrawingArea.pixels, k, k1, j1 >> 16, l >> 16);
			l += i2;
			j1 += j2;
		}

		while(--i >= 0) 
		{
			method377(DrawingArea.pixels, k, k1, j1 >> 16, i1 >> 16);
			i1 += l1;
			j1 += j2;
			k += DrawingArea.width;
		}
	}

	private static void method377(int ai[], int i, int j, int l, int i1)
	{
		int k;//was parameter
		if(aBoolean1462)
		{
			if(i1 > DrawingArea.centerX)
				i1 = DrawingArea.centerX;
			if(l < 0)
				l = 0;
		}
		if(l >= i1)
			return;
		i += l;
		k = i1 - l >> 2;
		if(anInt1465 == 0)
		{
			while(--k >= 0) 
			{
				ai[i++] = j;
				ai[i++] = j;
				ai[i++] = j;
				ai[i++] = j;
			}
			for(k = i1 - l & 3; --k >= 0;)
				ai[i++] = j;

			return;
		}
		int j1 = anInt1465;
		int k1 = 256 - anInt1465;
		j = ((j & 0xff00ff) * k1 >> 8 & 0xff00ff) + ((j & 0xff00) * k1 >> 8 & 0xff00);
		while(--k >= 0) 
		{
			ai[i++] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);
			ai[i++] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);
			ai[i++] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);
			ai[i++] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);
		}
		for(k = i1 - l & 3; --k >= 0;)
			ai[i++] = j + ((ai[i] & 0xff00ff) * j1 >> 8 & 0xff00ff) + ((ai[i] & 0xff00) * j1 >> 8 & 0xff00);

	}
	public static void drawTexturedTriangle(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18) {
		int[] texturePixels = getTexturePixels(var18);
		int var21;
		aBoolean1463 = !textureIsTransparant[var18];
		var21 = var4 - var3;
		int var26 = var1 - var0;
		int var27 = var5 - var3;
		int var31 = var2 - var0;
		int var28 = var7 - var6;
		int var23 = var8 - var6;
		int var29 = 0;
		if(var1 != var0) {
			var29 = (var4 - var3 << 16) / (var1 - var0);
		}

		int var30 = 0;
		if(var2 != var1) {
			var30 = (var5 - var4 << 16) / (var2 - var1);
		}

		int var22 = 0;
		if(var2 != var0) {
			var22 = (var3 - var5 << 16) / (var0 - var2);
		}

		int var32 = var21 * var31 - var27 * var26;
		if(var32 != 0) {
			int var41 = (var28 * var31 - var23 * var26 << 9) / var32;
			int var20 = (var23 * var21 - var28 * var27 << 9) / var32;
			var10 = var9 - var10;
			var13 = var12 - var13;
			var16 = var15 - var16;
			var11 -= var9;
			var14 -= var12;
			var17 -= var15;
			final int FOV = 512;
			int var24 = var11 * var12 - var14 * var9 << 14;
			int var38 = (int)(((long)(var14 * var15 - var17 * var12) << 3 << 14) / (long)FOV);
			int var25 = (int)(((long)(var17 * var9 - var11 * var15) << 14) / (long)FOV);
			int var36 = var10 * var12 - var13 * var9 << 14;
			int var39 = (int)(((long)(var13 * var15 - var16 * var12) << 3 << 14) / (long)FOV);
			int var37 = (int)(((long)(var16 * var9 - var10 * var15) << 14) / (long)FOV);
			int var33 = var13 * var11 - var10 * var14 << 14;
			int var40 = (int)(((long)(var16 * var14 - var13 * var17) << 3 << 14) / (long)FOV);
			int var34 = (int)(((long)(var10 * var17 - var16 * var11) << 14) / (long)FOV);


			int var35;
			if(var0 <= var1 && var0 <= var2) {
				if(var0 < DrawingArea.bottomY) {
					if(var1 > DrawingArea.bottomY) {
						var1 = DrawingArea.bottomY;
					}

					if(var2 > DrawingArea.bottomY) {
						var2 = DrawingArea.bottomY;
					}

					var6 = (var6 << 9) - var41 * var3 + var41;
					if(var1 < var2) {
						var5 = var3 <<= 16;
						if(var0 < 0) {
							var5 -= var22 * var0;
							var3 -= var29 * var0;
							var6 -= var20 * var0;
							var0 = 0;
						}

						var4 <<= 16;
						if(var1 < 0) {
							var4 -= var30 * var1;
							var1 = 0;
						}

						var35 = var0 - textureInt2;
						var24 += var25 * var35;
						var36 += var37 * var35;
						var33 += var34 * var35;
						if((var0 == var1 || var22 >= var29) && (var0 != var1 || var22 <= var30)) {
							var2 -= var1;
							var1 -= var0;
							var0 = anIntArray1472[var0];

							while(true) {
								--var1;
								if(var1 < 0) {
									while(true) {
										--var2;
										if(var2 < 0) {
											return;
										}

										drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var0, var4 >> 16, var5 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
										var5 += var22;
										var4 += var30;
										var6 += var20;
										var0 += DrawingArea.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var0, var3 >> 16, var5 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
								var5 += var22;
								var3 += var29;
								var6 += var20;
								var0 += DrawingArea.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						} else {
							var2 -= var1;
							var1 -= var0;
							var0 = anIntArray1472[var0];

							while(true) {
								--var1;
								if(var1 < 0) {
									while(true) {
										--var2;
										if(var2 < 0) {
											return;
										}

										drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var0, var5 >> 16, var4 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
										var5 += var22;
										var4 += var30;
										var6 += var20;
										var0 += DrawingArea.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var0, var5 >> 16, var3 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
								var5 += var22;
								var3 += var29;
								var6 += var20;
								var0 += DrawingArea.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						}
					} else {
						var4 = var3 <<= 16;
						if(var0 < 0) {
							var4 -= var22 * var0;
							var3 -= var29 * var0;
							var6 -= var20 * var0;
							var0 = 0;
						}

						var5 <<= 16;
						if(var2 < 0) {
							var5 -= var30 * var2;
							var2 = 0;
						}

						var35 = var0 - textureInt2;
						var24 += var25 * var35;
						var36 += var37 * var35;
						var33 += var34 * var35;
						if((var0 == var2 || var22 >= var29) && (var0 != var2 || var30 <= var29)) {
							var1 -= var2;
							var2 -= var0;
							var0 = anIntArray1472[var0];

							while(true) {
								--var2;
								if(var2 < 0) {
									while(true) {
										--var1;
										if(var1 < 0) {
											return;
										}

										drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var0, var3 >> 16, var5 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
										var5 += var30;
										var3 += var29;
										var6 += var20;
										var0 += DrawingArea.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var0, var3 >> 16, var4 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
								var4 += var22;
								var3 += var29;
								var6 += var20;
								var0 += DrawingArea.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						} else {
							var1 -= var2;
							var2 -= var0;
							var0 = anIntArray1472[var0];

							while(true) {
								--var2;
								if(var2 < 0) {
									while(true) {
										--var1;
										if(var1 < 0) {
											return;
										}

										drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var0, var5 >> 16, var3 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
										var5 += var30;
										var3 += var29;
										var6 += var20;
										var0 += DrawingArea.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var0, var4 >> 16, var3 >> 16, var6, var41, var24, var36, var33, var38, var39, var40);
								var4 += var22;
								var3 += var29;
								var6 += var20;
								var0 += DrawingArea.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						}
					}
				}
			} else if(var1 <= var2) {
				if(var1 < DrawingArea.bottomY) {
					if(var2 > DrawingArea.bottomY) {
						var2 = DrawingArea.bottomY;
					}

					if(var0 > DrawingArea.bottomY) {
						var0 = DrawingArea.bottomY;
					}

					var7 = (var7 << 9) - var41 * var4 + var41;
					if(var2 < var0) {
						var3 = var4 <<= 16;
						if(var1 < 0) {
							var3 -= var29 * var1;
							var4 -= var30 * var1;
							var7 -= var20 * var1;
							var1 = 0;
						}

						var5 <<= 16;
						if(var2 < 0) {
							var5 -= var22 * var2;
							var2 = 0;
						}

						var35 = var1 - textureInt2;
						var24 += var25 * var35;
						var36 += var37 * var35;
						var33 += var34 * var35;
						if((var1 == var2 || var29 >= var30) && (var1 != var2 || var29 <= var22)) {
							var0 -= var2;
							var2 -= var1;
							var1 = anIntArray1472[var1];

							while(true) {
								--var2;
								if(var2 < 0) {
									while(true) {
										--var0;
										if(var0 < 0) {
											return;
										}

										drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var1, var5 >> 16, var3 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
										var3 += var29;
										var5 += var22;
										var7 += var20;
										var1 += DrawingArea.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var1, var4 >> 16, var3 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
								var3 += var29;
								var4 += var30;
								var7 += var20;
								var1 += DrawingArea.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						} else {
							var0 -= var2;
							var2 -= var1;
							var1 = anIntArray1472[var1];

							while(true) {
								--var2;
								if(var2 < 0) {
									while(true) {
										--var0;
										if(var0 < 0) {
											return;
										}

										drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var1, var3 >> 16, var5 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
										var3 += var29;
										var5 += var22;
										var7 += var20;
										var1 += DrawingArea.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var1, var3 >> 16, var4 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
								var3 += var29;
								var4 += var30;
								var7 += var20;
								var1 += DrawingArea.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						}
					} else {
						var5 = var4 <<= 16;
						if(var1 < 0) {
							var5 -= var29 * var1;
							var4 -= var30 * var1;
							var7 -= var20 * var1;
							var1 = 0;
						}

						var3 <<= 16;
						if(var0 < 0) {
							var3 -= var22 * var0;
							var0 = 0;
						}

						var35 = var1 - textureInt2;
						var24 += var25 * var35;
						var36 += var37 * var35;
						var33 += var34 * var35;
						if(var29 < var30) {
							var2 -= var0;
							var0 -= var1;
							var1 = anIntArray1472[var1];

							while(true) {
								--var0;
								if(var0 < 0) {
									while(true) {
										--var2;
										if(var2 < 0) {
											return;
										}

										drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var1, var3 >> 16, var4 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
										var3 += var22;
										var4 += var30;
										var7 += var20;
										var1 += DrawingArea.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var1, var5 >> 16, var4 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
								var5 += var29;
								var4 += var30;
								var7 += var20;
								var1 += DrawingArea.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						} else {
							var2 -= var0;
							var0 -= var1;
							var1 = anIntArray1472[var1];

							while(true) {
								--var0;
								if(var0 < 0) {
									while(true) {
										--var2;
										if(var2 < 0) {
											return;
										}

										drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var1, var4 >> 16, var3 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
										var3 += var22;
										var4 += var30;
										var7 += var20;
										var1 += DrawingArea.width;
										var24 += var25;
										var36 += var37;
										var33 += var34;
									}
								}

								drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var1, var4 >> 16, var5 >> 16, var7, var41, var24, var36, var33, var38, var39, var40);
								var5 += var29;
								var4 += var30;
								var7 += var20;
								var1 += DrawingArea.width;
								var24 += var25;
								var36 += var37;
								var33 += var34;
							}
						}
					}
				}
			} else if(var2 < DrawingArea.bottomY) {
				if(var0 > DrawingArea.bottomY) {
					var0 = DrawingArea.bottomY;
				}

				if(var1 > DrawingArea.bottomY) {
					var1 = DrawingArea.bottomY;
				}

				var8 = (var8 << 9) - var41 * var5 + var41;
				if(var0 < var1) {
					var4 = var5 <<= 16;
					if(var2 < 0) {
						var4 -= var30 * var2;
						var5 -= var22 * var2;
						var8 -= var20 * var2;
						var2 = 0;
					}

					var3 <<= 16;
					if(var0 < 0) {
						var3 -= var29 * var0;
						var0 = 0;
					}

					var35 = var2 - textureInt2;
					var24 += var25 * var35;
					var36 += var37 * var35;
					var33 += var34 * var35;
					if(var30 < var22) {
						var1 -= var0;
						var0 -= var2;
						var2 = anIntArray1472[var2];

						while(true) {
							--var0;
							if(var0 < 0) {
								while(true) {
									--var1;
									if(var1 < 0) {
										return;
									}

									drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var2, var4 >> 16, var3 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
									var4 += var30;
									var3 += var29;
									var8 += var20;
									var2 += DrawingArea.width;
									var24 += var25;
									var36 += var37;
									var33 += var34;
								}
							}

							drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var2, var4 >> 16, var5 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
							var4 += var30;
							var5 += var22;
							var8 += var20;
							var2 += DrawingArea.width;
							var24 += var25;
							var36 += var37;
							var33 += var34;
						}
					} else {
						var1 -= var0;
						var0 -= var2;
						var2 = anIntArray1472[var2];

						while(true) {
							--var0;
							if(var0 < 0) {
								while(true) {
									--var1;
									if(var1 < 0) {
										return;
									}

									drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var2, var3 >> 16, var4 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
									var4 += var30;
									var3 += var29;
									var8 += var20;
									var2 += DrawingArea.width;
									var24 += var25;
									var36 += var37;
									var33 += var34;
								}
							}

							drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var2, var5 >> 16, var4 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
							var4 += var30;
							var5 += var22;
							var8 += var20;
							var2 += DrawingArea.width;
							var24 += var25;
							var36 += var37;
							var33 += var34;
						}
					}
				} else {
					var3 = var5 <<= 16;
					if(var2 < 0) {
						var3 -= var30 * var2;
						var5 -= var22 * var2;
						var8 -= var20 * var2;
						var2 = 0;
					}

					var4 <<= 16;
					if(var1 < 0) {
						var4 -= var29 * var1;
						var1 = 0;
					}

					var35 = var2 - textureInt2;
					var24 += var25 * var35;
					var36 += var37 * var35;
					var33 += var34 * var35;
					if(var30 < var22) {
						var0 -= var1;
						var1 -= var2;
						var2 = anIntArray1472[var2];

						while(true) {
							--var1;
							if(var1 < 0) {
								while(true) {
									--var0;
									if(var0 < 0) {
										return;
									}

									drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var2, var4 >> 16, var5 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
									var4 += var29;
									var5 += var22;
									var8 += var20;
									var2 += DrawingArea.width;
									var24 += var25;
									var36 += var37;
									var33 += var34;
								}
							}

							drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var2, var3 >> 16, var5 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
							var3 += var30;
							var5 += var22;
							var8 += var20;
							var2 += DrawingArea.width;
							var24 += var25;
							var36 += var37;
							var33 += var34;
						}
					} else {
						var0 -= var1;
						var1 -= var2;
						var2 = anIntArray1472[var2];

						while(true) {
							--var1;
							if(var1 < 0) {
								while(true) {
									--var0;
									if(var0 < 0) {
										return;
									}

									drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var2, var5 >> 16, var4 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
									var4 += var29;
									var5 += var22;
									var8 += var20;
									var2 += DrawingArea.width;
									var24 += var25;
									var36 += var37;
									var33 += var34;
								}
							}

							drawTexturedLine(DrawingArea.pixels, texturePixels, 0, 0, var2, var5 >> 16, var3 >> 16, var8, var41, var24, var36, var33, var38, var39, var40);
							var3 += var30;
							var5 += var22;
							var8 += var20;
							var2 += DrawingArea.width;
							var24 += var25;
							var36 += var37;
							var33 += var34;
						}
					}
				}
			}
		}
	}

	static void drawTexturedLine(int[] var0, int[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12, int var13, int var14) {
		if(aBoolean1462) {
			if(var6 > DrawingArea.centerX) {
				var6 = DrawingArea.centerX;
			}

			if(var5 < 0) {
				var5 = 0;
			}
		}

		if(var5 < var6) {
			var4 += var5;
			var7 += var8 * var5;
			int var17 = var6 - var5;
			int var15;
			int var16;
			int var18;
			int var19;
			int var20;
			int var21;
			int var22;
			int var23;
			if(false) {
				var15 = var5 - textureInt1;
				var9 += (var12 >> 3) * var15;
				var10 += (var13 >> 3) * var15;
				var11 += (var14 >> 3) * var15;
				var19 = var11 >> 12;
				if(var19 != 0) {
					var20 = var9 / var19;
					var18 = var10 / var19;
					if(var20 < 0) {
						var20 = 0;
					} else if(var20 > 4032) {
						var20 = 4032;
					}
				} else {
					var20 = 0;
					var18 = 0;
				}

				var9 += var12;
				var10 += var13;
				var11 += var14;
				var19 = var11 >> 12;
				if(var19 != 0) {
					var22 = var9 / var19;
					var16 = var10 / var19;
					if(var22 < 0) {
						var22 = 0;
					} else if(var22 > 4032) {
						var22 = 4032;
					}
				} else {
					var22 = 0;
					var16 = 0;
				}

				var2 = (var20 << 20) + var18;
				var23 = (var22 - var20 >> 3 << 20) + (var16 - var18 >> 3);
				var17 >>= 3;
				var8 <<= 3;
				var21 = var7 >> 8;
				if(aBoolean1463) {
					if(var17 > 0) {
						do {
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var20 = var22;
							var18 = var16;
							var9 += var12;
							var10 += var13;
							var11 += var14;
							var19 = var11 >> 12;
							if(var19 != 0) {
								var22 = var9 / var19;
								var16 = var10 / var19;
								if(var22 < 0) {
									var22 = 0;
								} else if(var22 > 4032) {
									var22 = 4032;
								}
							} else {
								var22 = 0;
								var16 = 0;
							}

							var2 = (var20 << 20) + var18;
							var23 = (var22 - var20 >> 3 << 20) + (var16 - var18 >> 3);
							var7 += var8;
							var21 = var7 >> 8;
							--var17;
						} while(var17 > 0);
					}

					var17 = var6 - var5 & 7;
					if(var17 > 0) {
						do {
							var3 = var1[(var2 & 4032) + (var2 >>> 26)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							--var17;
						} while(var17 > 0);

					}
				} else {
					if(var17 > 0) {
						do {
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var20 = var22;
							var18 = var16;
							var9 += var12;
							var10 += var13;
							var11 += var14;
							var19 = var11 >> 12;
							if(var19 != 0) {
								var22 = var9 / var19;
								var16 = var10 / var19;
								if(var22 < 0) {
									var22 = 0;
								} else if(var22 > 4032) {
									var22 = 4032;
								}
							} else {
								var22 = 0;
								var16 = 0;
							}

							var2 = (var20 << 20) + var18;
							var23 = (var22 - var20 >> 3 << 20) + (var16 - var18 >> 3);
							var7 += var8;
							var21 = var7 >> 8;
							--var17;
						} while(var17 > 0);
					}

					var17 = var6 - var5 & 7;
					if(var17 > 0) {
						do {
							if((var3 = var1[(var2 & 4032) + (var2 >>> 26)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							--var17;
						} while(var17 > 0);

					}
				}
			} else {
				var15 = var5 - textureInt1;
				var9 += (var12 >> 3) * var15;
				var10 += (var13 >> 3) * var15;
				var11 += (var14 >> 3) * var15;
				var19 = var11 >> 14;
				if(var19 != 0) {
					var20 = var9 / var19;
					var18 = var10 / var19;
					if(var20 < 0) {
						var20 = 0;
					} else if(var20 > 16256) {
						var20 = 16256;
					}
				} else {
					var20 = 0;
					var18 = 0;
				}

				var9 += var12;
				var10 += var13;
				var11 += var14;
				var19 = var11 >> 14;
				if(var19 != 0) {
					var22 = var9 / var19;
					var16 = var10 / var19;
					if(var22 < 0) {
						var22 = 0;
					} else if(var22 > 16256) {
						var22 = 16256;
					}
				} else {
					var22 = 0;
					var16 = 0;
				}

				var2 = (var20 << 18) + var18;
				var23 = (var22 - var20 >> 3 << 18) + (var16 - var18 >> 3);
				var17 >>= 3;
				var8 <<= 3;
				var21 = var7 >> 8;
				if(aBoolean1463) {
					if(var17 > 0) {
						do {
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var20 = var22;
							var18 = var16;
							var9 += var12;
							var10 += var13;
							var11 += var14;
							var19 = var11 >> 14;
							if(var19 != 0) {
								var22 = var9 / var19;
								var16 = var10 / var19;
								if(var22 < 0) {
									var22 = 0;
								} else if(var22 > 16256) {
									var22 = 16256;
								}
							} else {
								var22 = 0;
								var16 = 0;
							}

							var2 = (var20 << 18) + var18;
							var23 = (var22 - var20 >> 3 << 18) + (var16 - var18 >> 3);
							var7 += var8;
							var21 = var7 >> 8;
							--var17;
						} while(var17 > 0);
					}

					var17 = var6 - var5 & 7;
					if(var17 > 0) {
						do {
							var3 = var1[(var2 & 16256) + (var2 >>> 25)];
							var0[var4++] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							var2 += var23;
							--var17;
						} while(var17 > 0);

					}
				} else {
					if(var17 > 0) {
						do {
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var20 = var22;
							var18 = var16;
							var9 += var12;
							var10 += var13;
							var11 += var14;
							var19 = var11 >> 14;
							if(var19 != 0) {
								var22 = var9 / var19;
								var16 = var10 / var19;
								if(var22 < 0) {
									var22 = 0;
								} else if(var22 > 16256) {
									var22 = 16256;
								}
							} else {
								var22 = 0;
								var16 = 0;
							}

							var2 = (var20 << 18) + var18;
							var23 = (var22 - var20 >> 3 << 18) + (var16 - var18 >> 3);
							var7 += var8;
							var21 = var7 >> 8;
							--var17;
						} while(var17 > 0);
					}

					var17 = var6 - var5 & 7;
					if(var17 > 0) {
						do {
							if((var3 = var1[(var2 & 16256) + (var2 >>> 25)]) != 0) {
								var0[var4] = ((var3 & 16711935) * var21 & -16711936) + ((var3 & '\uff00') * var21 & 16711680) >> 8;
							}

							++var4;
							var2 += var23;
							--var17;
						} while(var17 > 0);

					}
				}
			}
		}
	}

	public static void drawTexturedTriangle317(int i, int j, int k, int l, int i1, int j1, int k1, int l1,
											   int i2, int j2, int k2, int l2, int i3, int j3, int k3,
											   int l3, int i4, int j4, int k4)
	{
		int ai[] = getTexturePixels(k4);
		aBoolean1463 = !textureIsTransparant[k4];
		k2 = j2 - k2;
		j3 = i3 - j3;
		i4 = l3 - i4;
		l2 -= j2;
		k3 -= i3;
		j4 -= l3;
		int l4 = l2 * i3 - k3 * j2 << 14;
		int i5 = k3 * l3 - j4 * i3 << 8;
		int j5 = j4 * j2 - l2 * l3 << 5;
		int k5 = k2 * i3 - j3 * j2 << 14;
		int l5 = j3 * l3 - i4 * i3 << 8;
		int i6 = i4 * j2 - k2 * l3 << 5;
		int j6 = j3 * l2 - k2 * k3 << 14;
		int k6 = i4 * k3 - j3 * j4 << 8;
		int l6 = k2 * j4 - i4 * l2 << 5;
		int i7 = 0;
		int j7 = 0;
		if(j != i)
		{
			i7 = (i1 - l << 16) / (j - i);
			j7 = (l1 - k1 << 16) / (j - i);
		}
		int k7 = 0;
		int l7 = 0;
		if(k != j)
		{
			k7 = (j1 - i1 << 16) / (k - j);
			l7 = (i2 - l1 << 16) / (k - j);
		}
		int i8 = 0;
		int j8 = 0;
		if(k != i)
		{
			i8 = (l - j1 << 16) / (i - k);
			j8 = (k1 - i2 << 16) / (i - k);
		}
		if(i <= j && i <= k)
		{
			if(i >= DrawingArea.bottomY)
				return;
			if(j > DrawingArea.bottomY)
				j = DrawingArea.bottomY;
			if(k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if(j < k)
			{
				j1 = l <<= 16;
				i2 = k1 <<= 16;
				if(i < 0)
				{
					j1 -= i8 * i;
					l -= i7 * i;
					i2 -= j8 * i;
					k1 -= j7 * i;
					i = 0;
				}
				i1 <<= 16;
				l1 <<= 16;
				if(j < 0)
				{
					i1 -= k7 * j;
					l1 -= l7 * j;
					j = 0;
				}
				int k8 = i - textureInt2;
				l4 += j5 * k8;
				k5 += i6 * k8;
				j6 += l6 * k8;
				if(i != j && i8 < i7 || i == j && i8 > k7)
				{
					k -= j;
					j -= i;
					i = anIntArray1472[i];
					while(--j >= 0) 
					{
						drawTexturedScanline317(DrawingArea.pixels, ai, i, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
						j1 += i8;
						l += i7;
						i2 += j8;
						k1 += j7;
						i += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while(--k >= 0) 
					{
						drawTexturedScanline317(DrawingArea.pixels, ai, i, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
						j1 += i8;
						i1 += k7;
						i2 += j8;
						l1 += l7;
						i += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				k -= j;
				j -= i;
				i = anIntArray1472[i];
				while(--j >= 0) 
				{
					drawTexturedScanline317(DrawingArea.pixels, ai, i, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += i8;
					l += i7;
					i2 += j8;
					k1 += j7;
					i += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--k >= 0) 
				{
					drawTexturedScanline317(DrawingArea.pixels, ai, i, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += i8;
					i1 += k7;
					i2 += j8;
					l1 += l7;
					i += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			i1 = l <<= 16;
			l1 = k1 <<= 16;
			if(i < 0)
			{
				i1 -= i8 * i;
				l -= i7 * i;
				l1 -= j8 * i;
				k1 -= j7 * i;
				i = 0;
			}
			j1 <<= 16;
			i2 <<= 16;
			if(k < 0)
			{
				j1 -= k7 * k;
				i2 -= l7 * k;
				k = 0;
			}
			int l8 = i - textureInt2;
			l4 += j5 * l8;
			k5 += i6 * l8;
			j6 += l6 * l8;
			if(i != k && i8 < i7 || i == k && k7 > i7)
			{
				j -= k;
				k -= i;
				i = anIntArray1472[i];
				while(--k >= 0) 
				{
					drawTexturedScanline317(DrawingArea.pixels, ai, i, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					i1 += i8;
					l += i7;
					l1 += j8;
					k1 += j7;
					i += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--j >= 0) 
				{
					drawTexturedScanline317(DrawingArea.pixels, ai, i, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += k7;
					l += i7;
					i2 += l7;
					k1 += j7;
					i += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			j -= k;
			k -= i;
			i = anIntArray1472[i];
			while(--k >= 0) 
			{
				drawTexturedScanline317(DrawingArea.pixels, ai, i, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += i8;
				l += i7;
				l1 += j8;
				k1 += j7;
				i += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--j >= 0) 
			{
				drawTexturedScanline317(DrawingArea.pixels, ai, i, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				j1 += k7;
				l += i7;
				i2 += l7;
				k1 += j7;
				i += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if(j <= k)
		{
			if(j >= DrawingArea.bottomY)
				return;
			if(k > DrawingArea.bottomY)
				k = DrawingArea.bottomY;
			if(i > DrawingArea.bottomY)
				i = DrawingArea.bottomY;
			if(k < i)
			{
				l = i1 <<= 16;
				k1 = l1 <<= 16;
				if(j < 0)
				{
					l -= i7 * j;
					i1 -= k7 * j;
					k1 -= j7 * j;
					l1 -= l7 * j;
					j = 0;
				}
				j1 <<= 16;
				i2 <<= 16;
				if(k < 0)
				{
					j1 -= i8 * k;
					i2 -= j8 * k;
					k = 0;
				}
				int i9 = j - textureInt2;
				l4 += j5 * i9;
				k5 += i6 * i9;
				j6 += l6 * i9;
				if(j != k && i7 < k7 || j == k && i7 > i8)
				{
					i -= k;
					k -= j;
					j = anIntArray1472[j];
					while(--k >= 0) 
					{
						drawTexturedScanline317(DrawingArea.pixels, ai, j, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
						l += i7;
						i1 += k7;
						k1 += j7;
						l1 += l7;
						j += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					while(--i >= 0) 
					{
						drawTexturedScanline317(DrawingArea.pixels, ai, j, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
						l += i7;
						j1 += i8;
						k1 += j7;
						i2 += j8;
						j += DrawingArea.width;
						l4 += j5;
						k5 += i6;
						j6 += l6;
					}
					return;
				}
				i -= k;
				k -= j;
				j = anIntArray1472[j];
				while(--k >= 0) 
				{
					drawTexturedScanline317(DrawingArea.pixels, ai, j, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					l += i7;
					i1 += k7;
					k1 += j7;
					l1 += l7;
					j += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--i >= 0) 
				{
					drawTexturedScanline317(DrawingArea.pixels, ai, j, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					l += i7;
					j1 += i8;
					k1 += j7;
					i2 += j8;
					j += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			j1 = i1 <<= 16;
			i2 = l1 <<= 16;
			if(j < 0)
			{
				j1 -= i7 * j;
				i1 -= k7 * j;
				i2 -= j7 * j;
				l1 -= l7 * j;
				j = 0;
			}
			l <<= 16;
			k1 <<= 16;
			if(i < 0)
			{
				l -= i8 * i;
				k1 -= j8 * i;
				i = 0;
			}
			int j9 = j - textureInt2;
			l4 += j5 * j9;
			k5 += i6 * j9;
			j6 += l6 * j9;
			if(i7 < k7)
			{
				k -= i;
				i -= j;
				j = anIntArray1472[j];
				while(--i >= 0) 
				{
					drawTexturedScanline317(DrawingArea.pixels, ai, j, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
					j1 += i7;
					i1 += k7;
					i2 += j7;
					l1 += l7;
					j += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--k >= 0) 
				{
					drawTexturedScanline317(DrawingArea.pixels, ai, j, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
					l += i8;
					i1 += k7;
					k1 += j8;
					l1 += l7;
					j += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			k -= i;
			i -= j;
			j = anIntArray1472[j];
			while(--i >= 0) 
			{
				drawTexturedScanline317(DrawingArea.pixels, ai, j, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				j1 += i7;
				i1 += k7;
				i2 += j7;
				l1 += l7;
				j += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--k >= 0) 
			{
				drawTexturedScanline317(DrawingArea.pixels, ai, j, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
				l += i8;
				i1 += k7;
				k1 += j8;
				l1 += l7;
				j += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		if(k >= DrawingArea.bottomY)
			return;
		if(i > DrawingArea.bottomY)
			i = DrawingArea.bottomY;
		if(j > DrawingArea.bottomY)
			j = DrawingArea.bottomY;
		if(i < j)
		{
			i1 = j1 <<= 16;
			l1 = i2 <<= 16;
			if(k < 0)
			{
				i1 -= k7 * k;
				j1 -= i8 * k;
				l1 -= l7 * k;
				i2 -= j8 * k;
				k = 0;
			}
			l <<= 16;
			k1 <<= 16;
			if(i < 0)
			{
				l -= i7 * i;
				k1 -= j7 * i;
				i = 0;
			}
			int k9 = k - textureInt2;
			l4 += j5 * k9;
			k5 += i6 * k9;
			j6 += l6 * k9;
			if(k7 < i8)
			{
				j -= i;
				i -= k;
				k = anIntArray1472[k];
				while(--i >= 0) 
				{
					drawTexturedScanline317(DrawingArea.pixels, ai, k, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
					i1 += k7;
					j1 += i8;
					l1 += l7;
					i2 += j8;
					k += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				while(--j >= 0) 
				{
					drawTexturedScanline317(DrawingArea.pixels, ai, k, i1 >> 16, l >> 16, l1 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
					i1 += k7;
					l += i7;
					l1 += l7;
					k1 += j7;
					k += DrawingArea.width;
					l4 += j5;
					k5 += i6;
					j6 += l6;
				}
				return;
			}
			j -= i;
			i -= k;
			k = anIntArray1472[k];
			while(--i >= 0) 
			{
				drawTexturedScanline317(DrawingArea.pixels, ai, k, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += k7;
				j1 += i8;
				l1 += l7;
				i2 += j8;
				k += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--j >= 0) 
			{
				drawTexturedScanline317(DrawingArea.pixels, ai, k, l >> 16, i1 >> 16, k1 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += k7;
				l += i7;
				l1 += l7;
				k1 += j7;
				k += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		l = j1 <<= 16;
		k1 = i2 <<= 16;
		if(k < 0)
		{
			l -= k7 * k;
			j1 -= i8 * k;
			k1 -= l7 * k;
			i2 -= j8 * k;
			k = 0;
		}
		i1 <<= 16;
		l1 <<= 16;
		if(j < 0)
		{
			i1 -= i7 * j;
			l1 -= j7 * j;
			j = 0;
		}
		int l9 = k - textureInt2;
		l4 += j5 * l9;
		k5 += i6 * l9;
		j6 += l6 * l9;
		if(k7 < i8)
		{
			i -= j;
			j -= k;
			k = anIntArray1472[k];
			while(--j >= 0) 
			{
				drawTexturedScanline317(DrawingArea.pixels, ai, k, l >> 16, j1 >> 16, k1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				l += k7;
				j1 += i8;
				k1 += l7;
				i2 += j8;
				k += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			while(--i >= 0) 
			{
				drawTexturedScanline317(DrawingArea.pixels, ai, k, i1 >> 16, j1 >> 16, l1 >> 8, i2 >> 8, l4, k5, j6, i5, l5, k6);
				i1 += i7;
				j1 += i8;
				l1 += j7;
				i2 += j8;
				k += DrawingArea.width;
				l4 += j5;
				k5 += i6;
				j6 += l6;
			}
			return;
		}
		i -= j;
		j -= k;
		k = anIntArray1472[k];
		while(--j >= 0) 
		{
			drawTexturedScanline317(DrawingArea.pixels, ai, k, j1 >> 16, l >> 16, i2 >> 8, k1 >> 8, l4, k5, j6, i5, l5, k6);
			l += k7;
			j1 += i8;
			k1 += l7;
			i2 += j8;
			k += DrawingArea.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
		while(--i >= 0) 
		{
			drawTexturedScanline317(DrawingArea.pixels, ai, k, j1 >> 16, i1 >> 16, i2 >> 8, l1 >> 8, l4, k5, j6, i5, l5, k6);
			i1 += i7;
			j1 += i8;
			l1 += j7;
			i2 += j8;
			k += DrawingArea.width;
			l4 += j5;
			k5 += i6;
			j6 += l6;
		}
	}

	private static void drawTexturedScanline317(int ai[], int ai1[], int k, int l, int i1, int j1,
												int k1, int l1, int i2, int j2, int k2, int l2, int i3)
	{
		int i = 0;//was parameter
		int j = 0;//was parameter
		if(l >= i1)
			return;
		int j3;
		int k3;
		if(aBoolean1462)
		{
			j3 = (k1 - j1) / (i1 - l);
			if(i1 > DrawingArea.centerX)
				i1 = DrawingArea.centerX;
			if(l < 0)
			{
				j1 -= l * j3;
				l = 0;
			}
			if(l >= i1)
				return;
			k3 = i1 - l >> 3;
			j3 <<= 12;
			j1 <<= 9;
		} else
		{
			if(i1 - l > 7)
			{
				k3 = i1 - l >> 3;
				j3 = (k1 - j1) * anIntArray1468[k3] >> 6;
			} else
			{
				k3 = 0;
				j3 = 0;
			}
			j1 <<= 9;
		}
		k += l;
		if(lowMem)
		{
			int i4 = 0;
			int k4 = 0;
			int k6 = l - textureInt1;
			l1 += (k2 >> 3) * k6;
			i2 += (l2 >> 3) * k6;
			j2 += (i3 >> 3) * k6;
			int i5 = j2 >> 12;
			if(i5 != 0)
			{
				i = l1 / i5;
				j = i2 / i5;
				if(i < 0)
					i = 0;
				else
				if(i > 4032)
					i = 4032;
			}
			l1 += k2;
			i2 += l2;
			j2 += i3;
			i5 = j2 >> 12;
			if(i5 != 0)
			{
				i4 = l1 / i5;
				k4 = i2 / i5;
				if(i4 < 7)
					i4 = 7;
				else
				if(i4 > 4032)
					i4 = 4032;
			}
			int i7 = i4 - i >> 3;
			int k7 = k4 - j >> 3;
			i += (j1 & 0x600000) >> 3;
			int i8 = j1 >> 23;
			if(aBoolean1463)
			{
				while(k3-- > 0) 
				{
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i = i4;
					j = k4;
					l1 += k2;
					i2 += l2;
					j2 += i3;
					int j5 = j2 >> 12;
					if(j5 != 0)
					{
						i4 = l1 / j5;
						k4 = i2 / j5;
						if(i4 < 7)
							i4 = 7;
						else
						if(i4 > 4032)
							i4 = 4032;
					}
					i7 = i4 - i >> 3;
					k7 = k4 - j >> 3;
					j1 += j3;
					i += (j1 & 0x600000) >> 3;
					i8 = j1 >> 23;
				}
				for(k3 = i1 - l & 7; k3-- > 0;)
				{
					ai[k++] = ai1[(j & 0xfc0) + (i >> 6)] >>> i8;
					i += i7;
					j += k7;
				}

				return;
			}
			while(k3-- > 0) 
			{
				int k8;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i += i7;
				j += k7;
				if((k8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = k8;
				k++;
				i = i4;
				j = k4;
				l1 += k2;
				i2 += l2;
				j2 += i3;
				int k5 = j2 >> 12;
				if(k5 != 0)
				{
					i4 = l1 / k5;
					k4 = i2 / k5;
					if(i4 < 7)
						i4 = 7;
					else
					if(i4 > 4032)
						i4 = 4032;
				}
				i7 = i4 - i >> 3;
				k7 = k4 - j >> 3;
				j1 += j3;
				i += (j1 & 0x600000) >> 3;
				i8 = j1 >> 23;
			}
			for(k3 = i1 - l & 7; k3-- > 0;)
			{
				int l8;
				if((l8 = ai1[(j & 0xfc0) + (i >> 6)] >>> i8) != 0)
					ai[k] = l8;
				k++;
				i += i7;
				j += k7;
			}

			return;
		}
		int j4 = 0;
		int l4 = 0;
		int l6 = l - textureInt1;
		l1 += (k2 >> 3) * l6;
		i2 += (l2 >> 3) * l6;
		j2 += (i3 >> 3) * l6;
		int l5 = j2 >> 14;
		if(l5 != 0)
		{
			i = l1 / l5;
			j = i2 / l5;
			if(i < 0)
				i = 0;
			else
			if(i > 16256)
				i = 16256;
		}
		l1 += k2;
		i2 += l2;
		j2 += i3;
		l5 = j2 >> 14;
		if(l5 != 0)
		{
			j4 = l1 / l5;
			l4 = i2 / l5;
			if(j4 < 7)
				j4 = 7;
			else
			if(j4 > 16256)
				j4 = 16256;
		}
		int j7 = j4 - i >> 3;
		int l7 = l4 - j >> 3;
		i += j1 & 0x600000;
		int j8 = j1 >> 23;
		if(aBoolean1463)
		{
			while(k3-- > 0) 
			{
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i = j4;
				j = l4;
				l1 += k2;
				i2 += l2;
				j2 += i3;
				int i6 = j2 >> 14;
				if(i6 != 0)
				{
					j4 = l1 / i6;
					l4 = i2 / i6;
					if(j4 < 7)
						j4 = 7;
					else
					if(j4 > 16256)
						j4 = 16256;
				}
				j7 = j4 - i >> 3;
				l7 = l4 - j >> 3;
				j1 += j3;
				i += j1 & 0x600000;
				j8 = j1 >> 23;
			}
			for(k3 = i1 - l & 7; k3-- > 0;)
			{
				ai[k++] = ai1[(j & 0x3f80) + (i >> 7)] >>> j8;
				i += j7;
				j += l7;
			}

			return;
		}
		while(k3-- > 0) 
		{
			int i9;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i += j7;
			j += l7;
			if((i9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = i9;
			k++;
			i = j4;
			j = l4;
			l1 += k2;
			i2 += l2;
			j2 += i3;
			int j6 = j2 >> 14;
			if(j6 != 0)
			{
				j4 = l1 / j6;
				l4 = i2 / j6;
				if(j4 < 7)
					j4 = 7;
				else
				if(j4 > 16256)
					j4 = 16256;
			}
			j7 = j4 - i >> 3;
			l7 = l4 - j >> 3;
			j1 += j3;
			i += j1 & 0x600000;
			j8 = j1 >> 23;
		}
		for(int l3 = i1 - l & 7; l3-- > 0;)
		{
			int j9;
			if((j9 = ai1[(j & 0x3f80) + (i >> 7)] >>> j8) != 0)
				ai[k] = j9;
			k++;
			i += j7;
			j += l7;
		}

	}
	public static int textureAmount = 117;
	public static final int anInt1459 = -477;
	public static boolean lowMem = true;
	static boolean aBoolean1462;
	private static boolean aBoolean1463;
	public static boolean aBoolean1464 = true;
	public static int anInt1465;
	public static int textureInt1;
	public static int textureInt2;
	private static int[] anIntArray1468;
	public static final int[] anIntArray1469;
	public static int anIntArray1470[];
	public static int anIntArray1471[];
	public static int anIntArray1472[];
	private static int textureCount;
	public static Background textures[] = new Background[textureAmount];
	private static boolean[] textureIsTransparant = new boolean[textureAmount];
	private static int[] averageTextureColours = new int[textureAmount];
	private static int textureRequestBufferPointer;
	private static int[][] textureRequestPixelBuffer;
	private static int[][] texturesPixelBuffer = new int[textureAmount][];
	public static int textureLastUsed[] = new int[textureAmount];
	public static int lastTextureRetrievalCount;
	public static int hslToRgb[] = new int[0x10000];
	private static int[][] currentPalette = new int[textureAmount][];

	static 
	{
		anIntArray1468 = new int[512];
		anIntArray1469 = new int[2048];
		anIntArray1470 = new int[2048];
		anIntArray1471 = new int[2048];
		for(int i = 1; i < 512; i++)
			anIntArray1468[i] = 32768 / i;

		for(int j = 1; j < 2048; j++)
			anIntArray1469[j] = 0x10000 / j;

		for(int k = 0; k < 2048; k++)
		{
			anIntArray1470[k] = (int)(65536D * Math.sin((double)k * 0.0030679614999999999D));
			anIntArray1471[k] = (int)(65536D * Math.cos((double)k * 0.0030679614999999999D));
		}

	}
}
