package com.gemplus.pacap.utils;
public class DecimalException extends com.gemplus.pacap.utils.PacapException{    
    public static final byte DECIMAL_INDETERMINE           = (byte) 0;
    public static final byte DECIMAL_OVERFLOW              = (byte) 0x01;
    public static final short SALERS_TABLE_FULL			  = (short)0x9F14;
    
    private static com.gemplus.pacap.utils.DecimalException instance;
    
    DecimalException (){
        super(DECIMAL_INDETERMINE);        
    }

    DecimalException (byte code){
        super(code);        
    }

    /*@ 
      modifies instance, instance.type;
    */
    public static void throwIt (byte t) throws DecimalException {
        if( instance == null ){
            instance = new DecimalException(t);
        }
        else{
            instance.setType(t);
        }
        throw instance;
    }
}

