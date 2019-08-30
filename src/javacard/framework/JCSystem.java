package javacard.framework;

//import com.sun.javacard.impl.PrivAccess;
//import com.sun.javacard.impl.NativeMethods;

public final class JCSystem
{
    private static final short API_VERSION = 0x0201;    
    static javacard.framework.PrivAccess thePrivAccess; 
    JCSystem(){}
    public static final byte NOT_A_TRANSIENT_OBJECT = 0;
    public static final byte CLEAR_ON_RESET = 1;
    public static final byte CLEAR_ON_DESELECT = 2;

    /*@ 
      modifies \nothing;
    */
    public static native byte isTransient(Object theObj);

    /*@ 
      modifies SystemException.systemInstance._reason;
    */
    public static native boolean[] makeTransientBooleanArray(short length, 
							     byte event) 
	throws SystemException;

    
    /*@ 
      modifies SystemException.systemInstance._reason;
    */
    public static native byte[] makeTransientByteArray(short length, byte event) 
	throws SystemException;
    
    
    /*@ 
      modifies SystemException.systemInstance._reason;
    */
    public static native short[] makeTransientShortArray(short length, byte event)
	throws SystemException;

    
    /*@ 
      modifies SystemException.systemInstance._reason;
    */
    public static native Object[] makeTransientObjectArray(short length, byte event) 
	throws SystemException;
    
    /*@ 
      modifies \nothing;
    */    
    public static short getVersion(){
	return JCSystem.API_VERSION;
    }
    
    /*@ 
      modifies \nothing;
    */
    public static AID getAID(){
	return javacard.framework.JCSystem.thePrivAccess.getAID(javacard.framework.PrivAccess.getCurrentAppID() );
    }

    /*@ 
      modifies \nothing;
    */
    public static AID lookupAID( byte[] buffer, short offset, 
				 byte length )
    throws NullPointerException,
           ArrayIndexOutOfBoundsException{    
	return javacard.framework.JCSystem.thePrivAccess.getAID(buffer, offset, length);
    }

    /*@ 
      modifies _transactionDepth, TransactionException.systemInstance._reason;
    */
    public static native void beginTransaction() throws TransactionException;

    /*@
      modifies _transactionDepth, TransactionException.systemInstance._reason;
    */
    public static native void abortTransaction() throws TransactionException;
    
    /*@
      modifies _transactionDepth, TransactionException.systemInstance._reason;       
    */
    public static native void commitTransaction() throws TransactionException;
    
    /*@
      modifies \nothing;
    */
    public static native byte getTransactionDepth();
    
    /*@ 
      modifies nothing;
    */
    public static native short getUnusedCommitCapacity();
    
    /*@ 
      modifies \nothing;
    */
    public static native short getMaxCommitCapacity();

    /*@
      modifies \nothing;
    */
    public static AID getPreviousContextAID(){	
	//Code temporaly removed by Néstor CATAÑO
	//return javacard.framework.JCSystem.thePrivAccess.getAID( (byte)
	//				      (NativeMethods.getPreviousContext() & javacard.framework.PrivAccess.APPID_BITMASK)
	//				      );
    }

    /*@ 
      modifies \nothing;
    */
    public static Shareable getAppletShareableInterfaceObject(javacard.framework.AID serverAID, 
                                                              byte parameter)
    throws NullPointerException,
           ArrayIndexOutOfBoundsException{
	return javacard.framework.JCSystem.thePrivAccess.getSharedObject(serverAID, parameter);
    }
}
