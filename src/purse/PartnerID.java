package com.gemplus.pacap.purse;

import javacard.framework.TransactionException;
import javacard.framework.JCSystem;
 
interface PartnerID {
    
    /*@ 
      modifies \nothing;
    */
    byte[] getBytes();
    
   
    /*@    
      modifies _data[*];
    */
    public void setBytes(byte[] id) 
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException;
}

