package com.gemplus.pacap.pacapinterfaces;
import com.gemplus.pacap.utils.*;
import javacard.framework.Shareable;


public interface TransactionInterface extends Shareable {
    public final short ID_LENGTH = (short)4;    
    
    /*@
      modifies \nothing;
     */
    public short getIntPartMontant();    
   
    /*@
      modifies \nothing;
    */
    public short getDecPartMontant();
    
    /*@
      modifies \nothing;
    */
    public byte getDevise();
    
    /*@
      modifies \nothing;
    */
    public byte getId(byte n);    
    
    /*@
      modifies \nothing;
    */
    public short getIdLength();
    
    /*@
      modifies \nothing;
    */
    public byte getReste();

    /*@
      modifies \nothing;
    */
    public short getTypeProduit();
    
    /*@
      modifies \nothing;
    */
    public byte getMois();    
    
    /*@
      modifies \nothing;
    */
    public byte getJour();
    
   
    /*@
      modifies \nothing;
    */
    public byte getAnnee();
    
}

