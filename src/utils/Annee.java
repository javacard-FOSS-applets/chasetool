package com.gemplus.pacap.utils;

public abstract class Annee /*extends Object*/{        
    public static final byte MIN = (byte)99;
    public static final byte MAX = (byte)127;    
    
    /*@
      modifies \nothing ;
    */
    public static boolean check(byte j) {
	return ((j >= Annee.MIN) && (j <= Annee.MAX));
    }
}
