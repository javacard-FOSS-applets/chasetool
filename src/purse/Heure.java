package com.gemplus.pacap.purse;

public class Heure /*extends Object*/{
     private byte heure;
     private byte minute;
     
     /*@ 
       modifies heure, minute ;
       modifies com.gemplus.pacap.purse.HeureException.instance, com.gemplus.pacap.purse.HeureException.instance.type;//!!
     */
     public void setHeure(byte h, byte m) throws HeureException{
	  if ( 0 <= h && h < 24 ){
	     if ( 0 <= m && m < 60 ){
		 heure = h;
		 minute = m;
	     }
	     else com.gemplus.pacap.purse.HeureException.throwIt(com.gemplus.pacap.purse.HeureException.ERREUR_MINUTE);
	  }
	  else com.gemplus.pacap.purse.HeureException.throwIt(com.gemplus.pacap.purse.HeureException.ERREUR_HEURE);
     } 
     
     /*@ 
       modifies heure, minute ;  
       modifies com.gemplus.pacap.purse.HeureException.instance, com.gemplus.pacap.purse.HeureException.instance.type;//!!  
     */
     public void setHeure(com.gemplus.pacap.purse.Heure h) throws HeureException{
	 setHeure(h.getHeure(), h.getMinute());
     } 
     
     /*@
       modifies \nothing;
     */
     public byte getHeure(){
	 return heure;
     }
     
     /*@
       modifies \nothing;
     */
     public byte getMinute(){
	 return minute;
     }
     
     /*@
       modifies bArray[*];
     */
    public short  getHeure(byte [] bArray, short offset){
        short aux = offset;
        bArray[aux++] = heure;
	bArray[aux++] = minute;
        return (short) (offset + (short) 2);
    }
    
 }
