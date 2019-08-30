package com.gemplus.pacap.purse;

public class AccessCondition /*extends Object*/{    
    public static final byte FREE		= (byte)1;
    public static final byte LOCKED		= (byte)2;
    public static final byte SECRET_CODE	= (byte)4;
    public static final byte SECURE_MESSAGING	= (byte)8; 
    
    private byte condition = FREE;
        
    public AccessCondition() {
	super();
    }

    /*@
      modifies condition ;
    */
    public void reset() {
        condition = FREE;
    }
    
    /*@ 
      modifies condition ;
    */
    public void setCondition (byte c){
        condition = c;
    }
    
    /*@
      modifies condition ;
    */  
    public void addCondition(byte c) {
        byte x;
        if ( ( c == SECURE_MESSAGING || c == SECRET_CODE )
	     && ( condition == SECRET_CODE || condition == SECURE_MESSAGING )){
    	    condition |= c;
	}
                   
        else condition = c;
    }

    /*@
      modifies \nothing;
    */   
    public byte getCondition(){
        return condition;
    }    
    
    /*@
      modifies \nothing ;
    */
    public final boolean verify( com.gemplus.pacap.purse.AccessCondition  c) throws AccessConditionException {
	return verify(c.getCondition());
    }
   
    /*@
      modifies \nothing ;
    */
    private final boolean verify(byte c) throws AccessConditionException {
	byte t = (byte)0;
	switch(condition) {
	case FREE:
	    // no condition required
	    return true;
	case SECRET_CODE:
	    // secret code required
	    t = (byte)(c & SECRET_CODE);
	    return(t == SECRET_CODE);
	case SECURE_MESSAGING:
	    // secure messaging required
	    t = (byte)(c & SECURE_MESSAGING);
	    return (t == SECURE_MESSAGING);
	case SECRET_CODE | SECURE_MESSAGING:
	    // secret code and secure messaging
	    t = (byte)(c & (SECRET_CODE | SECURE_MESSAGING));
	    return (t == (SECRET_CODE | SECURE_MESSAGING));
	case LOCKED:
	    // we never get ther
	    return false;
	default:
	    t = AccessConditionException.CONDITION_COURANTE_INVALIDE;
	    AccessConditionException.throwIt(t);
	    return false;
	}
    }
    
    /*@
      modifies \nothing ;
    */
    public boolean secretCodeNeeded(){
       byte t = (byte) ( condition & SECRET_CODE );
        return ( t == SECRET_CODE );
    }
    
}
