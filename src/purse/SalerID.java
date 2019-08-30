package com.gemplus.pacap.purse;
import javacard.framework.Util;
import javacard.framework.TransactionException;
import javacard.framework.JCSystem;
 
public class SalerID /*extends Object*/  implements com.gemplus.pacap.purse.PartnerID{    
    public static final byte ID_LENGTH = (byte)4;
    private byte[] data = new byte[SalerID.ID_LENGTH];
    
    public SalerID() { 
    }
       
    /*@
      modifies data[*];
    */
    void reset() {
	//Modified by Néstor CATAÑO
	//javacard.framework.Util.arrayFillNonAtomic(data, (short)0, ID_LENGTH, (byte)0);
	javacard.framework.Util.arrayFillNonAtomic(data, (short)0, (short)ID_LENGTH, (byte)0);
    }
    
    //@modifies \nothing;
    public byte[] getBytes() {
	return data;
    }
    
    /*@ 
      modifies dest[*] ; 
    */
    public short getBytes(byte[] dest, short off) {
	javacard.framework.Util.arrayCopyNonAtomic(data, (short)0, dest, off, (short)ID_LENGTH);
	return (short)(off + ID_LENGTH);
    }
    
    /*@
       modifies data[*];//!!
       modifies TransactionException.systemInstance._reason;//!!
    */
    public void setBytes(byte[] id) 
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException {
	setBytes(id, (short)0);
    }

    /*@
      modifies data[*];
      modifies TransactionException.systemInstance._reason;//!!
    */
    public void setBytes(byte[] id, short off) 
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException{
	javacard.framework.Util.arrayCopy(id, off, data, (short)0, (short)ID_LENGTH);
    }

    /*@
      modifies data[*];
      modifies TransactionException.systemInstance._reason;//!!
    */
    public void clone(com.gemplus.pacap.purse.SalerID s) 
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException {
	setBytes(s.getBytes());        
    }
}
