package com.gemplus.pacap.purse;

import com.gemplus.pacap.utils.PacapException;

class AllowedLoyaltyException extends com.gemplus.pacap.utils.PacapException{    
    static final byte NB_SALERS_OVERFLOW = (byte) 0x01;
    private static com.gemplus.pacap.purse.AllowedLoyaltyException instance;          
    
    AllowedLoyaltyException (byte code){
        super(code);  
    }
    
    /*@
      modifies instance, instance.type ;
    */
    public static void throwIt (byte t) throws AllowedLoyaltyException {
        if ( instance == null ) {
            instance = new AllowedLoyaltyException(t);
        }
        else {
            instance.setType(t);
        }
        throw instance;
    }
    
}

