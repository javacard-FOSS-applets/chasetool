package com.gemplus.pacap.purse;

import javacard.framework.Util;
import javacard.security.CryptoException;
import javacard.security.Signature;


public class PacapSignature {    
    private static com.gemplus.pacap.purse.Signature instance = null;
        
    /*@
      modifies instance ;
    */
    private static void createInstance()
	throws CryptoException {
	instance = com.gemplus.pacap.purse.Signature.getInstance(Signature.ALG_DES_MAC8_NOPAD, false);
    }
    
    
    public static short sign(	byte bArray[], short sOffset, short sLength,
				PacapKey key, byte dest[], short dOffset,
				byte iv[], short ivOffset, short ivLen)
	throws CryptoException {
	if(instance == null)
	    createInstance();
	instance.init(key.instance(), Signature.MODE_SIGN, iv, ivOffset, ivLen);
	// on padde !!
	short rest = (short)(sLength % (short)8);
	if (rest != 0) {
	    short newLength = (short)(sLength / (short)8);
	    newLength++;
	    newLength *= (short)8;
	    if((short)(sOffset + newLength) > bArray.length)
		CryptoException.throwIt(CryptoException.ILLEGAL_VALUE);
	    Util.arrayFillNonAtomic(
				    bArray, (short)(sOffset + sLength), (short)(newLength - sLength),
				    (byte)0
				    );
	    sLength = newLength;
	}
	return instance.sign(bArray, sOffset, sLength, dest, dOffset);
    }
    
    public static boolean verify(	byte inBuff[], short inOffset, short inLength,
					PacapKey key,
					byte sigBuff[], short  sigOffset,
					byte iv[], short ivOffset, short ivLen)
	throws CryptoException {
	if ( instance == null )
	    createInstance();
	instance.init(key.instance(), Signature.MODE_VERIFY, iv, ivOffset, ivLen);
	// on padde !!
	short rest = (short)(inLength % (short)8);
	if (rest != 0) {
	    short newLength = (short)(inLength / (short)8);
	    newLength++;
	    newLength *= (short)8;
	    if((short)(inOffset + newLength) > inBuff.length)
		CryptoException.throwIt(CryptoException.ILLEGAL_VALUE);
	    Util.arrayFillNonAtomic(
				    inBuff, (short)(inOffset + inLength), (short)(newLength - inLength),
				    (byte)0
				    );
	    inLength = newLength;
	}
	return instance.verify(inBuff, inOffset, inLength,sigBuff, sigOffset, (short)8);
    }
}

