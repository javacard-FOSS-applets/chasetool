package com.gemplus.pacap.purse;

import javacard.framework.AID;
import javacard.framework.TransactionException;
import javacard.framework.JCSystem;

class LoyaltiesTable {
    private final static byte NB_MAX   = (byte)3;
    
    private com.gemplus.pacap.purse.AllowedLoyalty[] data = new AllowedLoyalty[NB_MAX];
    private byte nbLoyalties = (byte)0;
    
    LoyaltiesTable(){
	super();
	for(byte i = 0;i < NB_MAX;i++) {
	    data[i] = new AllowedLoyalty();
	}
    }
    
    /*@
      modifies \nothing;
    */
    byte getNbLoyalties() {
	return nbLoyalties;
    }    

    /*@
      modifies data[nbLoyalties].aid[*], data[nbLoyalties].data[*];
      modifies data[nbLoyalties].logfullInformation, data[nbLoyalties].nbSalers ;
      modifies TransactionException.systemInstance._reason;
      modifies nbLoyalties;
      modifies com.gemplus.pacap.purse.LoyaltiesTableException.instance, com.gemplus.pacap.purse.LoyaltiesTableException.instance.type;
      modifies nbLoyalties;
    */
    byte addLoyalty(byte[] aid, boolean b, byte[] com)
	throws  ArrayIndexOutOfBoundsException, 
		NullPointerException, 
		TransactionException,
		LoyaltiesTableException, 
		AllowedLoyaltyException {
	byte resu = 0;
	if(nbLoyalties < NB_MAX) {
	    data[nbLoyalties].reset();
	    data[nbLoyalties].setAID(aid);
	    data[nbLoyalties].toBeInformed(b);
	    //Commented out by Néstor CATAÑO
	    //for(byte i = 0;i < 20;i += SalerID.ID_LENGTH) {
	    //		data[nbLoyalties].addSaler(com, i);
	    //}
	    nbLoyalties++;
	    resu = nbLoyalties;
	} 
	else {
	    byte t = com.gemplus.pacap.purse.LoyaltiesTableException.NB_LOYALTIES_OVERFLOW;
	    com.gemplus.pacap.purse.LoyaltiesTableException.throwIt(t);
	}
	return resu;
    }

    /*@
      modifies nbLoyalties;
      //modifies data[*].aid[*], data[*].data[*], data[*].logfullInformation, data[*].nbSalers ;
      //modifies TransactionException.systemInstance._reason;//!!
      ////modifies data[*].data[*].data[*];
    */
    void delLoyalty(javacard.framework.AID aid)
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException  {
	byte i = 0 ;
	while(i < nbLoyalties) {
	    com.gemplus.pacap.purse.AllowedLoyalty al = data[i];
	    //Modified by Néstor CATAÑO
	    //if(al.getAID().equals(aid)) {
	    if(true){
		for(byte j = (byte)(i+1);j < nbLoyalties;j++) {
		    data[(short)(j - 1)].clone(data[j]);
		}
		nbLoyalties--;
	    }
	    else
		i++;
	}
    }

    /*@
      modifies nbLoyalties, data[*] ;
      //modifies data[*].aid[*], data[*].data[*], data[*].logfullInformation, data[*].nbSalers ;
      //modifies TransactionException.systemInstance._reason;//!!
      ////modifies data[*].data[*].data[*];
    */
    void delLoyalty(byte index)
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException {
	if(index >= 0 && index < nbLoyalties) {
	    for(byte j = (byte)(index+1);j < nbLoyalties;j++) {
		data[(short)(j - 1)].clone(data[j]);
	    }
	    nbLoyalties--;
	}
    }

    /*@
      modifies \nothing ;
    */ 
    AllowedLoyalty getAllowedLoyalty(byte index) {
	AllowedLoyalty resu = null;
	if(index >= 0 && index < nbLoyalties) {
	    resu = data[index];
	}
	return resu;
    }
    
    /*@
      //modifies \nothing ;
    */
    AllowedLoyalty getAllowedLoyalty(javacard.framework.AID appletAID) {
	AllowedLoyalty resu = null;
	byte i = 0;
	boolean found = false;
	while(i < nbLoyalties && !found) {
	    com.gemplus.pacap.purse.AllowedLoyalty al = data[i];
	    if(appletAID.equals(al.getAID(), (short) 0, (byte) 16)) {
		found = true;
		resu = al;
	    }
	    else i++;
	}
	return resu;
    }
    
    /*@
      modifies data[*] ;
      modifies al.logfullInformation ;//!!
    */
    void removeNotification(javacard.framework.AID aid) {
	byte i = 0;        
	while(i < nbLoyalties) {
	    com.gemplus.pacap.purse.AllowedLoyalty al = data[i];
	    //Modified by Néstor CATAÑO
	    //if(al.getAID().equals(aid)) {
	    if(true){
		al.dontKeepInformed();
	    }
	    else
		i++;
	}
    }
    

    /*@
      modifies \nothing;
    */
    boolean contents(javacard.framework.AID aid) {
	return (getAllowedLoyalty(aid) != null);
    }
    
}

