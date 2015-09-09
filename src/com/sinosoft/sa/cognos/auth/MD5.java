/*     */ package com.sinosoft.sa.cognos.auth;
/*     */ 
/*     */ final class MD5
/*     */ {
/*     */   static final int S11 = 7;
/*     */   static final int S12 = 12;
/*     */   static final int S13 = 17;
/*     */   static final int S14 = 22;
/*     */   static final int S21 = 5;
/*     */   static final int S22 = 9;
/*     */   static final int S23 = 14;
/*     */   static final int S24 = 20;
/*     */   static final int S31 = 4;
/*     */   static final int S32 = 11;
/*     */   static final int S33 = 16;
/*     */   static final int S34 = 23;
/*     */   static final int S41 = 6;
/*     */   static final int S42 = 10;
/*     */   static final int S43 = 15;
/*     */   static final int S44 = 21;
/*  22 */   static final byte[] PADDING = { 
/*  23 */     -128 };
/*     */   private long[] state;
/*     */   private long[] count;
/*     */   private byte[] buffer;
/*     */   private String digestHexStr;
/*     */   private byte[] digest;
/*     */ 
/*     */   protected synchronized String getMD5ofStr(String inbuf)
/*     */   {
/*  39 */     md5Init();
/*  40 */     md5Update(inbuf.getBytes(), inbuf.length());
/*  41 */     md5Final();
/*  42 */     this.digestHexStr = "";
/*  43 */     for (int i = 0; i < 16; i++)
/*     */     {
/*  45 */       this.digestHexStr += byteHEX(this.digest[i]);
/*     */     }
/*     */ 
/*  48 */     return this.digestHexStr;
/*     */   }
/*     */ 
/*     */   protected MD5()
/*     */   {
/*  53 */     this.state = new long[4];
/*  54 */     this.count = new long[2];
/*  55 */     this.buffer = new byte[64];
/*  56 */     this.digest = new byte[16];
/*  57 */     md5Init();
/*     */   }
/*     */ 
/*     */   private void md5Init()
/*     */   {
/*  62 */     this.count[0] = 0L;
/*  63 */     this.count[1] = 0L;
/*  64 */     this.state[0] = 1732584193L;
/*  65 */     this.state[1] = 4023233417L;
/*  66 */     this.state[2] = 2562383102L;
/*  67 */     this.state[3] = 271733878L;
/*     */   }
/*     */ 
/*     */   private long F(long x, long y, long z)
/*     */   {
/*  72 */     return x & y | (x ^ 0xFFFFFFFF) & z;
/*     */   }
/*     */ 
/*     */   private long G(long x, long y, long z)
/*     */   {
/*  77 */     return x & z | y & (z ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   private long H(long x, long y, long z)
/*     */   {
/*  82 */     return x ^ y ^ z;
/*     */   }
/*     */ 
/*     */   private long I(long x, long y, long z)
/*     */   {
/*  87 */     return y ^ (x | z ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   private long FF(long a, long b, long c, long d, long x, long s, long ac)
/*     */   {
/*  92 */     a += F(b, c, d) + x + ac;
/*  93 */     a = (int)a << (int)s | (int)a >>> (int)(32L - s);
/*  94 */     a += b;
/*  95 */     return a;
/*     */   }
/*     */ 
/*     */   private long GG(long a, long b, long c, long d, long x, long s, long ac)
/*     */   {
/* 100 */     a += G(b, c, d) + x + ac;
/* 101 */     a = (int)a << (int)s | (int)a >>> (int)(32L - s);
/* 102 */     a += b;
/* 103 */     return a;
/*     */   }
/*     */ 
/*     */   private long HH(long a, long b, long c, long d, long x, long s, long ac)
/*     */   {
/* 108 */     a += H(b, c, d) + x + ac;
/* 109 */     a = (int)a << (int)s | (int)a >>> (int)(32L - s);
/* 110 */     a += b;
/* 111 */     return a;
/*     */   }
/*     */ 
/*     */   private long II(long a, long b, long c, long d, long x, long s, long ac)
/*     */   {
/* 116 */     a += I(b, c, d) + x + ac;
/* 117 */     a = (int)a << (int)s | (int)a >>> (int)(32L - s);
/* 118 */     a += b;
/* 119 */     return a;
/*     */   }
/*     */ 
/*     */   private void md5Update(byte[] inbuf, int inputLen)
/*     */   {
/* 124 */     byte[] block = new byte[64];
/* 125 */     int index = (int)(this.count[0] >>> 3) & 0x3F;
/* 126 */     if (this.count[0] += (inputLen << 3) < inputLen << 3)
/*     */     {
/* 128 */       this.count[1] += 1L;
/*     */     }
/* 130 */     this.count[1] += (inputLen >>> 29);
/* 131 */     int partLen = 64 - index;
/*     */     int i;
/* 133 */     if (inputLen >= partLen)
/*     */     {
/* 135 */       md5Memcpy(this.buffer, inbuf, index, 0, partLen);
/* 136 */       md5Transform(this.buffer);
/* 137 */       for (int i = partLen; i + 63 < inputLen; i += 64)
/*     */       {
/* 139 */         md5Memcpy(block, inbuf, 0, i, 64);
/* 140 */         md5Transform(block);
/*     */       }
/*     */ 
/* 143 */       index = 0;
/*     */     }
/*     */     else {
/* 146 */       i = 0;
/*     */     }
/* 148 */     md5Memcpy(this.buffer, inbuf, index, i, inputLen - i);
/*     */   }
/*     */ 
/*     */   private void md5Final()
/*     */   {
/* 153 */     byte[] bits = new byte[8];
/* 154 */     encode(bits, this.count, 8);
/* 155 */     int index = (int)(this.count[0] >>> 3) & 0x3F;
/* 156 */     int padLen = index >= 56 ? 120 - index : 56 - index;
/* 157 */     md5Update(PADDING, padLen);
/* 158 */     md5Update(bits, 8);
/* 159 */     encode(this.digest, this.state, 16);
/*     */   }
/*     */ 
/*     */   private void md5Memcpy(byte[] output, byte[] input, int outpos, int inpos, int len)
/*     */   {
/* 164 */     for (int i = 0; i < len; i++)
/*     */     {
/* 166 */       output[(outpos + i)] = input[(inpos + i)];
/*     */     }
/*     */   }
/*     */ 
/*     */   private void md5Transform(byte[] block)
/*     */   {
/* 173 */     long a = this.state[0];
/* 174 */     long b = this.state[1];
/* 175 */     long c = this.state[2];
/* 176 */     long d = this.state[3];
/* 177 */     long[] x = new long[16];
/* 178 */     decode(x, block, 64);
/* 179 */     a = FF(a, b, c, d, x[0], 7L, 3614090360L);
/* 180 */     d = FF(d, a, b, c, x[1], 12L, 3905402710L);
/* 181 */     c = FF(c, d, a, b, x[2], 17L, 606105819L);
/* 182 */     b = FF(b, c, d, a, x[3], 22L, 3250441966L);
/* 183 */     a = FF(a, b, c, d, x[4], 7L, 4118548399L);
/* 184 */     d = FF(d, a, b, c, x[5], 12L, 1200080426L);
/* 185 */     c = FF(c, d, a, b, x[6], 17L, 2821735955L);
/* 186 */     b = FF(b, c, d, a, x[7], 22L, 4249261313L);
/* 187 */     a = FF(a, b, c, d, x[8], 7L, 1770035416L);
/* 188 */     d = FF(d, a, b, c, x[9], 12L, 2336552879L);
/* 189 */     c = FF(c, d, a, b, x[10], 17L, 4294925233L);
/* 190 */     b = FF(b, c, d, a, x[11], 22L, 2304563134L);
/* 191 */     a = FF(a, b, c, d, x[12], 7L, 1804603682L);
/* 192 */     d = FF(d, a, b, c, x[13], 12L, 4254626195L);
/* 193 */     c = FF(c, d, a, b, x[14], 17L, 2792965006L);
/* 194 */     b = FF(b, c, d, a, x[15], 22L, 1236535329L);
/* 195 */     a = GG(a, b, c, d, x[1], 5L, 4129170786L);
/* 196 */     d = GG(d, a, b, c, x[6], 9L, 3225465664L);
/* 197 */     c = GG(c, d, a, b, x[11], 14L, 643717713L);
/* 198 */     b = GG(b, c, d, a, x[0], 20L, 3921069994L);
/* 199 */     a = GG(a, b, c, d, x[5], 5L, 3593408605L);
/* 200 */     d = GG(d, a, b, c, x[10], 9L, 38016083L);
/* 201 */     c = GG(c, d, a, b, x[15], 14L, 3634488961L);
/* 202 */     b = GG(b, c, d, a, x[4], 20L, 3889429448L);
/* 203 */     a = GG(a, b, c, d, x[9], 5L, 568446438L);
/* 204 */     d = GG(d, a, b, c, x[14], 9L, 3275163606L);
/* 205 */     c = GG(c, d, a, b, x[3], 14L, 4107603335L);
/* 206 */     b = GG(b, c, d, a, x[8], 20L, 1163531501L);
/* 207 */     a = GG(a, b, c, d, x[13], 5L, 2850285829L);
/* 208 */     d = GG(d, a, b, c, x[2], 9L, 4243563512L);
/* 209 */     c = GG(c, d, a, b, x[7], 14L, 1735328473L);
/* 210 */     b = GG(b, c, d, a, x[12], 20L, 2368359562L);
/* 211 */     a = HH(a, b, c, d, x[5], 4L, 4294588738L);
/* 212 */     d = HH(d, a, b, c, x[8], 11L, 2272392833L);
/* 213 */     c = HH(c, d, a, b, x[11], 16L, 1839030562L);
/* 214 */     b = HH(b, c, d, a, x[14], 23L, 4259657740L);
/* 215 */     a = HH(a, b, c, d, x[1], 4L, 2763975236L);
/* 216 */     d = HH(d, a, b, c, x[4], 11L, 1272893353L);
/* 217 */     c = HH(c, d, a, b, x[7], 16L, 4139469664L);
/* 218 */     b = HH(b, c, d, a, x[10], 23L, 3200236656L);
/* 219 */     a = HH(a, b, c, d, x[13], 4L, 681279174L);
/* 220 */     d = HH(d, a, b, c, x[0], 11L, 3936430074L);
/* 221 */     c = HH(c, d, a, b, x[3], 16L, 3572445317L);
/* 222 */     b = HH(b, c, d, a, x[6], 23L, 76029189L);
/* 223 */     a = HH(a, b, c, d, x[9], 4L, 3654602809L);
/* 224 */     d = HH(d, a, b, c, x[12], 11L, 3873151461L);
/* 225 */     c = HH(c, d, a, b, x[15], 16L, 530742520L);
/* 226 */     b = HH(b, c, d, a, x[2], 23L, 3299628645L);
/* 227 */     a = II(a, b, c, d, x[0], 6L, 4096336452L);
/* 228 */     d = II(d, a, b, c, x[7], 10L, 1126891415L);
/* 229 */     c = II(c, d, a, b, x[14], 15L, 2878612391L);
/* 230 */     b = II(b, c, d, a, x[5], 21L, 4237533241L);
/* 231 */     a = II(a, b, c, d, x[12], 6L, 1700485571L);
/* 232 */     d = II(d, a, b, c, x[3], 10L, 2399980690L);
/* 233 */     c = II(c, d, a, b, x[10], 15L, 4293915773L);
/* 234 */     b = II(b, c, d, a, x[1], 21L, 2240044497L);
/* 235 */     a = II(a, b, c, d, x[8], 6L, 1873313359L);
/* 236 */     d = II(d, a, b, c, x[15], 10L, 4264355552L);
/* 237 */     c = II(c, d, a, b, x[6], 15L, 2734768916L);
/* 238 */     b = II(b, c, d, a, x[13], 21L, 1309151649L);
/* 239 */     a = II(a, b, c, d, x[4], 6L, 4149444226L);
/* 240 */     d = II(d, a, b, c, x[11], 10L, 3174756917L);
/* 241 */     c = II(c, d, a, b, x[2], 15L, 718787259L);
/* 242 */     b = II(b, c, d, a, x[9], 21L, 3951481745L);
/* 243 */     this.state[0] += a;
/* 244 */     this.state[1] += b;
/* 245 */     this.state[2] += c;
/* 246 */     this.state[3] += d;
/*     */   }
/*     */ 
/*     */   private void encode(byte[] output, long[] input, int len)
/*     */   {
/* 251 */     int i = 0;
/* 252 */     for (int j = 0; j < len; j += 4)
/*     */     {
/* 254 */       output[j] = (byte)(int)(input[i] & 0xFF);
/* 255 */       output[(j + 1)] = (byte)(int)(input[i] >>> 8 & 0xFF);
/* 256 */       output[(j + 2)] = (byte)(int)(input[i] >>> 16 & 0xFF);
/* 257 */       output[(j + 3)] = (byte)(int)(input[i] >>> 24 & 0xFF);
/* 258 */       i++;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void decode(long[] output, byte[] input, int len)
/*     */   {
/* 265 */     int i = 0;
/* 266 */     for (int j = 0; j < len; j += 4)
/*     */     {
/* 268 */       output[i] = (b2iu(input[j]) | b2iu(input[(j + 1)]) << 8 | b2iu(input[(j + 2)]) << 16 | b2iu(input[(j + 3)]) << 24);
/* 269 */       i++;
/*     */     }
/*     */   }
/*     */ 
/*     */   private long b2iu(byte b)
/*     */   {
/* 276 */     return b >= 0 ? b : b & 0xFF;
/*     */   }
/*     */ 
/*     */   private String byteHEX(byte ib)
/*     */   {
/* 281 */     char[] Digit = { 
/* 282 */       '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
/* 283 */       'a', 'b', 'c', 'd', 'e', 'f' };
/*     */ 
/* 285 */     char[] ob = new char[2];
/* 286 */     ob[0] = Digit[(ib >>> 4 & 0xF)];
/* 287 */     ob[1] = Digit[(ib & 0xF)];
/* 288 */     String s = new String(ob);
/* 289 */     return s;
/*     */   }
/*     */ }

/* Location:           C:\Users\wuxinbo\Desktop\JDBCAuth\
 * Qualified Name:     com.sinosoft.sa.cognos.auth.MD5
 * JD-Core Version:    0.6.0
 */