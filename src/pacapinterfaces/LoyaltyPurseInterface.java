package com.gemplus.pacap.pacapinterfaces;

import javacard.framework.Shareable;

public interface LoyaltyPurseInterface extends Shareable {
    
    /*@
      modifies \nothing;
    */
    public void logFull();
   
    /*@
      modifies \nothing;
    */
    public void exchangeRate();
}

