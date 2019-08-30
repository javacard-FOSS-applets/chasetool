package com.gemplus.pacap.purse;

public class AccessControl extends com.gemplus.pacap.purse.AccessCondition {
    private byte methode = 0x00;
       
    AccessControl() {
	super();
    }
        
    AccessControl(byte m) {
	methode = m;
    }
    
    /*@
      modifies \nothing;
    */
    byte getMethode() {
	return methode;
    }

    /*@
      modifies methode;
    */
    void setMethode(byte m) {
	methode = m;
    }

}

