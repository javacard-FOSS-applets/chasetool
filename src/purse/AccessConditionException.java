package com.gemplus.pacap.purse;
import com.gemplus.pacap.utils.PacapException;

public class AccessConditionException extends com.gemplus.pacap.utils.PacapException{    
    public static final byte CONDITION_COURANTE_INVALIDE     = (byte) 1;
    
    private static com.gemplus.pacap.purse.AccessConditionException instance;
    
    AccessConditionException (byte code){
        super(code);
    }      
    
    /*@ 
      modifies instance, instance.type;
    */
    public static void throwIt (byte t) throws AccessConditionException {
	if ( instance == null ) {
	    instance = new AccessConditionException(t);
	    throw instance;
	}
	else {
	    instance.setType(t);
	}
	return;
    }
}
