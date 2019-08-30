package javacard.framework;
import javacard.framework.JCSystem;


public final class AID{ 

/*@ spec_public non_null */ byte[] theAID;

 public AID( byte[] bArray, short offset, byte length ) 

 throws SystemException,
        NullPointerException,      
        ArrayIndexOutOfBoundsException, 
        TransactionException {
     if (length < 5 || length>16) 
	 SystemException.throwIt(SystemException.ILLEGAL_VALUE);
     theAID = new byte[length];
     javacard.framework.Util.arrayCopy( bArray, offset, theAID, (short)0, length );
 }

    
    /*@ 
      modifies dest[*],TransactionException.systemInstance._reason;
    */
    public byte getBytes (byte[] dest, short offset) 
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException,
	       TransactionException

    {
	javacard.framework.Util.arrayCopy( theAID, (short)0, dest, offset, (short)theAID.length );
	//code modified by Néstor CATAÑO
	//return (byte) theAID.length;
	return theAID.length;
    }


    /*@ 
      modifies \nothing;
    */
    public boolean equals( Object anObject )
	throws NullPointerException,
	       ArrayIndexOutOfBoundsException,
	       ClassCastException { 
	if ( ! (anObject instanceof AID) || ((AID)anObject).theAID.length != theAID.length)
	    return false; 
	return (javacard.framework.Util.arrayCompare(anObject.theAID, (short)0, 
						     theAID, (short)0, (short)theAID.length) ==0);
    }

    /*@ 
      modifies \nothing;
    */
    public boolean equals( byte[] bArray, short offset, byte length )
    
	throws NullPointerException,
	       ArrayIndexOutOfBoundsException { 
	if (bArray==null) 
	    return false;
	byte testByte = bArray[(short)(offset + length - (short)1)];
	return ((length == theAID.length) &&
		//code modified by Néstor CATAÑO
		//javacard.framework.Util.arrayCompare(bArray, offset, theAID, (short)0, length) == 0;
		(javacard.framework.Util.arrayCompare(bArray, offset, theAID, (short)0, (short)length) == 0));
    } 

    
    /*@ 
      modifies \nothing;
    */
    public boolean partialEquals( byte[] bArray, short offset, byte length )
    
	throws NullPointerException,
	       ArrayIndexOutOfBoundsException { 
	if (bArray==null || length > theAID.length) return false;
	//code modified by Néstor CATAÑO
	//javacard.framework.Util.arrayCompare(bArray, offset, theAID, (short)0, length) ==
	return (javacard.framework.Util.arrayCompare(bArray, offset, theAID, (short)0, (short)length) ==0);	
    }

    /*@
      modifies \nothing;
    */
    public boolean RIDEquals (javacard.framework.AID otherAID )
	throws NullPointerException,
	       ArrayIndexOutOfBoundsException { 
	if (otherAID==null) 
	    return false;
	if (javacard.framework.Util.arrayCompare(theAID, (short)0, otherAID.theAID, (short)0, (short)5) ==0 )
	    return true;
	return false;
    }

}//end of class
