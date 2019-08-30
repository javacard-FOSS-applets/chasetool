package com.gemplus.pacap.utils;


public abstract class Jour /*extends Object*/{

    ////////////////      ATTRIBUTS       ////////////////
    
    public static final byte MIN	= (byte)1;
    public static final byte MAX	= (byte)31;
    
    ///////////////     CONSTRUCTEUR     ////////////////
    
    
    
    ////////////////       METHODES      ///////////////
    /*@ 
      modifies \nothing ;
    */
    public static boolean check(byte j){
	if((j >= Jour.MIN) && (j <= Jour.MAX)) {
	    return true;
	} else {
	    return false;
	}
    }
}
