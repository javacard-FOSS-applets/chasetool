package com.gemplus.pacap.purse;

import javacard.framework.AID;
import javacard.framework.JCSystem;
import javacard.framework.Shareable;

import com.gemplus.pacap.pacapinterfaces.PurseLoyaltyInterface;
import com.gemplus.pacap.pacapinterfaces.TransactionInterface;

public class LoyaltyInterfaceObject /* extends Object*/ implements PurseLoyaltyInterface {
    public static byte interfaceID = (byte)0x01;
    private static Purse purse = null;
    
    
    LoyaltyInterfaceObject (Purse p){
	super();
	purse = p;
    }    
    
    public TransactionInterface getTransaction(javacard.framework.AID loyaltyAID) {
	TransactionInterface resu = null;
	resu = purse.getTransaction(loyaltyAID);
	return resu;
    }
    
    public boolean isThereTransaction(javacard.framework.AID loyaltyAID) {
	return purse.isThereTransaction(loyaltyAID);
    }
            
    public short getInvExchangeRateDecPart() {
	return purse.getInvExchangeRate().getDecPart(); 
    }
    
    public short getInvExchangeRateIntPart() {
	return purse.getInvExchangeRate().getIntPart(); 
    }
   
}
