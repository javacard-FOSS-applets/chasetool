package com.gemplus.pacap.purse;

import com.gemplus.pacap.utils.PacapException;

public class DateException extends  com.gemplus.pacap.utils.PacapException{    
    public static final byte ERREUR_JOUR    = (byte) 1;
    public static final byte ERREUR_MOIS    = (byte) 2;
    public static final byte ERREUR_ANNEE   = (byte) 3;
    
    private static com.gemplus.pacap.purse.DateException instance;
  
    
    DateException (byte code){
        super(code);
    }
    
    /*@
      modifies instance, instance.type ;
    */ 
    public static void throwIt (byte t) throws DateException {
        if ( instance == null ) {
            instance = new DateException(t);
	}
	else {
	    instance.setType(t);
	    }
	throw instance;
    }    
}
