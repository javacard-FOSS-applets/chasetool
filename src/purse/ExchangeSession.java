package com.gemplus.pacap.purse;

import javacard.framework.Util;
import javacard.framework.TransactionException;
import javacard.framework.JCSystem;

public class ExchangeSession {    
    public static final byte ID_LENGTH			= (byte)4;
    public static final byte TTC_LENGTH			= (byte)4;
    public static final byte TSN_LENGTH			= (byte)4;
    public static final byte WELL_TERMINATED		= (byte)0x01;
    public static final byte ABORTED			= (byte)0x02;
    
    private short sessionNumber;
    private com.gemplus.pacap.purse.Date date = new com.gemplus.pacap.purse.Date();    
    private com.gemplus.pacap.purse.Heure heure = new com.gemplus.pacap.purse.Heure();    
    private byte[] id = new byte[ID_LENGTH];    
    private byte ancienneDevise;    
    private byte nouvelleDevise;    
    private byte[] terminalTC = new byte[TTC_LENGTH];
    private byte[] terminalSN = new byte[TSN_LENGTH];        
    private byte status = ABORTED;
    boolean isValid = false;    
    
    /*@
      modifies sessionNumber, ancienneDevise, nouvelleDevise, isValid,
               terminalTC[*], terminalSN[*], date.jour, date.mois, date.annee,
               heure.heure, heure.minute;
      modifies com.gemplus.pacap.purse.DateException.instance, com.gemplus.pacap.purse.DateException.instance.type;//!!
      modifies com.gemplus.pacap.purse.HeureException.instance, com.gemplus.pacap.purse.HeureException.instance.type;//!!
     */
    void reset() {
	sessionNumber = (short)-1;
	try {
	    date.setDate((byte)1, (byte)1, (byte)99);
	    heure.setHeure((byte)0, (byte)0);
	} catch(DateException de) {
	    //comment out by Néstor CATAÑO
	    //} catch(HeureException ee) {
	}
	ancienneDevise = (byte)0;
	nouvelleDevise = (byte)0;
	//Modified by Néstor CATAÑO
	//javacard.framework.Util.arrayFillNonAtomic(terminalTC, (short)0, TTC_LENGTH, (byte)0);
	javacard.framework.Util.arrayFillNonAtomic(terminalTC, (short)0, (short)TTC_LENGTH, (byte)0);
	//Modified by Néstor CATAÑO
	//javacard.framework.Util.arrayFillNonAtomic(terminalSN, (short)0, TSN_LENGTH, (byte)0);
	javacard.framework.Util.arrayFillNonAtomic(terminalSN, (short)0, (short)TSN_LENGTH, (byte)0);
	isValid = false;
    }

    /*@
      modifies sessionNumber;
      modifies date.jour, date.mois, date.annee;
      modifies com.gemplus.pacap.purse.DateException.instance, com.gemplus.pacap.purse.DateException.instance.type;//!!
      modifies heure.heure, heure.minute;
      modifies com.gemplus.pacap.purse.HeureException.instance, com.gemplus.pacap.purse.HeureException.instance.type;//!!
      modifies id[*];    
      modifies TransactionException.systemInstance._reason;//!!
      modifies terminalTC[*]; 
      modifies terminalSN[*];
      modifies ancienneDevise, nouvelleDevise, isValid, status;
    */
    void clone(com.gemplus.pacap.purse.ExchangeSession es)
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException {
	sessionNumber = es.getSessionNumber();
 	try {
 	    date.setDate(es.getDate());
 	    heure.setHeure(es.getHeure());
 	    //comment out by Néstor CATAÑO
 	    //} catch(DateException de) {
 	} catch(HeureException ee) {
         }
	//modified by Néstor CATAÑO:javacard.framework.Util.arrayCopy(es.getId(), (short)0, id, (short)0, ID_LENGTH);
	javacard.framework.Util.arrayCopy(es.getId(), (short)0, id, (short)0, (short)ID_LENGTH);
	//modified by Néstor CATAÑO:javacard.framework.Util.arrayCopy(es.getTerminalTC(), (short)0, terminalTC, (short)0, TTC_LENGTH);
	javacard.framework.Util.arrayCopy(es.getTerminalTC(), (short)0, terminalTC, (short)0, (short)TTC_LENGTH);
	//modified by Néstor CATAÑO:javacard.framework.Util.arrayCopy(es.getTerminalSN(), (short)0, terminalSN, (short)0, TSN_LENGTH);
	javacard.framework.Util.arrayCopy(es.getTerminalSN(), (short)0, terminalSN, (short)0, (short)TSN_LENGTH);

	ancienneDevise = es.getAncienneDevise();
	nouvelleDevise = es.getNouvelleDevise();
	isValid = es.isValid();
	status = es.getStatus();
    } 
    
    /*@
      modifies \nothing;
    */
    byte[] getId() {
	return id;
    }

