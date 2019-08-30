package com.gemplus.pacap.purse;

import com.gemplus.pacap.utils.*;

public class Date /*extends Object*/ {       
    private byte jour	= Jour.MIN;
    private byte mois	= Mois.MIN;    
    private byte annee	= Annee.MIN;
    
    public Date() {
	super();
    }
            
    public Date(byte j, byte m, byte a) throws DateException{
	super();
	// check the day     
	if(!Jour.check(j)) {
	    DateException.throwIt(DateException.ERREUR_JOUR);
	} else {
	    // check the months
	    if(!Mois.check(m)) {
		DateException.throwIt(DateException.ERREUR_MOIS);
	    } else {
		// check the year
		if(!Annee.check(a)) {
		    DateException.throwIt(DateException.ERREUR_ANNEE);
                } else {
		    // all is good
		    jour = j;
		    mois = m;
		    annee = a;
		}
	    }
	}
    }            
    
    /*@
      modifies \nothing ;
    */
    public byte getJour() {
	return jour;
    }

    /*@
      modifies \nothing ;
    */
    public byte getMois() {
	return mois;
    }
    
    /*@
      modifies \nothing ;
    */
    public byte getAnnee() {
	return annee;
    }
    
    /*@
      modifies bArray[*] ;
    */
    public short getDate(byte [] bArray, short offset) {
	short aux = offset;
	// assume aux >= 0 && aux <= 256 ;
	bArray[aux++] = jour;
	bArray[aux++] = mois;
	bArray[aux++] = annee;
	return aux;
    }
    
    /*@ 
      modifies jour, mois, annee;
      modifies com.gemplus.pacap.purse.DateException.instance, com.gemplus.pacap.purse.DateException.instance.type;//!!
    */
    public void setDate(byte j, byte m, byte a) throws DateException {
	// check the day         
	if(!com.gemplus.pacap.utils.Jour.check(j)){
	    com.gemplus.pacap.purse.DateException.throwIt(com.gemplus.pacap.purse.DateException.ERREUR_JOUR);
	} else {
	     // check the month
	    if(!com.gemplus.pacap.utils.Mois.check(m)) {
		com.gemplus.pacap.purse.DateException.throwIt(com.gemplus.pacap.purse.DateException.ERREUR_MOIS);
	    } else {
		// check the year
		if(!com.gemplus.pacap.utils.Annee.check(a)) {
		    com.gemplus.pacap.purse.DateException.throwIt(com.gemplus.pacap.purse.DateException.ERREUR_ANNEE);
		} else {
		    // all is good
		    jour = j;
		    mois = m;
		    annee = a;
		}
	    }
	}
    }
    
    /*@ 
      modifies jour, mois, annee ;
      modifies com.gemplus.pacap.purse.DateException.instance, com.gemplus.pacap.purse.DateException.instance.type;//!!
    */
    public void setDate(com.gemplus.pacap.purse.Date d) throws DateException {
	setDate(d.getJour(), d.getMois(), d.getAnnee());
    }
    
    /*@ 
      modifies \nothing;
    */
    public boolean before(com.gemplus.pacap.purse.Date d) {
	if(d.getAnnee() < annee) return false;
	else if(d.getAnnee() > annee) return true;
	else if(d.getMois() < mois) return false;
	else if(d.getMois() > mois) return true;
	else if(d.getJour() <= jour) return false;
	else return true;
    }

    /*@
      modifies \nothing;
    */
    public boolean after(com.gemplus.pacap.purse.Date d) {
	if(d.getAnnee() > annee) return false;
	else if(d.getAnnee() < annee) return true;
	else if(d.getMois() > mois) return false;
	else if(d.getMois() < mois) return true;
	else if(d.getJour() > jour) return false;
	else return true;
    }
}
