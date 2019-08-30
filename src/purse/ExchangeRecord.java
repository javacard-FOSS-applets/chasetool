package com.gemplus.pacap.purse;

import javacard.framework.TransactionException;
import javacard.framework.JCSystem;


class ExchangeRecord {
    private static final byte MAX_ENTRIES = (byte)10;
    private byte nb_entries = (byte)0;
    private short newIndex = (byte)0;
    private short firstIndex = (byte)0;
    private com.gemplus.pacap.purse.ExchangeSession[] data = new com.gemplus.pacap.purse.ExchangeSession[MAX_ENTRIES];
    
    ExchangeRecord () {
	for(short i = 0;i < MAX_ENTRIES;i++) {
	    data[i] = new ExchangeSession();
	}
    }

    /*@
      modifies \nothing;
    */
    byte getNbEntries() {
	return nb_entries;
    }

    /*@
      modifies \nothing;
    */
    ExchangeSession getExchangeSession(short index) {
	return data[index%10];
    }

    /*@
      modifies data[newIndex].sessionNumber;//!!
      modifies data[newIndex].date.jour, data[newIndex].date.mois, data[newIndex].date.annee;//!!
      modifies com.gemplus.pacap.purse.DateException.instance, com.gemplus.pacap.purse.DateException.instance.type;//!!
      modifies data[newIndex].heure.heure, data[newIndex].heure.minute;//!!
      modifies com.gemplus.pacap.purse.HeureException.instance, com.gemplus.pacap.purse.HeureException.instance.type;//!!
      modifies data[newIndex].id[*]; //!!
      modifies TransactionException.systemInstance._reason;//!!
      modifies data[newIndex].terminalTC[*]; //!!
      modifies data[newIndex].terminalSN[*];//!!
      modifies data[newIndex].ancienneDevise, data[newIndex].nouvelleDevise, data[newIndex].isValid, data[newIndex].status;//!!
      modifies data[newIndex].isValid;//!!
      modifies newIndex;//!!
      modifies nb_entries;//!!
      modifies firstIndex;//!!
    */
    void addExchangeSession(com.gemplus.pacap.purse.ExchangeSession t)
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException {
	data[newIndex].clone(t);
	data[newIndex].valid();
	newIndex = nextIndex(newIndex);
	if(nb_entries < MAX_ENTRIES){
	    nb_entries++;
	} else {
	    firstIndex = nextIndex(firstIndex);
	}
    }

    /*@
      modifies \nothing;
    */
    boolean isFull() {
	return nb_entries == MAX_ENTRIES;
    }

    /*@
      modifies data[index].isValid;//!!
      //modifies data[*].date.jour,data[*].date.mois,data[*].date.annee;
      //modifies com.gemplus.pacap.purse.DateException.instance, com.gemplus.pacap.purse.DateException.instance.type;
      //modifies data[*].heure.heure, data[*].heure.minute;
      //modifies com.gemplus.pacap.purse.HeureException.instance, com.gemplus.pacap.purse.HeureException.instance.type;
      //modifies data[*].id[*];
      //modifies TransactionException.systemInstance._reason;
      //modifies data[*].terminalTC[*];
      //modifies data[*].terminalSN[*];
      //modifies data[*].ancienneDevise, data[*].nouvelleDevise, data[*].isValid, data[*].status;
      //
      //modifies fields_of(data[*]);
      //
      modifies nb_entries, newIndex;//!!
    */
    void deleteExchangeSession(short index)
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException  {
	if(nb_entries > 0) {
	    data[index].unvalid();
	    defrag(index);
	}
    }

    /*@   
      //modifies data[*].sessionNumber;
      //modifies data[*].date.jour,data[*].date.mois,data[*].date.annee;
      //modifies com.gemplus.pacap.purse.DateException.instance, com.gemplus.pacap.purse.DateException.instance.type;
      //modifies data[*].heure.heure, data[*].heure.minute;
      //modifies com.gemplus.pacap.purse.HeureException.instance, com.gemplus.pacap.purse.HeureException.instance.type;
      //modifies data[*].id[*];
      //modifies TransactionException.systemInstance._reason;
      //modifies data[*].terminalTC[*];
      //modifies data[*].terminalSN[*];
      //modifies data[*].ancienneDevise, data[*].nouvelleDevise, data[*].isValid, data[*].status;
      //
      //modifies fields_of(data[*]);
      //
      modifies nb_entries, newIndex;
    */ 
    private void defrag(short index)
	throws ArrayIndexOutOfBoundsException, 
	       NullPointerException, 
	       TransactionException  {
	for(short i = nextIndex(index);i != newIndex; i = nextIndex(i)) {
	    data[prevIndex(i)].clone(data[i]);
	    data[i].reset();
	}
	nb_entries--;
	newIndex = prevIndex(newIndex);
    }
    
    /*@
      modifies \nothing;
    */
    private short prevIndex(short i) {
	i--;
	return (short)(i == (short)-1 ? MAX_ENTRIES - (short)1 : i);
    }

    /*@
      modifies \nothing;
    */
    private short nextIndex(short i) {
	i++;
	return (short)(i == MAX_ENTRIES ? 0 : i);
    }
}

