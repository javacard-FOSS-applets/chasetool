package javacard.framework;

public class PrivAccess{   
    public static final byte APPID_BITMASK = (byte)0xF;
    
    //@modifies \nothing;
    public javacard.framework.AID getAID(byte[] aidArray, short aidOff, byte aidLength) 
	throws NullPointerException,
	       ArrayIndexOutOfBoundsException;
    
    //@modifies \nothing;
    public javacard.framework.AID getAID( byte appID ) ;
    
    //@modifies \nothing;
    public static byte getCurrentAppID();

    //@modifies \nothing;
    public Shareable getSharedObject( javacard.framework.AID serverAID, byte param ) 
    throws NullPointerException, 
           ArrayIndexOutOfBoundsException;
}
