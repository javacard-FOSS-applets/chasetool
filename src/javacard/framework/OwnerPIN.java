package javacard.framework;

public class OwnerPIN implements javacard.framework.PIN{
    private byte tryLimit;
    private byte maxPINSize;
    private byte[] pinValue;
    private byte pinSize;
    private boolean[] flags; 
    private static final byte VALIDATED = (byte)0;
    private static final byte NUMFLAGS = (byte)(VALIDATED+1);
  
    /*@ 
      modifies flags;
      modifies SystemException.systemInstance._reason; 
      modifies flags[VALIDATED]; //!!
    */
    private void createFlags() 
	throws SystemException{
	if (flags!=null) return;
	//Code modified by Néstor CATAÑO
	//flags = javacard.framework.JCSystem.makeTransientBooleanArray(NUMFLAGS, javacard.framework.JCSystem.CLEAR_ON_RESET);
	flags = javacard.framework.JCSystem.makeTransientBooleanArray((short)NUMFLAGS, javacard.framework.JCSystem.CLEAR_ON_RESET);
	setValidatedFlag(false);
    }
    
    /*@ 
      modifies flags; //!!
      modifies SystemException.systemInstance._reason;
      modifies flags[VALIDATED]; //!!
    */
    protected boolean getValidatedFlag() 
	throws SystemException{
	createFlags();
	return flags[VALIDATED];
    }

    /*@
      modifies flags; //!!
      modifies SystemException.systemInstance._reason; 
      modifies flags[VALIDATED]; //!!
    */
    protected void setValidatedFlag(boolean value) 
	throws SystemException{
	createFlags();
	flags[VALIDATED] = value;
    }

    /*@ 
      modifies flags;
      modifies SystemException.systemInstance._reason;   
      modifies flags[VALIDATED]; //!!
    */
    public boolean isValidated()
	throws SystemException{
	return getValidatedFlag();
    }
    
    private byte[] triesLeft;
    private byte[] temps;  
    private static final byte TRIES = (byte)0; 
    private static final byte NUMTEMPS = (byte)(TRIES+1);
    
    /*@ 
      modifies triesLeft[0];
    */
    private void resetTriesRemaining() 
    { 
	triesLeft[0] = tryLimit;
    }
  
    /*@ 
      modifies triesLeft[0], temps[TRIES];
      modifies SystemException.systemInstance._reason;
      modifies temps; //!!
      modifies temps[TRIES]; //!!
      modifies triesLeft[*]; //!!
    */
    private void decrementTriesRemaining() throws SystemException{
	if (temps==null) 
	    //code modified by Néstor CATAÑO
	    //temps = javacard.framework.JCSystem.makeTransientByteArray(NUMTEMPS, javacard.framework.JCSystem.CLEAR_ON_RESET);
	    temps = javacard.framework.JCSystem.makeTransientByteArray((short)NUMTEMPS, javacard.framework.JCSystem.CLEAR_ON_RESET);
	
	temps[TRIES] = (byte)(triesLeft[0]-1);
	//code modified by Néstor CATAÑO
	//javacard.framework.Util.arrayCopyNonAtomic( temps, TRIES, triesLeft, (short)0, (short)1 );
	javacard.framework.Util.arrayCopyNonAtomic( temps, (short)TRIES, triesLeft, (short)0, (short)1 );
    }

    /*@ 
      modifies \nothing;
    */    
    public byte getTriesRemaining(){
	return triesLeft[0];
    }
  
      
    /*@ 
      modifies \fields_of(this);
    */
  public OwnerPIN(byte tryLimit, byte maxPINSize) 
      throws PINException{
      if ((tryLimit<1) || (maxPINSize<1)) 
	  PINException.throwIt(PINException.ILLEGAL_VALUE);
      pinValue = new byte[maxPINSize]; 
    this.pinSize = maxPINSize; 
    this.maxPINSize = maxPINSize;
    this.tryLimit = tryLimit;
    triesLeft = new byte[1];
    resetTriesRemaining();
  }
  
    
    /*@
      modifies flags; 
      modifies SystemException.systemInstance._reason; 
      modifies flags[VALIDATED];
      modifies triesLeft[0], temps[TRIES];
      modifies SystemException.systemInstance._reason;
      modifies temps;
      modifies triesLeft[*]; //!!
    */
    public boolean check(byte[] pin, short offset, byte length)
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException,
	       SystemException{
	
	setValidatedFlag(false);
	if ( getTriesRemaining() == 0 ) 
	    return false;
	decrementTriesRemaining();
	if (length!=pinSize) 
	    return false;
	if (javacard.framework.Util.arrayCompare(pin, offset, pinValue, (short)0, (short)length)==(byte)0){
	    setValidatedFlag(true);
	    resetTriesRemaining();
	    return true;
        }
	return false;
    }
    

    /*@ 
      modifies flags; 
      modifies SystemException.systemInstance._reason;   
      modifies flags[VALIDATED];
      modifies triesLeft[0];
    */
    public void reset()
	throws SystemException{
	if (isValidated()) resetAndUnblock();
    }
  
    
    /*@ 
      modifies PINException.systemInstance._reason;
      modifies SystemException.systemInstance._reason;
      modifies TransactionException.systemInstance._reason;
      modifies pinValue[*];//!!
      modifies pinSize; //!!
      modifies triesLeft[0]; //!!
      modifies flags; //!!
      modifies flags[VALIDATED]; //!!
    */
    public void update(byte[] pin, short offset, byte length)
	throws PINException,
	       NullPointerException,
	       ArrayIndexOutOfBoundsException,
	       TransactionException,
	       SystemException{
	//Code temporally removed by Néstor CATAÑO
	//if ( length>maxPINSize ) PINException.throwIt( PINException.ILLEGAL_VALUE );
	//Code modified by Néstor CATAÑO
	//javacard.framework.Util.arrayCopy( pin, offset, pinValue, (short)0, length );
	javacard.framework.Util.arrayCopy( pin, offset, pinValue, (short)0, (short)length );
	pinSize = length;
	resetTriesRemaining();
	setValidatedFlag(false);
    }

    /*@ 
      modifies SystemException.systemInstance._reason; 
      modifies triesLeft[0]; //!!
      modifies flags; //!!
      modifies flags[VALIDATED]; //!!
    */    
    public void resetAndUnblock()
	throws SystemException{
	resetTriesRemaining();
	setValidatedFlag(false);
    }
}