    /*@
      modifies \nothing;
    */
	byte[] getTerminalTC() {
		return terminalTC;
	}

    /*@
      modifies \nothing;
    */
    byte[] getTerminalSN() {
	return terminalSN;
    }

    /*@
      modifies id[*];
    */
    void setId(byte[] bArray, short off) {
	//modified by Néstor CATAÑO:javacard.framework.Util.arrayCopyNonAtomic(bArray, off, id, (short)0, ID_LENGTH);
	javacard.framework.Util.arrayCopyNonAtomic(bArray, off, id, (short)0, (short)ID_LENGTH);
    }

    /*@
      modifies terminalTC[*];
    */
    void setTerminalTC(byte[] bArray, short off) {
	//modified by Néstor CATAÑO:javacard.framework.Util.arrayCopyNonAtomic(bArray, off, terminalTC, (short)0, TTC_LENGTH);
	javacard.framework.Util.arrayCopyNonAtomic(bArray, off, terminalTC, (short)0, (short)TTC_LENGTH);
    }
    
    /*@
      modifies terminalSN[*];
    */
    void setTerminalSN(byte[] bArray, short off) {
	//modified by Néstor CATAÑO:javacard.framework.Util.arrayCopyNonAtomic(bArray, off, terminalSN, (short)0, TSN_LENGTH);
	javacard.framework.Util.arrayCopyNonAtomic(bArray, off, terminalSN, (short)0, (short)TSN_LENGTH);
    }

    /*@
      modifies bArray[*];
    */
    short getId(byte[] bArray, short off) {
	short offset = off;
	//modifies by Néstor CATAÑO:javacard.framework.Util.arrayCopyNonAtomic(id, (short)0, bArray, offset, ID_LENGTH);
	offset = javacard.framework.Util.arrayCopyNonAtomic(id, (short)0, bArray, offset, (short)ID_LENGTH);
        return offset;
    }

    /*@
      modifies bArray[*];
    */
    short getTerminalTC(byte[] bArray, short off) {
	short offset = off;
	//modifies by Néstor CATAÑO:javacard.framework.Util.arrayCopyNonAtomic(terminalTC, (short)0, bArray, offset, TTC_LENGTH);
	offset = javacard.framework.Util.arrayCopyNonAtomic(terminalTC, (short)0, bArray, offset, (short)TTC_LENGTH);
	return offset;
    }

    /*@
      modifies bArray[*];
    */
    short getTerminalSN(byte[] bArray, short off) {
	short offset = off;
	//modified by Néstor CATAÑO:javacard.framework.Util.arrayCopyNonAtomic(terminalSN, (short)0, bArray, offset, TSN_LENGTH);
	offset = javacard.framework.Util.arrayCopyNonAtomic(terminalSN, (short)0, bArray, offset, (short)TSN_LENGTH);
	return offset;
    }

    /*@
      modifies \nothing;
     */
    short getSessionNumber() {
	return sessionNumber;
	}
    
    /*@
      modifies sessionNumber;
    */
    void setSessionNumber(short n) {
	sessionNumber = n;
    }
    
    /*@
      modifies \nothing;
    */
    com.gemplus.pacap.purse.Date getDate() {
	return date;
    }

    /*@
      modifies date.jour, date.mois, date.annee;
      modifies com.gemplus.pacap.purse.DateException.instance, com.gemplus.pacap.purse.DateException.instance.type;//!!
    */
    void setDate(com.gemplus.pacap.purse.Date d) throws DateException {
	date.setDate(d);
    }
    
    /*@
      modifies \nothing;
    */
    com.gemplus.pacap.purse.Heure getHeure() {
	return heure;
    }

    /*@
      modifies heure, heure.heure, heure.minute;
      modifies com.gemplus.pacap.purse.HeureException.instance, com.gemplus.pacap.purse.HeureException.instance.type;//!!
    */
    void setHeure(com.gemplus.pacap.purse.Heure h) throws HeureException {
	heure.setHeure(h);
    }

    /*@
      modifies \nothing;
    */
    byte getAncienneDevise() {
	return ancienneDevise;
    }
    
    /*@
      modifies ancienneDevise;
    */
	void setAncienneDevise(byte d) {
		ancienneDevise = d;
	}
    
    /*@
      modifies \nothing;
    */
    byte getNouvelleDevise() {
	return nouvelleDevise;
    }
    
    /*@
      modifies nouvelleDevise;
    */
    void setNouvelleDevise(byte d) {
	nouvelleDevise = d;
    }

    /*@
      modifies \nothing ;
    */
    boolean isValid() {
	return isValid;
    }
    
    /*@
      modifies isValid;
    */
    void valid() {
	isValid = true;
    }
    
    /*@
      modifies isValid;
    */
    void unvalid() {
	isValid = false;
    }

    /*@
      modifies status;
    */
    void setStatus(byte s) {
	status = s;
    }
    
    /*@
      modifies \nothing;
    */
    byte getStatus() {
	return status;
    }

}

