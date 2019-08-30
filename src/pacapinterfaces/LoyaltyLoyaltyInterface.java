package com.gemplus.pacap.pacapinterfaces;

import javacard.framework.Shareable;

public interface LoyaltyLoyaltyInterface extends Shareable {    
    public static final byte OK			= (byte)0x01;
    public static final byte UNKNOWN		= (byte)0x02;
    public static final byte NOT_A_FRIEND	= (byte)0x03;
    public static final byte NOT_ENOUGH_POINTS	= (byte)0x04;    
       
    /*@
      modifies \nothing;
    */
    public byte debit(short pts, boolean conv);
    
    /*@
      modifies \nothing;
    */
    public short getBalance();

}

