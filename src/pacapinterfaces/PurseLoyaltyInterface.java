package com.gemplus.pacap.pacapinterfaces;

import javacard.framework.Shareable;
import javacard.framework.AID;


public interface PurseLoyaltyInterface extends Shareable { 
   
    /*@
      modifies \nothing;
    */
    public TransactionInterface getTransaction(AID loyaltyAID);
    
    /*@
      modifies \nothing;
    */
    public boolean isThereTransaction(AID loyaltyAID);
    
    
    /*@
      modifies \nothing;
    */
    public short getInvExchangeRateIntPart();
    
   
    /*@
      modifies \nothing;
    */
    public short getInvExchangeRateDecPart();
}
