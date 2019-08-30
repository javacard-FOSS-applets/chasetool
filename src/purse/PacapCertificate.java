package com.gemplus.pacap.purse;

import javacard.framework.Util;
import javacard.security.CryptoException;


public class PacapCertificate extends Object {
    
    ////////////////      ATTRIBUTES       ////////////////
    
 
    
    ///////////////     CONSTRUCTOR     ////////////////
    
    
    
    ////////////////       METHODS      ///////////////
    
    
    
    public static short sign(
			     byte[] bArray, short sOffset, short sLength,
			     PacapKey key, byte[] dest, short dOffset,
			     byte ivNull[]
			     ) 
	throws CryptoException {
	return PacapSignature.sign(	bArray, sOffset, sLength,
					key, dest, dOffset,
					ivNull, (short)0, (short)ivNull.length);
    }
    
    public static boolean verify(byte[] inBuff, short inOffset, short inLength,
				 PacapKey key,
				 byte[] sigBuff, short  sigOffset,
				 byte ivNull[]
				 ) 
	throws CryptoException {
	return PacapSignature.verify(	inBuff, inOffset, inLength,
					key, sigBuff, sigOffset,
					ivNull, (short)0, (short)ivNull.length);
    }
    

}

