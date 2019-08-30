package javacard.framework;
import javacard.framework.JCSystem;

public class Util {
    
    Util() {}
    
    /*@ 
      modifies dest[*], TransactionException.systemInstance._reason;
    */
    public static final native short arrayCopy(byte[] src, 
					       short srcOff, 
					       byte[] dest, 
					       short destOff, 
					       short length)
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException;
    
    /*@ 
      modifies dest[*];
    */	
    public static final native short arrayCopyNonAtomic(byte[] src, 
							short srcOff, 
							byte[] dest, 
							short destOff, 
							short length)
	    throws ArrayIndexOutOfBoundsException, 
		   NullPointerException ;
    
    /*@ 
      modifies bArray[*];
    */
    public static final native short arrayFillNonAtomic(byte[] bArray, 
							short bOff, 
							short bLen, 
							byte bValue)
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException;
    
    
    /*@ 
      modifies \nothing;
    */
    public static final native byte arrayCompare(byte[] src, 
						 short srcOff, 
						 byte[] dest, 
						 short destOff, 
						 short length)
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException;
    
        
    /*@ 
      modifies \nothing;
     */
    public static final short makeShort( byte b1, byte b2 ){ 
	return (short)(((short)b1 << 8) + ((short)b2 & 0xFF));
    }
    
    /*@ 
      modifies \nothing;
     */
    public static final short getShort( byte[] bArray, short bOff)
	throws ArrayIndexOutOfBoundsException 
    { 
	return (short)(( (short)(bArray[bOff]) << 8 ) +
		       ( (short)(bArray[(short)(bOff+1)]) & 0xFF));
    }

    /*@ 
      modifies bArray[bOff], bArray[bOff+1], TransactionException.systemInstance._reason;
    */
    public static final native short setShort( byte[] bArray, 
					       short bOff, 
					       short sValue)
	throws TransactionException,
	       ArrayIndexOutOfBoundsException;
}
