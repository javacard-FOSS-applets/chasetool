package com.gemplus.pacap.utils;

public class  PacapException /*Exception*/{    
    public static final byte INDETERMINE = (byte) 0;
    private byte type = INDETERMINE;
    
    private static com.gemplus.pacap.utils.PacapException instance = null;    
    
    public PacapException (byte code){
        super();
        type = code;
    }    
    
    public PacapException(){
        this(INDETERMINE);        
    }
     
    /*@  
      modifies instance, instance.type ;
    */
    public static void throwIt (byte t) throws PacapException {
        if ( instance == null ) {
            instance = new PacapException(t);
        }
        else {
            instance.setType(t);
        }
        throw instance;
    }
    
    /*@
      modifies \nothing ;
    */
    public byte getType(){
        return type;
    }
    
    /*@  
      modifies type ;
    */
    public void setType(byte code){
        type = code;
    }
}

