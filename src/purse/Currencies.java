package com.gemplus.pacap.purse;

public class Currencies extends Object{
    private byte MAX_DATA = (byte)20;
        
    public static final byte REFERENCE				= (byte)0x00;
    public static final byte EURO				= (byte)0x01;
    public static final byte FRANC				= (byte)0x02;
    public static final byte LIVRE_STERLING			= (byte)0x03;
    public static final byte DEUTSCH_MARK			= (byte)0x04;
    public static final byte FRANC_SUISSE			= (byte)0x05;
    public static final byte LIRE_ITALIENNE			= (byte)0x06;
    public static final byte FRANC_BELGE			= (byte)0x07;
    public static final byte DOLLAR_US				= (byte)0x08;

    private byte nbData = (byte)0;
    private byte[] data = new byte[MAX_DATA];
 
    Currencies() {
	addCurrency(REFERENCE);
	addCurrency(EURO);
	addCurrency(FRANC);
	addCurrency(LIVRE_STERLING);
	addCurrency(DEUTSCH_MARK);
	addCurrency(FRANC_SUISSE);
	addCurrency(LIRE_ITALIENNE);
	addCurrency(FRANC_BELGE);
	addCurrency(DOLLAR_US);      
    }
    
    /*@ 
      modifies data[*], nbData ;
    */
    void delCurrency(byte index) {
	byte i = index;
	if (i >= 0 && i < nbData) {
	    byte b = (byte)(nbData - (byte) 1);
	    while(i < b) {
		data[i] = data[(byte)(i+1)];
		i++;
	    }
	    nbData--;
	}
    }
    
    
    /*@ 
      modifies nbData, data[nbData];
    */
    void addCurrency(byte cur) {
	if(nbData < MAX_DATA) {
	    data[nbData] = cur;
	    nbData++;
	}                
    }    

    /*@
      modifies \nothing;
    */
    boolean contens(byte cur) {
	boolean resu = false;
	byte i = (byte)0;
	boolean trouve = false;
	while(i < MAX_DATA && ! resu) {
	    if(data[i] == cur) {
		resu = true;
	    } else
		i++;
	}
	return resu;
    }
    
    /*@ 
      modifies \nothing;
    */
    byte getNbData() {
	return nbData;
    }
    
    /*@ 
      modifies \nothing;
    */
    byte getData(byte i) {
	return data[i];
    }

    /*@ 
      modifies this.data[index];
    */
    void setData(byte index, byte data) {
	this.data[index] = data;
    }

}
