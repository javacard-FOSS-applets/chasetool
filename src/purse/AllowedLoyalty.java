package com.gemplus.pacap.purse;

import javacard.framework.AID;
import javacard.framework.Util;
import javacard.framework.JCSystem;
import javacard.framework.TransactionException;
import javacard.framework.JCSystem;

class AllowedLoyalty {    
    static final byte MAX_SALERS = (byte)5;
    
    private byte[] aid = new byte[16];
    private boolean logfullInformation = false;
    private com.gemplus.pacap.purse.SalerID[] data = new com.gemplus.pacap.purse.SalerID[MAX_SALERS];
    private byte nbSalers = (byte)0;

       
    AllowedLoyalty() {
	super();
	for(byte i = 0;i < MAX_SALERS;i++) {
	    data [i] = new SalerID();
	}
    }
        
    AllowedLoyalty(boolean toBeInformed) {
	this();
	logfullInformation = toBeInformed;
    }

    /*@
      modifies aid[*], data[*];
      modifies logfullInformation, nbSalers;
      //modifies data[*].data[*];
    */
    void reset() {
	for(byte i = 0;i < 16;i++) {
	    aid[i] = (byte) 0;
	}
	logfullInformation = false;
	for(byte i = 0;i < MAX_SALERS;i++) {
	    data[i].reset();
	}
	nbSalers = 0;
    }
    
    /*@
      modifies aid[*], data[*], logfullInformation, nbSalers;
      modifies logfullInformation, nbSalers;
      //modifies data[*].data[*];
      modifies TransactionException.systemInstance._reason;//!!
    */
    void clone(com.gemplus.pacap.purse.AllowedLoyalty al) 
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException {
	this.reset();
	javacard.framework.Util.arrayCopy(al.getAID(), (short)0, aid, (short)0, (short)16);
	this.logfullInformation = al.isToBeInformed();
	for(byte i = 0;i < MAX_SALERS;i++) {
	    data[i].clone(al.getSaler(i));
	}
	this.nbSalers = al.getNbSalers();
    }
    
    /*@
      modifies \nothing;
    */
    boolean isThis(AID aid) {
	//commented out by Néstor CATAÑO
	//return aid.equals(this.aid);
    }    
    
    /*@
      modifies \nothing ;
    */    
    boolean isToBeInformed(){
	return logfullInformation;
    }
    
    /*@
      modifies logfullInformation;
    */    
    void keepInformed() {
	toBeInformed(true);
    }
    
    /*@
      modifies logfullInformation ;
    */    
    void dontKeepInformed() {
	toBeInformed(false);
    }
    
    /*@
      modifies logfullInformation ;
    */    
    void toBeInformed(boolean b) {
	logfullInformation = b;
    }

    /*@
      modifies aid[*], TransactionException.systemInstance._reason;//!!
    */
    void setAID(byte[] aidBytes)
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException {
	javacard.framework.Util.arrayCopy(aidBytes, (short)0, aid, (short)0, (short)16);
    }

    /*@
      modifies \nothing;
    */    
    byte[] getAID() {
	return aid;
    }

    /*@
      modifies \nothing;
    */    
    byte getNbSalers() {
	return nbSalers;
    }

    /*@
      //modifies data[nbSalers].data[*];
      modifies TransactionException.systemInstance._reason;
    */  
    void addSaler(com.gemplus.pacap.purse.SalerID s)
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException,
	       AllowedLoyaltyException {
 	if(nbSalers < MAX_SALERS) {
 	    data[nbSalers++].clone(s);
 	} else {
 	    byte t = AllowedLoyaltyException.NB_SALERS_OVERFLOW;
 	    AllowedLoyaltyException.throwIt(t);
 	}
    }
    
    /*@
      //modifies data[nbSalers].data[*];
      modifies TransactionException.systemInstance._reason;//!!
    */
    void addSaler(byte[] s, short off) 
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException,
	       AllowedLoyaltyException {
	if(nbSalers < MAX_SALERS){
	    data[nbSalers++].setBytes(s, off);
	} else {
	    byte t = AllowedLoyaltyException.NB_SALERS_OVERFLOW;
	    AllowedLoyaltyException.throwIt(t);
	}
    }
   
    /*@      
      modifies data[*].data[*];
      modifies TransactionException.systemInstance._reason;//!!
     */
    void delSaler(byte[] id)
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException,  
	       TransactionException {
	//look for the index
	byte i = 0;
	while(i < nbSalers) {
	    byte comp = Util.arrayCompare(
					  data[i].getBytes(), (short)0, id, (short)0, SalerID.ID_LENGTH
					  );
	    if(comp == 0) {
		// suppress the offset
		for(byte j = (byte)(i+1);j < nbSalers;j++) {
		    data[(short)(j-1)].clone(data[j]);
		}
		//   update the entries number
		nbSalers--;
	    }
	    else i++;
	}
    }
    
    /*@
      modifies \nothing ;
    */
    com.gemplus.pacap.purse.SalerID getSaler(byte index) {
	return data[index];
    }
    
    /*@
      modifies \nothing ;
    */
    SalerID[] getSaler() {
	return data;
    }

}

