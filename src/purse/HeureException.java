package com.gemplus.pacap.purse;

import com.gemplus.pacap.utils.PacapException;

public class HeureException extends com.gemplus.pacap.utils.PacapException{        
    public static final byte ERREUR_HEURE    = (byte) 1;
    public static final byte ERREUR_MINUTE    = (byte) 2;
    
    private static com.gemplus.pacap.purse.HeureException instance;    
           
    HeureException (byte code){
        super(code);
    }
        
    /*@
      modifies instance, instance.type;
    */
    public static void throwIt (byte t) throws HeureException {
        if ( instance == null ) {
            instance = new HeureException(t);
        }
        else {
            instance.setType(t);
        }
        throw instance;
    }
}
