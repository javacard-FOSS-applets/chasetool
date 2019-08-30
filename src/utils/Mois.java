package com.gemplus.pacap.utils;

public abstract class Mois /*extends Object*/{
    public static final byte JANVIER	= (byte)1;
    public static final byte FEVRIER	= (byte)2;
    public static final byte MARS	= (byte)3;
    public static final byte AVRIL	= (byte)4;
    public static final byte MAI	= (byte)5;
    public static final byte JUIN	= (byte)6;
    public static final byte JUILLET	= (byte)7;
    public static final byte AOUT	= (byte)8;
    public static final byte SEPTEMBRE	= (byte)9;
    public static final byte OCTOBRE	= (byte)10;
    public static final byte NOVEMBRE	= (byte)11;
    public static final byte DECEMBRE	= (byte)12;
    
    public static final byte MIN	= Mois.JANVIER;
    public static final byte MAX	= Mois.DECEMBRE;

    /*@ 
      modifies \nothing ;
    */
    public static boolean check(byte j) {
	return ((j >= Mois.MIN) && (j <= Mois.MAX));
    }    
}
