
package javacard.framework;

public interface PIN {
    /*@ 
      modifies \nothing;
    */    
    byte getTriesRemaining();

 
   /*@
     modifies \fields_of(this);
   */
   boolean isValidated()
   throws SystemException; 

   void reset()
   throws SystemException; 


    /*@ 
      modifies SystemException.systemInstance._reason; 
    */    
    public boolean check(byte[] pin, 
                         short offset, 
			 byte length)			 
   throws ArrayIndexOutOfBoundsException, 
          NullPointerException,
          SystemException;
}
