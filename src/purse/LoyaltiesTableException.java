package com.gemplus.pacap.purse;

import com.gemplus.pacap.utils.PacapException;

class LoyaltiesTableException extends com.gemplus.pacap.utils.PacapException{       
    static final byte NB_LOYALTIES_OVERFLOW = (byte) 0x01;
    
    private static com.gemplus.pacap.purse.LoyaltiesTableException instance;
        
    LoyaltiesTableException (byte code){
        super(code);     
    }
    
    /*@
      modifies instance, instance.type;
    */
    public static void throwIt (byte t) throws LoyaltiesTableException {
        if ( instance == null ) {
            instance = new LoyaltiesTableException(t);
        }
        else {
            instance.setType(t);
        }
        throw instance;
    }
}

