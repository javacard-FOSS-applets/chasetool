/**
 * Copyright (c) 1999 GEMPLUS group. All Rights Reserved.
 *------------------------------------------------------------------------------
 *  Project name:  PACAP  - cas d'étude -
 *
 *
 *  Platform    :  Java virtual machine
 *  Language    :  JAVA 1.1.x
 *  Devl tool   :  Symantec VisualCafe
 *
 *  @author: Thierry Bressure
 *  @modified Jean Louis Lanet, Abdellah El Marouani, Ludovic Casset & Hugues Martin
 *  @version 2.0.1
 *------------------------------------------------------------------------------
 */


package com.gemplus.pacap.purse;

// temporarily removed by Rodolphe Muller on 05/07/2000
/*
import com.gemplus.pacap.utils.AccessControl;
// removed by Rodolphe Muller on 12/07/2000
import com.gemplus.pacap.utils.AppletState;
import com.gemplus.pacap.utils.AppletStateException;
import com.gemplus.pacap.utils.Date;
import com.gemplus.pacap.utils.DateException;
*/
// temporarily removed by Rodolphe Muller on 05/07/2000
/*
import com.gemplus.pacap.utils.Heure;
import com.gemplus.pacap.utils.HeureException;
import com.gemplus.pacap.utils.PacapKey;
import com.gemplus.pacap.utils.PacapSecureMessaging;
import com.gemplus.pacap.utils.PacapSignature;
*/

import com.gemplus.pacap.utils.Decimal;
import com.gemplus.pacap.utils.DecimalException;


import javacard.framework.AID;
import javacard.framework.Applet;
import javacard.framework.APDU;
import javacard.framework.JCSystem;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.Shareable;
import javacard.framework.Util;


//code added by Nestor CATANO and Marieke HUISMAN
import javacard.framework.SystemException;
import javacard.framework.TransactionException;
import javacard.framework.OwnerPIN;
import javacard.security.CryptoException ;


// added by Rodolphe Muller on 12/07/2000
////import visa.openplatform.OPSystem;


/* Description of the purse applet */
public class PurseApplet extends Applet {
    /*@
	invariant ADM_INIT_ADMINISTRATIVE_MODE		== 0x0A;
	invariant ADM_VERIFY_ADM_PIN			== 0x0C;
	invariant ADM_GET_ASN				== 0x0E;
	invariant ADM_GET_BALANCE			== 0x10;
	invariant ADM_GET_CREDIT_AMOUNT_WO_AUT		== 0x12;
	invariant ADM_SET_CREDIT_AMOUNT_WO_AUT		== 0x14;
	invariant ADM_GET_CURRENCY_TABLE		== 0x16;
	invariant ADM_SET_CURRENCY_TABLE		== 0x18;
	invariant ADM_DEL_CURRENCY_TABLE		== 0x1A;
	invariant ADM_GET_TRANSACTION_RECORD		== 0x1C;
	invariant ADM_GET_MAX_TRANSACTION_COUNTER	== 0x1E;
	invariant ADM_SET_MAX_TRANSACTION_COUNTER	== 0x20;
	invariant ADM_GET_EXCHANGE_RECORD		== 0x22;
	invariant ADM_GET_CURRENCY			== 0x24;
	invariant ADM_GET_EXCHANGE_RATE			== 0x26;
	invariant ADM_GET_LOYALTIES_TABLE		== 0x28;
	invariant ADM_DEL_LOYALTIES_TABLE		== 0x2A;
	invariant ADM_ADD_LOYALTIES_TABLE		== 0x2C;
	invariant ADM_GET_MAX_BALANCE			== 0x2E;
	invariant ADM_SET_MAX_BALANCE			== 0x30;
	invariant ADM_GET_MAX_DEBIT_WO_PIN		== 0x32;
	invariant ADM_SET_MAX_DEBIT_WO_PIN		== 0x34;
	invariant ADM_GET_MAX_TRANSACTION_WO_PIN	== 0x36;
	invariant ADM_GET_EXPIRATION_DATE		== 0x38;
	invariant ADM_SET_EXPIRATION_DATE		== 0x3A;
	invariant ADM_SET_DEBIT_KEY			== 0x3C;
	invariant ADM_SET_CREDIT_KEY			== 0x3E;
	invariant ADM_SET_PIN_KEY			== 0x40;
	invariant ADM_GET_STATE				== 0x48;
	invariant ADM_SET_CREDIT_AUTHORIZATION_KEY	== 0x4E;
	invariant ADM_SET_CREDIT_SIGNATURE_KEY		== 0x50;
	invariant ADM_SET_DEBIT_SIGNATURE_KEY		== 0x52;
	invariant ADM_SET_EXCHANGE_KEY			== 0x54;
	invariant ADM_SET_EXCHANGE_RATE_KEY		== 0x56;
	invariant ADM_GET_CONTROL_ACCESS_TABLE		== 0x58;
	invariant ADM_SET_CONTROL_ACCESS_TABLE		== 0x5A;
	invariant ADM_SET_ADMINISTRATIVE_KEY		== 0x5C;
	invariant ADM_SET_SYSTEM_KEY			== 0x5E;
	invariant ADM_SET_ADMINISTRATIVE_CODE		== 0x60; 
	invariant ADM_SET_MAX_TRANSACTION_WO_PIN	== 0x62;
	invariant ADM_ADD_CURRENCY_TABLE		== 0x64;
	invariant ADM_SET_ASN				== 0x66;
	invariant ADM_SET_STATE				== 0x68;
	invariant ADM_END_SESSION                       == 0x6A;
	invariant APP_INIT_DEBIT			== 0x02;
	invariant APP_INIT_CREDIT			== 0x04;
	invariant APP_INIT_EXCHANGE			== 0x06;
	invariant APP_INIT_VERIFY_PIN			== 0x08;    
	invariant APP_CREDIT				== 0x42;
	invariant APP_DEBIT				== 0x44;
	invariant APP_EXCHANGE_CURRENCY			== 0x46;
	invariant APP_GET_BALANCE			== 0x4A;
	invariant APP_VERIFY_PIN			== 0x4C; 
	
	
	invariant APPLET_STATE_ERR			== 0x9F01;
	invariant INCOMING_PARAM_SIZE_ERR		== 0x9F02;
	invariant OUTGOING_DATA_SIZE_ERR		== 0x9F03;
	invariant ACCESS_CONDITION_ERR			== 0x9F04;
	invariant TRANSACTION_COUNTER_OVERFLOW	== 0x9F05;
	invariant NOT_CURRENT_CURRENCY			== 0x9F06;
	invariant BAD_VALUE_FOR_AMOUNT			== 0x9F07;
	invariant BALANCE_OVERFLOW			== 0x9F08;     
	invariant CURRENCY_NOT_SUPPORTED		== 0x9F09;
	invariant BANK_CERTIFICATE_ERR			== 0x9F10;
	invariant WRONG_ADMIN_CMD_TYPE_ERR		== 0x9F11;
	invariant TOO_MUCH_TRANSACTION_WO_PIN	== 0x9F12;
	invariant LOYALTIES_TABLE_FULL		== 0x9F13;
	invariant SALERS_TABLE_FULL		== 0x9F14;
	invariant DECIMAL_OVERFLOW		== 0x9F15;	
	

	invariant PRIVATE_COMMAND_APDU	        == 0x80;
	invariant SM_COMMAND_APDU		== 0x0C;

	invariant SYSTEM_SESSION                == Security.SYSTEM_SESSION;
	invariant ADMINISTRATIVE_SESSION        == Security.ADMINISTRATIVE_SESSION;

	invariant purse != null ;
	invariant responseData != null ;
	invariant t1_4 != null ;
	invariant t2_4 != null ;
	invariant t3_4 != null ;
	invariant t4_4 != null ;
	invariant t5_8 != null ;
	invariant t6_16 != null ;
	invariant temp != null ;
	invariant decimal1 != null ;
	invariant date1 != null ;
	invariant heure1 != null ;
    */

	////////////////      CONSTANT ATTRIBUTES       ////////////////
    
    /************************************************************/
    /* administratives and personalisation command	identifiers */
    /************************************************************/
	public static final byte ADM_INIT_ADMINISTRATIVE_MODE		= (byte)0x0A;
	public static final byte ADM_VERIFY_ADM_PIN					= (byte)0x0C;
	public static final byte ADM_GET_ASN						= (byte)0x0E;
	public static final byte ADM_GET_BALANCE					= (byte)0x10;
	public static final byte ADM_GET_CREDIT_AMOUNT_WO_AUT		= (byte)0x12;
	public static final byte ADM_SET_CREDIT_AMOUNT_WO_AUT		= (byte)0x14;
	public static final byte ADM_GET_CURRENCY_TABLE				= (byte)0x16;
	public static final byte ADM_SET_CURRENCY_TABLE				= (byte)0x18;
	public static final byte ADM_DEL_CURRENCY_TABLE				= (byte)0x1A;
	public static final byte ADM_GET_TRANSACTION_RECORD			= (byte)0x1C;
	public static final byte ADM_GET_MAX_TRANSACTION_COUNTER	= (byte)0x1E;
	public static final byte ADM_SET_MAX_TRANSACTION_COUNTER	= (byte)0x20;
	public static final byte ADM_GET_EXCHANGE_RECORD			= (byte)0x22;
	public static final byte ADM_GET_CURRENCY					= (byte)0x24;
	public static final byte ADM_GET_EXCHANGE_RATE				= (byte)0x26;
	public static final byte ADM_GET_LOYALTIES_TABLE			= (byte)0x28;
	public static final byte ADM_DEL_LOYALTIES_TABLE			= (byte)0x2A;
	public static final byte ADM_ADD_LOYALTIES_TABLE			= (byte)0x2C;
	public static final byte ADM_GET_MAX_BALANCE				= (byte)0x2E;
	public static final byte ADM_SET_MAX_BALANCE				= (byte)0x30;
	public static final byte ADM_GET_MAX_DEBIT_WO_PIN			= (byte)0x32;
	public static final byte ADM_SET_MAX_DEBIT_WO_PIN			= (byte)0x34;
	public static final byte ADM_GET_MAX_TRANSACTION_WO_PIN		= (byte)0x36;
	public static final byte ADM_GET_EXPIRATION_DATE			= (byte)0x38;
	public static final byte ADM_SET_EXPIRATION_DATE			= (byte)0x3A;
	public static final byte ADM_SET_DEBIT_KEY					= (byte)0x3C;
	public static final byte ADM_SET_CREDIT_KEY					= (byte)0x3E;
	public static final byte ADM_SET_PIN_KEY					= (byte)0x40;
	public static final byte ADM_GET_STATE						= (byte)0x48;
	public static final byte ADM_SET_CREDIT_AUTHORIZATION_KEY	= (byte)0x4E;
	public static final byte ADM_SET_CREDIT_SIGNATURE_KEY		= (byte)0x50;
	public static final byte ADM_SET_DEBIT_SIGNATURE_KEY		= (byte)0x52;
	public static final byte ADM_SET_EXCHANGE_KEY				= (byte)0x54;
	public static final byte ADM_SET_EXCHANGE_RATE_KEY			= (byte)0x56;
	public static final byte ADM_GET_CONTROL_ACCESS_TABLE		= (byte)0x58;
	public static final byte ADM_SET_CONTROL_ACCESS_TABLE		= (byte)0x5A;
	public static final byte ADM_SET_ADMINISTRATIVE_KEY			= (byte)0x5C;
	public static final byte ADM_SET_SYSTEM_KEY					= (byte)0x5E;
	public static final byte ADM_SET_ADMINISTRATIVE_CODE		= (byte)0x60; 
	public static final byte ADM_SET_MAX_TRANSACTION_WO_PIN		= (byte)0x62;
	public static final byte ADM_ADD_CURRENCY_TABLE				= (byte)0x64;
	public static final byte ADM_SET_ASN						= (byte)0x66;
	public static final byte ADM_SET_STATE						= (byte)0x68;
	public static final byte ADM_END_SESSION                    = (byte)0x6A;

    // command removed by Rodolphe Muller on 12/07/2000
    //	public static final byte ADM_REGISTER						= (byte)0x70;


    /***********************************/
    /* applicative command identifiers */
    /***********************************/
	public static final byte APP_INIT_DEBIT						= (byte)0x02;
	public static final byte APP_INIT_CREDIT					= (byte)0x04;
	public static final byte APP_INIT_EXCHANGE					= (byte)0x06;
	public static final byte APP_INIT_VERIFY_PIN				= (byte)0x08;    
	public static final byte APP_CREDIT							= (byte)0x42;
	public static final byte APP_DEBIT							= (byte)0x44;
	public static final byte APP_EXCHANGE_CURRENCY				= (byte)0x46;
	public static final byte APP_GET_BALANCE					= (byte)0x4A;
	public static final byte APP_VERIFY_PIN						= (byte)0x4C; 
	
	
	/**********************************************/
    /* List of error codes returned by the applet */
    /**********************************************/
    
    /* the applet state can't be initialised  or changed because its present 
       state is invalide */
	public static final short APPLET_STATE_ERR				= (short)0x9F01;
	
    /* The size of imput data is not correct */
	public static final short INCOMING_PARAM_SIZE_ERR		= (short)0x9F02;
	
	/* The size of exit data is not correct */
	public static final short OUTGOING_DATA_SIZE_ERR		= (short)0x9F03;
	
	/* Error launched at the access condition verification */
	public static final short ACCESS_CONDITION_ERR			= (short)0x9F04;
	
	/* Transaction counter has reached a maximum value */
	public static final short TRANSACTION_COUNTER_OVERFLOW	= (short)0x9F05;
	
	/* The currency used is not the purse currency */
	public static final short NOT_CURRENT_CURRENCY			= (short)0x9F06;
	
	/* Error that correspond to a bad value of the amount, for example a debit 
	   with a amount that is nil, negative or superior to the purse balance */
	public static final short BAD_VALUE_FOR_AMOUNT			= (short)0x9F07;
	
	/* Purse credit attempt causes an balance overflow */
	public static final short BALANCE_OVERFLOW				= (short)0x9F08;     
	
	/* Error caused by the attemp of a devise download to a currency nor supported 
	   by the purse */
	public static final short CURRENCY_NOT_SUPPORTED		= (short)0x9F09;
	
	/* The exchange rate certificate is false */
	public static final short BANK_CERTIFICATE_ERR			= (short)0x9F10;
	
	/* The administrative command type is bad */
	public static final short WRONG_ADMIN_CMD_TYPE_ERR		= (short)0x9F11;
	
	/* Error caused by too many transaction without authentiation */
	public static final short TOO_MUCH_TRANSACTION_WO_PIN	= (short)0x9F12;
	
	/* Loyalty table is full */
	public static final short LOYALTIES_TABLE_FULL			= (short)0x9F13;
	
    /* Saler table is full */
	public static final short SALERS_TABLE_FULL				= (short)0x9F14;

    /* Decimal number is too large */
	public static final short DECIMAL_OVERFLOW		= (short)0x9F15;	
	
	
    /*****************************/
    /* Constants for APDU format */
    /*****************************/
	public static final byte PRIVATE_COMMAND_APDU	= (byte)0x80;
	public static final byte SM_COMMAND_APDU		= (byte)0x0C;


    /************************************************************************/
	/* Constants of the Purse class repeated in the PurseApplet class so that
	   the client coudl acess to them. The client application can't acess to 
	   the Purse clas that reprsent the intern behaviour reprsentation of the 
	   applet. The client appication can only acess to the PurseApplet clas. 
	*/
	/************************************************************************/
	
	public static final byte SYSTEM_SESSION = Security.SYSTEM_SESSION;
	public static final byte ADMINISTRATIVE_SESSION = Security.ADMINISTRATIVE_SESSION;

	////////////////      ATTRIBUTES       ////////////////
		
	/* Purse descriptor that have the methods defining the applet behaviour */
    private /*@spec_public */ Purse purse;

	/* Temporary table used to format response */
    private /*@ spec_public */ byte responseData[] = 
	        JCSystem.makeTransientByteArray((short)70, JCSystem.CLEAR_ON_RESET);

	/* Temporary tables */
	private /*@ spec_public */ byte t1_4[] =
		JCSystem.makeTransientByteArray((short)4, JCSystem.CLEAR_ON_DESELECT);
    private /*@ spec_public */ byte t2_4[] =
		JCSystem.makeTransientByteArray((short)4, JCSystem.CLEAR_ON_DESELECT);
    private /*@ spec_public */ byte t3_4[] =
		JCSystem.makeTransientByteArray((short)4, JCSystem.CLEAR_ON_DESELECT);
    private /*@ spec_public */ byte t4_4[] =
		JCSystem.makeTransientByteArray((short)4, JCSystem.CLEAR_ON_DESELECT);
    private /*@ spec_public */ byte t5_8[] =
		JCSystem.makeTransientByteArray((short)8, JCSystem.CLEAR_ON_DESELECT);
    private /*@ spec_public */ byte t6_16[] =
		JCSystem.makeTransientByteArray((short)16, JCSystem.CLEAR_ON_DESELECT);
    private /*@ spec_public */ byte temp[] =
		JCSystem.makeTransientByteArray((short)60, JCSystem.CLEAR_ON_DESELECT);

	/* Temporary objects */
	private /*@ spec_public */ Decimal decimal1 = new Decimal();
	private /*@ spec_public */ Date date1 = new Date();
	private /*@ spec_public */ Heure heure1 = new Heure();

	// apdu recu par la méthode process
	// initialisée par "void process(APDU)" et utlisé
	// par les "xxxxxxResp(...,byte [])"    
// removed by Rodolphe Muller on 05/05/2000
// for the same reasons that the other applets; we cannot store
// APDU object.
//    private APDU apdu = null;

// added by Rodolphe Muller on 05/05/2000
	//private byte[] ivNull = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
	
    
	///////////////     CONSTRUCTOR     ////////////////

	/**
	 *	@exception AppletStateException Erreur lors de l'initialisation du
	 *	comportement ( <code> PacapBehavior </code> )
	 */
     // removed by Rodolphe Muller on 12/07/2000
    /*
	protected PurseApplet(byte[] aid, short off, byte len)
	throws AppletStateException {
		super();
		decimal1 = new Decimal();X
		date1 = new Date();X
		heure1 = new Heure();X
		purse = new Purse(this, aid, off, len);
	}
    */

	
	////////////////       METHODS      ///////////////


    /*@
      requires true ;
      ensures true ;
      exsures (TransactionException e) 
                   e._reason == TransactionException.BUFFER_FULL
                   && JCSystem._transactionDepth == 1;
      exsures (SystemException e) e._reason == SystemException.ILLEGAL_AID;
      exsures (NullPointerException) false; 
      exsures (ArrayIndexOutOfBoundsException) false; 
    */
	public static void install(byte[] bArray, short bOffset, byte bLength)
	    throws ISOException,
		   SystemException,
		   NullPointerException,          // MH, not in original source
		   ArrayIndexOutOfBoundsException,// MH, not in original source
		   TransactionException           // MH, not in original source
		// instantiation and registration to the JCRE
        
        // modified by Rodolphe Muller on 12/07/2000
        /*
		try {
			PurseApplet instance = new PurseApplet(bArray, bOffset, bLength);
        } catch(AppletStateException ase) {
			ISOException.throwIt(APPLET_STATE_ERR);
		}
		instance.register(bArray,bOffset,bLength);
        */
        {
	    PurseApplet instance = new PurseApplet();
	    instance.register();
	}


/*	@return the result of the selection : TRUE if sucess*/
    //code modifies by Nestor CATANO
    //inclusion of throws clause
    /*@
      also_modifies purse.securite.terminalSessionType ;
      also_modifies purse.securite.accessCondition.condition ;
      also_modifies purse.securite.administrativeCode.flags, 
               purse.securite.administrativeCode.flags[OwnerPIN.VALIDATED],
               purse.securite.administrativeCode.triesLeft[0] ;
      also_modifies purse.securite.authPin ;
      also_ensures purse.securite.terminalSessionType == Security.NOT_INITIALIZED ;
      also_ensures purse.securite.accessCondition.condition == AccessCondition.FREE ;
      also_ensures purse.securite.administrativeCode.flags != null ;
      also_ensures purse.securite.authPin == false ;
      also_exsures (SystemException e) e._reason == SystemException.NO_TRANSIENT_SPACE;
     */
    public boolean select()
	throws SystemException {
    // added by Rodolphe Muller
    // the instantiation of a Purse object is too big for the current GemXpresso    
    // transaction buffer so we put it here.
		if(purse == null) {
			purse = new Purse(this);
		}
		return purse.select();
	}
    
    
/*	Action to do when the applet is not selecioned */
    //code modified by Nestor CATANO
    //inclusion of throws clause
    /*@
      also_modifies purse.securite.terminalSessionType ;
      also_modifies purse.securite.administrativeCode.flags, 
               purse.securite.administrativeCode.flags[OwnerPIN.VALIDATED],
               purse.securite.administrativeCode.triesLeft[0] ;
      also_ensures purse.securite.terminalSessionType == Security.NOT_INITIALIZED ; 
      also_ensures purse.securite.administrativeCode.flags != null ; 
      also_exsures (SystemException e) e._reason == SystemException.NO_TRANSIENT_SPACE;
    */
	public void deselect()
	    throws SystemException {
		purse.deselect();
		return;
	}


/*  method invoked by the JCRE so that the applet treat the APDU*/
    /*@
      //also_requires true ;
      //also_ensures true ;
    */
    public void process(APDU apdu) throws ISOException {
	// removed by Rodolphe Muller on 05/05/2000
	//	this.apdu = apdu;
	switch(apdu.getBuffer()[ISO7816.OFFSET_INS]) {
	case APP_INIT_DEBIT:
	    appInitDebit(apdu);
	    break;
	case APP_INIT_CREDIT:
	    appInitCredit(apdu);
	    break;
            // re-added by LC 23/11/00
	case APP_INIT_EXCHANGE:
	    appInitExchange(apdu);
	    break;
	case APP_INIT_VERIFY_PIN:
	    appInitVerifyPin(apdu);
	    break;
	case APP_VERIFY_PIN:
	    appVerifyPin(apdu);
	    break;
	case APP_CREDIT:
	    appCredit(apdu);
	    break;
	case APP_DEBIT:
	    appDebit(apdu);
	    break;
	    // re-added by LC 23/11/00
	case APP_EXCHANGE_CURRENCY:
	    appExchangeCurrency(apdu);
	    break;
	case APP_GET_BALANCE:
	    appGetBalance(apdu);
	    break;
	case ADM_INIT_ADMINISTRATIVE_MODE:
	    admInitAdministrativeMode(apdu);
	    break;
	case ADM_VERIFY_ADM_PIN:
	    admVerifyAdmPin(apdu);
	    break;
	case ADM_END_SESSION:
	    admEndSession(apdu);
	    break;
	case ADM_GET_ASN:
	    admGetAsn(apdu);
	    break;
	case ADM_SET_ASN:
	    admSetAsn(apdu);
	    break;
	case ADM_GET_BALANCE:
	    admGetBalance(apdu);
	    break;
            // re-added by LC 23/11/00
	case ADM_GET_CREDIT_AMOUNT_WO_AUT:
	    admGetCreditAmountWOAut(apdu);
	    break;
	    // re-added by LC 23/11/00
	case ADM_SET_CREDIT_AMOUNT_WO_AUT:
	    admSetCreditAmountWOAut(apdu);
	    break;
			// re-added by LC 23/11/00
	case ADM_GET_CURRENCY_TABLE:
	    admGetCurrencyTable(apdu);
	    break;
	    // re-added by LC 23/11/00
	case ADM_SET_CURRENCY_TABLE:
	    admSetCurrencyTable(apdu);
	    break;
	    // re-added by LC 23/11/00
	case ADM_ADD_CURRENCY_TABLE:
	    admAddCurrencyTable(apdu);
	    break;
	    // re-added by LC 23/11/00
	case ADM_DEL_CURRENCY_TABLE:
	    admDelCurrencyTable(apdu);
	    break;
	    // re-added by LC 23/11/00
	case ADM_GET_TRANSACTION_RECORD:
	    admGetTransactionRecord(apdu);
	    break;
            // re-added by LC 23/11/00
	case ADM_GET_MAX_TRANSACTION_COUNTER:
	    admGetMaxTransactionCounter(apdu);
	    break;
	    // re-added by LC 23/11/00
	case ADM_SET_MAX_TRANSACTION_COUNTER:
	    admSetMaxTransactionCounter(apdu);
	    break;
	    // re-added by LC 23/11/00
	case ADM_GET_EXCHANGE_RECORD:
	    admGetExchangeRecord(apdu);
	    break;
	    // re-added by LC 23/11/00	
	case ADM_GET_CURRENCY:
	    admGetCurrency(apdu);
	    break;
	    // re-added by LC 23/11/00
	case ADM_GET_EXCHANGE_RATE:
	    admGetExchangeRate(apdu);
	    break;
	    // re-added by LC 23/11/00
	case ADM_GET_LOYALTIES_TABLE:
	    admGetLoyaltiesTable(apdu);
	    break;
	    // re-added by LC 23/11/00
	case ADM_DEL_LOYALTIES_TABLE :
	    admDelLoyaltiesTable(apdu);
	    break;
	    // re-added by LC 23/11/00
	case ADM_ADD_LOYALTIES_TABLE :
	    admAddLoyaltiesTable(apdu);
            break;
            // re-added by LC 23/11/00
	case ADM_GET_MAX_BALANCE :
	    admGetMaxBalance(apdu);
            break;
            // re-added by LC 23/11/00
	case ADM_SET_MAX_BALANCE :
	    admSetMaxBalance(apdu);
            break;
            // re-added by LC 23/11/00
	case ADM_GET_MAX_DEBIT_WO_PIN :
	    admGetMaxDebitWOPIN(apdu);
            break;
            // re-added by LC 23/11/00
	case ADM_SET_MAX_DEBIT_WO_PIN :
	    admSetMaxDebitWOPIN(apdu);
            break;
            // re-added by LC 23/11/00
	case ADM_GET_MAX_TRANSACTION_WO_PIN :
	    admGetMaxTransactionWOPIN(apdu);
            break;
            // re-added by LC 23/11/00
	case ADM_SET_MAX_TRANSACTION_WO_PIN :
	    admSetMaxTransactionWOPIN(apdu);
            break;
	case ADM_GET_EXPIRATION_DATE :
	    admGetExpirationdate(apdu);
            break;
	case ADM_SET_EXPIRATION_DATE:
	    admSetExpirationDate(apdu);
	    break;
	case ADM_SET_DEBIT_KEY:
	    admSetDebitKey(apdu);
	    break;
	case ADM_SET_CREDIT_KEY:
	    admSetCreditKey(apdu);
	    break;
	case ADM_SET_PIN_KEY:
	    admSetPINKey(apdu);
	    break;   
	case ADM_GET_STATE:
	    admGetState(apdu);
	    break;
	case ADM_SET_STATE:
	    admSetState(apdu);
	    break;                
	case ADM_SET_CREDIT_AUTHORIZATION_KEY :
	    admSetCreditAuthorizationKey(apdu);
            break;
	case ADM_SET_CREDIT_SIGNATURE_KEY :
	    admSetCreditSignatureKey(apdu);
            break;
	case ADM_SET_DEBIT_SIGNATURE_KEY :
	    admSetDebitSignatureKey(apdu);
            break;
            // re-added by LC 23/11/00
	case ADM_SET_EXCHANGE_KEY:
	    admSetExchangeKey(apdu);
	    break;
	    // re-added by LC 23/11/00
	case ADM_SET_EXCHANGE_RATE_KEY :
	    admSetExchangeRateKey(apdu);
            break;
            // re-added by LC 23/11/00
	case ADM_GET_CONTROL_ACCESS_TABLE : 
	    admGetControlAccessTable(apdu);
            break;
            // re-added by LC 23/11/00
	case ADM_SET_CONTROL_ACCESS_TABLE : 
	    admSetControlAccessTable(apdu);
            break;
	case ISO7816.INS_SELECT:
	    break;   
	case ADM_SET_ADMINISTRATIVE_KEY:
	    admSetAdministrativeKey(apdu);
	    break;
	case ADM_SET_SYSTEM_KEY:
	    admSetSystemKey(apdu);
	    break;
	case ADM_SET_ADMINISTRATIVE_CODE :
	    admSetAdministrativeCode(apdu);
            break;
            // command removed by Rodolphe Muller on 12/07/2000
            /*
	      case ADM_REGISTER :
	      admRegister(apdu);
	      break;                
            */
	default:
	    ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
	    break;
	}
    } 



/*	called by the JCRE when an applet call an shareable object
 *	@param client aid of the client applet
 *	@param type byte that specify the shared object
 *	@return the shareable object*/     
    /*@
      //modifies \nothing ;
      also_ensures true ;
      //exsures (Exception) false ;
    */   
    public Shareable getShareableInterfaceObject(AID client, byte type) {
	// we do treatment in the choice of sharing or not
	return purse.getShareableInterfaceObject(client, type);
    }


    /*************************************************************/
    /* Private methods that construct data to send them to purse */
    /*************************************************************/

/*  initialisation of the debit session
 *	imput data :
 *	the debit amount                        : 4 bytes
 *	the currency                            : 1 byte
 *	the saler identifier                    : 4 bytes
 *	the product identifier                  : 2 bytes
 *	the terminal random                     : 4 bytes
 *	the terminal transaction counter        : 4 bytes
 *	the terminal serial number              : 4 bytes	 */
    /*@
      modifies purse.securite.State ;
      modifies purse.securite.temp[*], purse.securite.SMKey ;
      modifies TransactionException.systemInstance._reason, 
               JCSystem._transactionDepth ;
      modifies purse.transactionWOPIN  ;
      requires true ;
      ensures true ;
      exsures (ISOException e ) true;
      exsures (TransactionException e) 
                   e._reason == TransactionException.BUFFER_FULL 
                   && JCSystem._transactionDepth == 1 ; 
     */
    private void appInitDebit(APDU apdu) 
	throws ISOException,TransactionException, AccessConditionException {
	byte[] buffer = apdu.getBuffer();
	
	// verification of the imput data size
	verifyAPDU(buffer, (byte)23);
	
	short readCount = apdu.setIncomingAndReceive();
	short offset = (short)ISO7816.OFFSET_CDATA;
	
        // Decimal exception added 14/11/00
	try{
	    offset = decimal1.setValue(buffer, offset);
	}
	catch(DecimalException e){
            ISOException.throwIt(DECIMAL_OVERFLOW);			
	}
	byte monnaie = buffer[offset++];
	offset += Util.arrayCopyNonAtomic(buffer, offset, t1_4, (short)0, (short)4);
	short typeProd = Util.getShort(buffer, offset);
	offset += 2;
        offset += Util.arrayCopyNonAtomic(buffer, offset, t2_4, (short)0, (short)4);
        offset += Util.arrayCopyNonAtomic(buffer, offset, t3_4, (short)0, (short)4);
        offset += Util.arrayCopyNonAtomic(buffer, offset, t4_4, (short)0, (short)4);
	purse.appInitDebit(decimal1, monnaie, t1_4, typeProd, t2_4, t3_4, t4_4, apdu);
    }

/*  initialisation of the credit session
 *	imput data :
 *	
 *	the debit amount                        : 4 bytes
 *	the currency                            : 1 byte
 *	the credit operator identifier          : 4 bytes
 *	the terminal random                     : 4 bytes
 *	the terminal transaction counter        : 4 bytes
 *	the terminal serial number              : 4 bytes*/
    /*@
      requires true ;
      ensures true ;
    */
	private void appInitCredit(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		
		// verification of the imput data size
		verifyAPDU(buffer, (byte)21);
		
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		
		// Decimal exception added 14/11/00
		try{
		    offset = decimal1.setValue(buffer, offset); 				
		}
		catch(DecimalException e){
            ISOException.throwIt(DECIMAL_OVERFLOW);			
		}
		byte monnaie = buffer[offset++];
		offset += Util.arrayCopyNonAtomic(buffer, offset, t1_4, (short)0, (short)4);
		offset += Util.arrayCopyNonAtomic(buffer, offset, t2_4, (short)0, (short)4);
		offset += Util.arrayCopyNonAtomic(buffer, offset, t3_4, (short)0, (short)4);
		offset += Util.arrayCopyNonAtomic(buffer, offset, t4_4, (short)0, (short)4);
		purse.appInitCredit(decimal1, monnaie, t1_4, t2_4, t3_4, t4_4, apdu);
	}

/*  initialisation of the exchange currency session
 *	imput data :
 *	the present purse currency              : 1 byte
 *	the destinantion currency               : 1 byte
 *	the change operator identifier          : 4 bytes
 *	the terminal random                     : 4 bytes
 *	the terminal transaction counter        : 4 bytes
 *	the terminal serial number              : 4 bytes*/
   // re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
	private void appInitExchange(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
	    
	    // verification of the imput data size
		verifyAPDU(buffer, (byte)18);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;

		byte monnaieCourante = buffer[offset++];
		byte nouvelleMonnaie = buffer[offset++];
		offset += Util.arrayCopyNonAtomic(buffer, offset, t1_4, (short)0, (short)4);
		offset += Util.arrayCopyNonAtomic(buffer, offset, t2_4, (short)0, (short)4);
		offset += Util.arrayCopyNonAtomic(buffer, offset, t3_4, (short)0, (short)4);
		offset += Util.arrayCopyNonAtomic(buffer, offset, t4_4, (short)0, (short)4);
		purse.appInitExchange(
			monnaieCourante, nouvelleMonnaie, t1_4, t2_4, t3_4, t4_4, apdu
		);
    }



	/**
	 * The pin code verification is secure
	 *	La verification du pin est sécurisée.
	 *	imput data :	
	 *	the terminal random                  : 4 octets
	 *	the terminal serial number           : 4 octets
	 */
    /*@
      // not checked completely, because ESC/Java times out (> 300 s)
      requires true ;
      ensures true ;
    */
	private void appInitVerifyPin(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		
		// verification of the imput data size
		verifyAPDU(buffer, (byte)8);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;

		offset += Util.arrayCopyNonAtomic(buffer, offset, t1_4, (short)0, (short)4);
		offset += Util.arrayCopyNonAtomic(buffer, offset, t2_4, (short)0, (short)4);
		purse.appInitVerifyPin(t1_4, t2_4, apdu);
	}

/*	input data :	
 *	the terminal random number      : 2 bytes*/
    /*@
      // not checked completely, because ESC/Java times out (> 300 s)
      requires true ;
      ensures true ;
    */
	private void appVerifyPin(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)8);
		// CardManager.GLOBAL_PIN_SIZE = 8
		short readCount = apdu.setIncomingAndReceive();
		purse.appVerifyPin(apdu);
	}

/*	Read the balance of the purse*/
    /*@
      // not checked completely, because ESC/Java times out (> 300 s)
      requires true ;
      ensures true ;
    */
	private void appGetBalance(APDU apdu){
		purse.appGetBalance(apdu);
	}

/*	confirmation of a debit command 
 *	input data :
 *	date : 3 bytes :  1 byte for the day
 *                    1 byte for the month
 *                    1 byte for the year
 *	hour : 2 bytes
 *	signature used at the mutual authentiation : 8 bytes */
    /*@
      // not checked completely, because ESC/Java times out (> 300 s)
      requires true ;
      ensures true ;
    */
	private void appDebit(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)13);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		try {
			byte jj = buffer[offset++];
			byte mm = buffer[offset++];
			byte aa = buffer[offset++];
			date1.setDate(jj,mm,aa);
			byte hh = buffer[offset++];
			byte mn = buffer[offset++];
			heure1.setHeure(hh,mn);
			Util.arrayCopyNonAtomic(buffer, offset, t5_8, (short)0, (short)8);
			purse.appDebit(date1, heure1, t5_8, apdu);
		} catch(DateException de) {
			ISOException.throwIt(ISO7816.SW_DATA_INVALID);
		} catch(HeureException he) {
			ISOException.throwIt(ISO7816.SW_DATA_INVALID);
		}
	}


/*	confirmation of a credit command 
	 *	input data :
	 *	date : 3 bytes :  1 byte for the day
	 *                    1 byte for the month
	 *                    1 byte for the year
	 *	hour : 2 bytes
	 *  bank certificate : 8 bytes
	 *	signature used at the mutual authentiation : 8 bytes */
    /*@
      // not checked completely, because ESC/Java times out (> 300 s)
      requires true ;
      ensures true ;
    */
	private void appCredit(APDU apdu){
		byte [] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)21);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		try {
			byte jj = buffer[offset++];
			byte mm = buffer[offset++];
			byte aa = buffer[offset++];
			date1.setDate(jj, mm, aa);
			byte hh = buffer[offset++];
			byte mn = buffer[offset++];
			heure1.setHeure(hh, mn);
			offset += Util.arrayCopyNonAtomic(buffer, offset, temp, (short)0, (short)8);
			offset += Util.arrayCopyNonAtomic(buffer, offset, t5_8, (short)0, (short)8);
			purse.appCredit(date1, heure1, temp, t5_8, apdu);
		} catch(DateException de) {
			ISOException.throwIt(ISO7816.SW_DATA_INVALID);
		} catch(HeureException he) {
			ISOException.throwIt(ISO7816.SW_DATA_INVALID);
		}
	}


/*	confirmation of a currency change that does after an "initExchange" method
 *	input data :
 *	date : 3 bytes :  1 byte for the day
 *                    1 byte for the month
 *                    1 byte for the year
 *	hour : 2 bytes
 *  certificate of purse provider : 23 bytes
 *      operation type : 1 bytes
 *      exchange rate (2 short of a Decimal) : 2*2 bytes
 *      the inverse exchange rate : 2*2 bytes
 *      the serial purse number : 4 bytes 
 *      transaction counter (short) : 2 bytes 
 *      data previous the certificate signature : 8 bytes 
 *      signature (terminal authentiation) : 8 bytes*/
// re-added by LC 23/11/00
    /*@
      // not checked completely, because ESC/Java times out (> 300 s)
      requires true ;
      ensures true ;
    */
	private void appExchangeCurrency(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)36);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		try {
			byte jj = buffer[offset++];
			byte mm = buffer[offset++];
			byte aa = buffer[offset++];
			date1.setDate(jj, mm, aa);
			byte hh = buffer[offset++];
			byte mn = buffer[offset++];
			heure1.setHeure(hh, mn);
			offset += Util.arrayCopyNonAtomic(
				buffer, offset, temp, (short)0, (short)23
			);
			offset += Util.arrayCopyNonAtomic(buffer, offset, t5_8, (short)0, (short)8);
			purse.appExchangeCurrency(date1, heure1, temp, t5_8, apdu);
		} catch(DateException de) {
			ISOException.throwIt(ISO7816.SW_DATA_INVALID);
		} catch(HeureException he) {
			ISOException.throwIt(ISO7816.SW_DATA_INVALID);
		}
	}


/*	initialisation of an administrative session
 *	input data :
 *  serial teminal number : 4 bytes
 *  terminal administrative session counter : 4 bytes
 *  terminal random : 4 bytes
 *  session type : 1 bytes */
    /*@
      requires true ;
      ensures true ;
    */
	private void admInitAdministrativeMode(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)13);
		short readCount = apdu.setIncomingAndReceive();

		Util.arrayCopyNonAtomic(buffer, ISO7816.OFFSET_CDATA, t1_4, (short)0, (short)4);
		Util.arrayCopyNonAtomic(
			buffer, (short)(ISO7816.OFFSET_CDATA + 4), t3_4,(short)0, (short)4
		);

		Util.arrayCopyNonAtomic(
			buffer, (short)(ISO7816.OFFSET_CDATA + 8), t2_4, (short)0, (short)4
		);
		byte type = buffer[ISO7816.OFFSET_CDATA + 12];
		try {
			purse.admInitAdministrativeMode(t1_4, t3_4, t2_4, type, apdu);
		} catch(PurseException pe) {
			ISOException.throwIt(WRONG_ADMIN_CMD_TYPE_ERR);
		}
	}

/* input data :      
 * pin to verify : 2 bytes */
    /*@
      // not checked completely, because ESC/Java times out (> 300 s)
      requires true ;
      ensures true ;
    */
	private void admVerifyAdmPin(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)2);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		short pin  = Util.getShort(buffer, offset);
		purse.admVerifyAdmPin(pin, apdu);
	}
	
    /*@
      requires true ;
      ensures true ;
    */
	private void admEndSession(APDU apdu){
	    purse.admEndSession(apdu);
	}
	

/* Read the applet serial number*/
    /*@
      requires true ;
      ensures true ;
    */
	private void admGetAsn(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)0);
		short offset = (short)ISO7816.OFFSET_CDATA;
		purse.admGetAsn(apdu);
	}

/*	modify the applet serial number
 *  input data
 *	SN : 4 bytes	 */
    /*@
      requires true ;
      ensures true ;
    */
	private void admSetAsn(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)4);
		short readCount = apdu.setIncomingAndReceive();
		Util.arrayCopyNonAtomic(buffer, ISO7816.OFFSET_CDATA, t1_4, (short)0, (short)4);
		purse.admSetAsn(t1_4, apdu);
	}

/*	modify the purse applet state
 *  input data
 *	the new state : 1 bytes*/
    /*@
      requires true ;
      ensures true ;
    */
	private void admSetState(APDU apdu) {
		byte [] buffer = apdu.getBuffer();
		verifyAPDU(buffer,(byte)1);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		byte etat = buffer[offset++];
		purse.admSetState(etat, apdu);
	}


/*	read the applet serial number*/
    /*@
      requires true ;
      ensures true ;
    */
	private void admGetBalance(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)0);
  
		short offset = (short)ISO7816.OFFSET_CDATA;
		purse.admGetBalance(apdu);
	}


/*read the maximal credit whitout bank autorisation */
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
private void admGetCreditAmountWOAut(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)0);
		short offset = (short)ISO7816.OFFSET_CDATA;
		purse.admGetCreditAmountWOAut(apdu);
	}


/*  Modify the maximal credit witout autorisation of the bank
 *  input data : 
 *  amount maximal : 2*2 bytes*/
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
    private void admSetCreditAmountWOAut(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)4);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		try{
		    offset = decimal1.setValue(buffer, offset);
		}
		catch(DecimalException de){
		    ISOException.throwIt(PurseApplet.DECIMAL_OVERFLOW);
		}
		purse.admSetCreditAmountWOAut(decimal1, apdu);
    }

/*  read the supported currencies.
 *  input data:
 *  table enter number : 1 byte*/
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */	 
	private void admGetCurrencyTable(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)1);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		byte index = buffer[offset++];
		purse.admGetCurrencyTable(index, apdu);
	}

    
/*  modify the devise supported
 *  input data:
 *  enter index : 1 byte
 *  supported currency : 1 byte*/
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
    private void admSetCurrencyTable(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)2);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		byte index = buffer[offset++];
		byte devise = buffer[offset++];
		purse.admSetCurrencyTable(index, devise, apdu);
	}


/*  add the supported devise
 *  input data:
 *  supported devise :  1 byte*/
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */	 
    /*@
      requires true ;
      ensures true ;
    */
 private void admAddCurrencyTable(APDU apdu){
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)1);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		byte devise = buffer[offset++];
		purse.admAddCurrencyTable(devise, apdu);
	}
 

	
/*  delete the supported currency
 *  input data : 
 *  entry table number : 1 byte */
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
private void admDelCurrencyTable(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)1);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		byte index = buffer[offset++];
		purse.admDelCurrencyTable(index, apdu);
	}


/* read the transcation saved in the historic 
 * input data: 
 * index in the table of historic : 1 byte */
    /*@
      requires true ;
      ensures true ;
    */
	private void admGetTransactionRecord(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
    // modified by RM on 28/08/2000
    //		verifyAPDU(buffer, (byte)1);
		verifyAPDU(buffer, (byte)2);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
    // modified by RM on 28/08/2000
    //		byte index = buffer[offset++];
		short index = Util.getShort(buffer, offset);
		purse.admGetTransactionRecord(index, apdu);
	}


/* read the number of maximum transaction*/
// re-added by LC 23/11/00  
    /*@
      requires true ;
      ensures true ;
    */
	private void admGetMaxTransactionCounter(APDU apdu) {
		byte [] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)0);
		short offset = (short)ISO7816.OFFSET_CDATA;
		purse.admGetMaxTransactionCounter(apdu);
	}



/*  modify the maximum number of transaction to do by  the purse
 *  inpût data:
 *	number maximum of transaction : 2 bytes	 */
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
private void admSetMaxTransactionCounter(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)2);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		short maxTransaction = (short)0;
		maxTransaction = Util.getShort(buffer, offset);
		purse.admSetMaxTransactionCounter(maxTransaction, apdu);
	}


/*  read a session of change of a rate saved in historic file 
 *  input data:
 *	file index : 1 byte*/
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */	 
 private void admGetExchangeRecord(APDU apdu) {
		byte [] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)1);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		byte index = buffer[offset++];
		purse.admGetExchangeRecord(index, apdu);
	}
 


/* read the present currency */
  // re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
	private void admGetCurrency(APDU apdu) {
		byte [] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)0);
    // removed by Rodolphe Muller on 10/07/2000
    //		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		byte index = buffer[offset++];
		purse.admGetCurrency(apdu);
	}
  


/*  read the exchange rate */
//re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
    private void admGetExchangeRate(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)0);
		short offset = (short)ISO7816.OFFSET_CDATA;
		byte index = buffer[offset++];
		purse.admGetExchangeRate(apdu);
	}


/*  read the entry in the loyalty table
 *  input data : 
 *  index in the table : 1 byte*/
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
	private void admGetLoyaltiesTable(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)1);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		byte index = buffer[offset++];
		purse.admGetLoyaltiesTable(index, apdu);
	}

/* delete the entry in the loyalty table
 *  entry data :
 *  index in the table : 1 byte*/ 
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
	private void admDelLoyaltiesTable(APDU apdu) {
		byte [] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)1);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		byte index = buffer[offset++];
		purse.admDelLoyaltiesTable(index, apdu);
	}


/*  Add a loyalty to the table of loyalties that are allowed to dialog with the purse
 *  entry data :
 *  loyalty aid : 16 bytes
 *  subscription boolean : 1 byte
 *  saler list : 5*4 bytes*/
 // re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
	private void admAddLoyaltiesTable(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)37);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		offset += Util.arrayCopyNonAtomic(buffer, offset, t6_16, (short)0, (short)16);
		boolean bool = buffer[offset++] == (byte)1;
		offset += Util.arrayCopyNonAtomic(buffer, offset, temp, (short)0, (short)20);
		purse.admAddLoyaltiesTable(t6_16, bool, temp, apdu);
	}
 

/*  read the maximal balance of the purse*/
//re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
	private void admGetMaxBalance(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)0);
		short offset = (short)ISO7816.OFFSET_CDATA;
		purse.admGetMaxBalance(apdu);
	}


/* modify the maximal balance 
	 * entry data : 
	 * maximal balance : 2*2 bytes*/
 // re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
	private void admSetMaxBalance(APDU apdu) {
		byte [] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)4);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		try{
		    decimal1.setValue(buffer, offset);
		}
		catch(DecimalException e){
		    ISOException.throwIt(PurseApplet.DECIMAL_OVERFLOW);	
		}
		purse.admSetMaxBalance(decimal1, apdu);
	}
 

/*  read the maximal debit without pin code presentation*/
// re-added by LC 23/11/00
	private void admGetMaxDebitWOPIN(APDU apdu) {
		byte [] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)0);
		short offset = (short)ISO7816.OFFSET_CDATA;
		purse.admGetMaxDebitWOPIN(apdu);
	}
 

/*  read the maximal debit without pin code presentation*/
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
	private void admSetMaxDebitWOPIN(APDU apdu) {
	    byte [] buffer = apdu.getBuffer();
	    verifyAPDU(buffer, (byte)4);
	    short readCount = apdu.setIncomingAndReceive();
	    short offset = (short)ISO7816.OFFSET_CDATA;
	    try{
		decimal1.setValue(buffer, offset);
	    }
	    catch(DecimalException e){
		ISOException.throwIt(PurseApplet.DECIMAL_OVERFLOW);			
	    }
	    purse.admSetMaxDebitWOPIN(decimal1, apdu);
	}


/*  read the maximal transaction number that are can be done without 
 *  pin code presentation*/
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
    private void admGetMaxTransactionWOPIN(APDU apdu){
        byte [] buffer = apdu.getBuffer();
        short readCount = apdu.setIncomingAndReceive();
        short offset = (short) ISO7816.OFFSET_CDATA;
        purse.admGetMaxTransactionWOPIN(apdu);    
    }

    
/*  read the maximal transaction number that are can be done without 
 *  pin code presentation
 *  entry data : 1 byte */
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
	private void admSetMaxTransactionWOPIN(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)1);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		byte n = buffer[offset++];
		purse.admSetMaxTransactionWOPIN(n, apdu);
	}
  

/* read the expiration date of the purse*/
    /*@
      requires true ;
      ensures true ;
    */
	private void admGetExpirationdate(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)0);
		short offset = (short)ISO7816.OFFSET_CDATA;
		purse.admGetExpirationDate(apdu);
	}
    
/* modify the expiration date of the purse
 *  entry data :
 *  epiration date : 3*1 bytes */
    /*@
      requires true ;
      ensures true ;
    */
	private void admSetExpirationDate(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)3);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA; 
		byte jj = buffer[offset++]; 
		byte mm = buffer[offset++];
		byte aa = buffer[offset++];
		try {
			date1.setDate(jj, mm, aa);
			purse.admSetExpirationDate(date1, apdu);
		} catch(DateException de) {
			ISOException.throwIt(ISO7816.SW_DATA_INVALID);
		}
	}

/*  modify the mother key of debit session key
 *  entry data : 
 *  mother key : 16 bytes*/
    /*@
      requires true ;
      ensures true ;
    */
	private void admSetDebitKey(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)16);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short) ISO7816.OFFSET_CDATA; 
		offset = Util.arrayCopyNonAtomic(
			buffer, offset, temp, (short)0, (short)16
		);
		purse.admSetDebitKey(temp, apdu);
	}


 /*  modify the mother key of credit session
  * entry data : 16 bytes */
    /*@
      requires true ;
      ensures true ;
    */
	private void admSetCreditKey(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)16);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		offset = Util.arrayCopyNonAtomic(
			buffer, offset, temp, (short)0, (short)16
		);
		purse.admSetCreditKey(temp, apdu);
	}
    
/* modify the mother key of verification session pin code
   entry data :
   mother key : 16 bytes*/
    /*@
      requires true ;
      ensures true ;
    */
	private void admSetPINKey(APDU apdu) {
		byte [] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)16);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		offset = Util.arrayCopyNonAtomic(
			buffer, offset, temp, (short)0, (short)16
		);
		purse.admSetPINKey(temp, apdu);
	}

/*  read the purse applet state	 */
    /*@
      requires true ;
      ensures true ;
    */
	private void admGetState(APDU apdu){
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)0);
		short offset = (short)ISO7816.OFFSET_CDATA; 
		purse.admGetState(apdu);
	}

/* set the key used to authenticate a bank authorization*/
    /*@
      requires true ;
      ensures true ;
    */
    private void  admSetCreditAuthorizationKey(APDU apdu){
        byte [] buffer = apdu.getBuffer();
        verifyAPDU(buffer, (byte) 16);
        short readCount = apdu.setIncomingAndReceive();
        short offset = (short) ISO7816.OFFSET_CDATA; 
        offset = Util.arrayCopyNonAtomic(buffer, offset, temp, (short) 0,
                                         (short) 16);
        purse.admSetCreditAuthorizationKey(temp, apdu);
    }
    
/* set the key used to generate a credit certificate*/
    /*@
      requires true ;
      ensures true ;
    */
    private void admSetCreditSignatureKey(APDU apdu){
        byte [] buffer = apdu.getBuffer();
        verifyAPDU(buffer, (byte) 16        );
        short readCount = apdu.setIncomingAndReceive();
        short offset = (short) ISO7816.OFFSET_CDATA; 
        offset = Util.arrayCopyNonAtomic(buffer, offset, temp, (short) 0,
                                         (short) 16);
        purse.admSetCreditSignatureKey(temp, apdu);
    }
    
/* set the key used to generate a debit certificate*/ 
    /*@
      requires true ;
      ensures true ;
    */   
    private void admSetDebitSignatureKey(APDU apdu){
        byte [] buffer = apdu.getBuffer();
        verifyAPDU(buffer, (byte) 16        );
        short readCount = apdu.setIncomingAndReceive();
        short offset = (short) ISO7816.OFFSET_CDATA; 
        offset = Util.arrayCopyNonAtomic(buffer, offset, temp, (short) 0,
                                         (short) 16);
        purse.admSetDebitSignatureKey(temp, apdu);
    }

/* set the cryptographic key for exchange session*/
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
	private void  admSetExchangeKey(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)16);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short) ISO7816.OFFSET_CDATA; 
		offset = Util.arrayCopyNonAtomic(
			buffer, offset,
			temp, (short)0,
			(short)16
		);
		purse.admSetExchangeKey(temp, apdu);
	}

/*set the cryptographic key that authenticates the bank exchange rate*/
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
	private void admSetExchangeRateKey(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)16);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		offset = Util.arrayCopyNonAtomic(buffer, offset, temp, (short)0, (short)16);
		purse.admSetExchangeRateKey(temp, apdu);
	}
  

    
	
/*read the access condition of a command
  entry data :
  command identifier : 1 byte*/
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
    private void admGetControlAccessTable(APDU apdu){
        byte [] buffer = apdu.getBuffer();
	    verifyAPDU(buffer,(byte) 1);
	    short readCount = apdu.setIncomingAndReceive();
	    byte idCmd = buffer[ISO7816.OFFSET_CDATA];
	    purse.admGetControlAccesTable(idCmd, apdu);
    }    

    
/*entry data :
  command identifier : 1 byte
  access condition : 1 byte*/
// re-added by LC 23/11/00
    /*@
      requires true ;
      ensures true ;
    */
    private void admSetControlAccessTable(APDU apdu){
        byte [] buffer = apdu.getBuffer();
	    verifyAPDU(buffer,(byte) 2);
	    short readCount = apdu.setIncomingAndReceive();
	    byte idCmd = buffer[ISO7816.OFFSET_CDATA];
	    byte ac = buffer[(byte) (ISO7816.OFFSET_CDATA + 1)];
	    purse.admSetControlAccesTable(idCmd, ac, apdu);
    }
    

    /*@
      requires true ;
      ensures true ;
    */
	private void admSetAdministrativeKey(APDU apdu){
		byte[] buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)16);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		offset = Util.arrayCopyNonAtomic(
			buffer, offset, temp, (short)0, (short)16
		);
		purse.admSetAdministrativeKey(temp, apdu);
	}

    /*@
      requires true ;
      ensures true ;
    */
	private void admSetSystemKey(APDU apdu) {
		byte []buffer = apdu.getBuffer();
		verifyAPDU(buffer, (byte)16);
		short readCount = apdu.setIncomingAndReceive();
		short offset = (short)ISO7816.OFFSET_CDATA;
		offset = Util.arrayCopyNonAtomic(buffer, offset, temp, (short)0, (short)16);
		purse.admSetSystemKey(temp, apdu);
	}

	/**
	 *  entry data :
	 *  new code administrator : 2 byte
	 */
    /*@
      requires true ;
      ensures true ;
    */
    private void admSetAdministrativeCode(APDU apdu) {
	    byte [] buffer = apdu.getBuffer();
	    verifyAPDU(buffer,(byte) 2);
	    short readCount = apdu.setIncomingAndReceive();
	    short newCode = Util.getShort(buffer,ISO7816.OFFSET_CDATA);
	    purse.admSetAdministrativeCode(newCode, apdu);
    }
    
    // command removed by Rodolphe Muller on 12/07/2000
    /*
    private void admRegister(APDU apdu){
        byte [] buffer = apdu.getBuffer();
    // removed by Rodolphe Muller on 10/07/2000
    //		verifyAPDU(buffer,(byte) 0);
	    short readCount = apdu.setIncomingAndReceive();	    
	    purse.admRegister(apdu);
    }
    */
    
    
	/*****************************************/
	/*  Methods "package" to format reponses */
    /*****************************************/
    
    // command removed by Rodolphe Muller on 12/07/2000
    /*
	void admRegisterResp(PacapKey SMKey, APDU apdu) {
		short offset = (short)0;
		addNeededSMAndSend(offset,SMKey, apdu);
	}
    */

	/**
	 * initiDebit command response : Pute the flowing data in the buffer apdu
	 *	purse provider identifier : 5 bytes
	 *  purse applet serial number : 4 bytes
	 *  day, month, year of purse expiration : 3*1 bytes
	 *  purse transaction counter : 2 bytes
	 *  purse session random : 4 bytes
	 *  derivation key algorithm : 1 byte
	 *  signature alogorithm : 1 byte
	 *  mutual or not autentification : 1 byte
	 *  previous data signature : 8 bytes
	 * 
	 *	@param PPID provider ientifier : 5 bytes
	 *	@param asn purse applet serial number  :  4 bytes
	 *	@param expDate purse expiration date
	 *	@param atc applat transaction counter
	 *	@param randA applet random : 4 bytes
	 *	@param algoDC derivation key algorithm. It is a constant chosen betewen
	 *         constants of class javacardx.crypto.Cypher or javacard.secutity.Signature
	 *	@param algoS signature algoritm
	 *	@param  mutual autentification or not
	 *	@param sig previous data signature : 8 bytes
	 */
    /*@
      requires true ;
      ensures true ;
    */
	void appInitDebitResp(
		byte[] PPID, byte[] asn, Date expDate, short atc, byte[] randA, byte algoDC,
		byte algoS, byte authentification, byte[] sig, APDU apdu
	) {
		short offset = (short)0;
		offset = Util.arrayCopyNonAtomic(
			PPID, (short)0, responseData, offset, (short)5
		);
		offset = Util.arrayCopyNonAtomic(asn, (short)0, responseData, offset, (short)4);
		responseData[offset++] = expDate.getJour();
		responseData[offset++] = expDate.getMois();
		responseData[offset++] = expDate.getAnnee();
		offset = Util.setShort(responseData, offset, atc);
		offset = Util.arrayCopyNonAtomic(
			randA, (short)0, responseData, offset, (short)4
		);
		responseData[offset++] = algoDC;
		responseData[offset++] = algoS;
		responseData[offset++] = authentification;
		offset = Util.arrayCopyNonAtomic(sig, (short)0, responseData, offset, (short)8);
		short le = apdu.setOutgoing();
		if(le != 0 && le < offset)
			ISOException.throwIt(OUTGOING_DATA_SIZE_ERR);
		apdu.setOutgoingLength(offset);
		apdu.sendBytesLong(responseData, (short)0, offset);
	}


	/**
	 * initiCredit command response : Pute the flowing data in the buffer apdu
	 *	purse provider identifier : 5 bytes
	 *  purse applet serial number : 4 bytes
	 *  day, month, year of purse expiration : 3*1 bytes
	 *  purse transaction counter : 2 bytes
	 *  purse session random : 4 bytes
	 *  derivation key algorithm : 1 byte
	 *  signature alogorithm : 1 byte
	 *  mutual or not autentification : 1 byte
	 *  previous data signature : 8 bytes
	 * 
	 *	@param PPID provider ientifier : 5 bytes
	 *	@param asn purse applet serial number  :  4 bytes
	 *	@param expDate purse expiration date
	 *	@param atc applat transaction counter
	 *	@param randA applet random : 4 bytes
	 *	@param algoDC derivation key algorithm. It is a constant chosen betewen
	 *         constants of class javacardx.crypto.Cypher or javacard.secutity.Signature
	 *	@param algoS signature algoritm
	 *	@param authen banck autorisation needed
	 *	@param sig previous data signature : 8 bytes
	 */
    /*@
      requires true ;
      ensures true ;
    */
	void appInitCreditResp(
		byte[] PPID, byte[] asn, Date expDate, short atc, byte[] randA, byte algoDC,
		byte algoS, byte authen, byte[] sig, APDU apdu
	) {
		short offset = (short)0;
		offset = Util.arrayCopyNonAtomic(
			PPID, (short)0, responseData, offset, (short)5
		);
		offset = Util.arrayCopyNonAtomic(asn, (short)0, responseData, offset, (short)4);
		responseData[offset++] = expDate.getJour();
		responseData[offset++] = expDate.getMois();
		responseData[offset++] = expDate.getAnnee();
		offset = Util.setShort(responseData, offset, atc);
		offset = Util.arrayCopyNonAtomic(
			randA, (short)0, responseData, offset, (short)4
		);
		responseData[offset++] = algoDC;
		responseData[offset++] = algoS;
		responseData[offset++] = authen;
		offset = Util.arrayCopyNonAtomic(sig, (short)0, responseData, offset, (short)8);
		short le = apdu.setOutgoing();
		if((le != 0) && (le < offset))
			ISOException.throwIt(OUTGOING_DATA_SIZE_ERR);
		apdu.setOutgoingLength(offset);
		apdu.sendBytesLong(responseData, (short)0, offset);
	}

	 
    /**
	 *  appInitExchange command response : Pute the flowing data in the buffer apdu
	 *	purse provider identifier : 5 bytes
	 *  purse applet serial number : 4 bytes
	 *  day, month, year of purse expiration : 3*1 bytes
	 *  purse transaction counter : 2 bytes
	 *  purse session random : 4 bytes
	 *  derivation key algorithm : 1 byte
	 *  signature alogorithm : 1 byte
	 * 
	 *	@param PPID provider ientifier : 5 bytes
	 *	@param asn purse applet serial number  :  4 bytes
	 *	@param expDate purse expiration date
	 *	@param atc applat transaction counter
	 *	@param randA applet random : 4 bytes
	 *	@param algoDC derivation key algorithm. It is a constant chosen betewen
	 *         constants of class javacardx.crypto.Cypher or javacard.secutity.Signature
	 *	@param algoS signature algoritm
	 */
    //re-added by LC 22/11/00
    /*@
      requires true ;
      ensures true ;
    */
	void appInitExchangeResp(
		byte[] PPID, byte[] asn, Date expDate, short atc, byte[] randA, byte algoDC,
		byte algoS , APDU apdu
	) {
		short offset = (short)0;
		offset = Util.arrayCopyNonAtomic(
			PPID, (short)0, responseData, offset, (short)5
		);
		offset = Util.arrayCopyNonAtomic(asn, (short)0, responseData, offset, (short)4);
		responseData[offset++] = expDate.getJour();
		responseData[offset++] = expDate.getMois();
		responseData[offset++] = expDate.getAnnee();
		offset = Util.setShort(responseData,offset,atc);
		offset = Util.arrayCopyNonAtomic(
			randA, (short)0, responseData, offset, (short)4
		);
		responseData[offset++] = algoDC;
		responseData[offset++] = algoS;                      
		short le = apdu.setOutgoing();
		if(le != 0 && le < offset)
			ISOException.throwIt(OUTGOING_DATA_SIZE_ERR);
		apdu.setOutgoingLength(offset);		
		apdu.sendBytesLong(responseData, (short)0, offset);
	}
   


	/*returned data
	 *	the new balance         :   2*2 bytes
	 *	the debit certificate   :   8 bytes
	 *	@param balance: the new purse balance
	 *	@param certificat: the certificate that authenticates the debit transacation*/
    /*@
      requires true ;
      ensures true ;
    */
	void appDebitResp(Decimal balance, byte[] certificat,APDU apdu) {
		short offset = (short)0;
		offset = balance.getValue(responseData, offset);
		offset = Util.arrayCopyNonAtomic(
			certificat, (short)0, responseData, offset, (short)8
		);
		addNeededSMAndSend(offset, apdu);
	}


	/*	returned data
	 *	the new balance              :   2*2 bytes
	 *	credit certificate      :   8 bytes
	 *
	 *	@param the new purse balance
	 *	@param the certificate that authenticates the transaction for the bank*/
    /*@
      requires true ;
      ensures true ;
    */
	void appCreditResp(Decimal balance, byte[] certificat, APDU apdu) {
	    short offset = (short) 0;
	    offset = balance.getValue(responseData,offset);
	    offset = Util.arrayCopyNonAtomic(
			certificat, (short)0, responseData, offset,(short)8
		);
		addNeededSMAndSend(offset, apdu);
	}


	/**
	 *	returned data
	 *	2*2 bytes    : the new balance (in the new currency)
	 *	1 byte       : the new currency
	 *	8 bytes      : the purse signature for authentication
	 *
	 *	@param b: the purse balance
	 *	@param c : the current currency
	 *	@param sign: the signature that authenticates the terminal
	 */
// re-added by LC 22/11/00

    /*@
      requires true ;
      ensures true ;
    */
	void appExchangeCurrencyResp(
		Decimal b, byte c, byte[] sign,  APDU apdu
	) {
		short offset = (short)0;
		offset = b.getValue(responseData, offset);
		responseData[offset++] = c;
		offset = Util.arrayCopyNonAtomic(
			sign, (short)0, responseData, offset, (short)8
		);
		addNeededSMAndSend(offset, apdu);
	}

	
	/*	returned data
	 *	4 bytes: the purse serial number
	 *	4 bytes: the purse random
	 *  1 byte: the key generation algorithm
	 *  1 byte: the algorithm signature
	 *
	 *	@param asn       : the applet serial number
	 *	@param randA     : the applet random
	 *	@param algoDS    : the key generation algorithm
	 *	@param algoS     : the algorithm signature
	 */
    /*@
      requires true ;
      ensures true ;
    */
	void appInitVerifyPinResp(
		byte[] asn, byte[] randA, byte algoDS, byte algoS, APDU apdu
	) {
		short offset = (short)0;
		offset = Util.arrayCopyNonAtomic(asn, (short)0, responseData, offset, (short)4);
		offset = Util.arrayCopyNonAtomic(
			randA, (short)0, responseData, offset, (short)4
		);
		responseData[offset++] = algoDS;
		responseData[offset++] = algoS;
		short le = apdu.setOutgoing();
		if(le != 0 && le < offset)
			ISOException.throwIt(OUTGOING_DATA_SIZE_ERR);
		apdu.setOutgoingLength(offset);
		apdu.sendBytesLong(responseData, (short)0, offset);
	}

/*return the result of the PIN verification	 */
    /*@
      requires true ;
      ensures true ;
    */
    void appVerifyPinResp(byte resu, APDU apdu) {
		short offset = (short)0;
		responseData[offset++] = resu;
		addNeededSMAndSend(offset, apdu);
	}

	/**
	 return the following session data
	 *	purse serial number                          : 4 bytes
	 *	adlinistrative session counter               : 2 bytes
	 *	purse random                                 : 4 bytes
	 *	cryptographic algorithm used to generate session keys    : 1 byte
	 *	algorithm used to generate signature         : 1 byte
	 *	authentication algorithm                     : 8 bytes
	 */
    /*@
      requires true ;
      ensures true ;
    */
	void admInitAdministrativeModeResp(
		byte[] asn, short atc, byte[] randA, byte algoDC, byte algoDS, byte[] sig,
		APDU apdu
	) {
		short offset = (short)0;

		offset = Util.arrayCopyNonAtomic(asn, (short)0, responseData, offset, (short)4);
		offset = Util.setShort(responseData, offset, atc);
		offset = Util.arrayCopyNonAtomic(
			randA, (short)0, responseData, offset, (short)4
		);
		responseData[offset++] = algoDC;
		responseData[offset++] = algoDS;
		offset = Util.arrayCopyNonAtomic(sig, (short)0, responseData, offset, (short)8);

		short le = apdu.setOutgoing();
		if(le != 0 && le < offset)
			ISOException.throwIt(OUTGOING_DATA_SIZE_ERR);
		apdu.setOutgoingLength(offset);		
    	apdu.sendBytesLong(responseData, (short)0, offset);
	}

/*return the balance amount. returned value:
  the balance amount             : 2*2 bytes,
  the current currency           : 1 byte*/
    /*@
      requires true ;
      ensures true ;
    */
    void appGetBalanceResp(Decimal b, byte devise, APDU apdu){
        short offset = (short) 0;
        offset  = b.getValue(responseData, offset);
        responseData[offset++] = devise;
        addNeededSMAndSend(offset, apdu);
    }

/*	return the result of the administrative code verification
	0x01: the code is correct, 0x00 otherwise : 1 byte
	number of remaining tries before being blocked : 1 byte*/
    /*@
      requires true ;
      ensures true ;
    */
	void admVerifyAdmPinResp(
		boolean resp, byte triesRemaining, APDU apdu
	) {
		short offset = ( short)0;
		responseData[offset++] = (resp ? (byte)0x01 : (byte)0x00);
		responseData[offset++] = triesRemaining;
		addNeededSMAndSend(offset, apdu);
	}

    /*@
      requires true ;
      ensures true ;
    */

void admEndSessionResp(APDU apdu){
    short offset = (short)0;
	addNeededSMAndSend(offset,  apdu);
}

/*return the applet serial number: 4 bytes*/
    /*@
      requires true ;
      ensures true ;
    */
	void admGetAsnResp(byte[] asn, APDU apdu) {
		short offset = (short)0;
		offset = Util.arrayCopyNonAtomic(asn, (short)0, responseData, offset, (short)4);
		addNeededSMAndSend(offset, apdu);
	}

    /* return the purse balance. Returned data:
     * the balance amount             : 2*2 bytes,
     * the current currency           : 1 byte.*/
    /*@
      requires true ;
      ensures true ;
    */
    void admGetBalanceResp(Decimal b, byte devise, APDU apdu){
        short offset = (short) 0;
        offset  = b.getValue(responseData, offset);
        responseData[offset++] = devise;
        addNeededSMAndSend(offset, apdu);
    }


/*	return the value of the credit without a bank authorization
 *	the value             : 2*2 bytes,
 *	the currency          : 1 byte
 */
// readded by LC 21/11/00
    /*@
      requires true ;
      ensures true ;
    */
	void admGetCreditAmountWOAutResp(Decimal d, byte devise, APDU apdu)
	{
		short offset = (short)0;
		offset  = d.getValue(responseData, offset);
		responseData[offset++] = devise;
		addNeededSMAndSend(offset, apdu);
	}


/*This command allows to set a new value for the maximum credit without a bank authorization*/
// readded by LC 21/11/00
    /*@
      requires true ;
      ensures true ;
    */
	void admSetCreditAmountWOAutResp( APDU apdu) {
		short offset = (short)0;
		addNeededSMAndSend(offset,  apdu);
	}


/* read an entry in the currency table. Return:
	 *	the currency                                : 1 byte
	 *	the number of remaining entries in the table: 1 byte
	 */
// re-added by LC 22/11/00
    /*@
      requires true ;
      ensures true ;
    */
	void admGetCurrencyTableResp(byte d, byte r, APDU apdu) {
		short offset = (short)0;
		responseData[offset++] = d;
		responseData[offset++] = r;
		addNeededSMAndSend(offset,  apdu);
	}


/* set an entry in the currency table*/
// re-added by LC 22/11/00
    /*@
      requires true ;
      ensures true ;
    */
	void admSetCurrencyTableResp( APDU apdu) {
		short offset = (short)0;
		addNeededSMAndSend(offset, apdu);	                
	}


/* add an entry in the currency table*/
// re-added by LC 22/11/00
    /*@
      requires true ;
      ensures true ;
    */
	void admAddCurrencyTableResp(APDU apdu) {
		short offset = (short)0;
		addNeededSMAndSend(offset,  apdu);
	}


/* suppress an entry in the currency table*/
// re-added by LC 22/11/00
    /*@
      requires true ;
      ensures true ;
    */
	void admDelCurrencyTableResp(APDU apdu) {
		short offset = (short)0;
		addNeededSMAndSend(offset, apdu);
	}

	/**
	 * returned data of a transaction : 
	 * number of the transaction                    : 2 bytes
	 * terminal serial number                       : 4 bytes
	 * terminal transaction counter                 : 4 bytes
	 * transaction type                             : 1 byte
	 * partners id                                  : 4 bytes
     * product type                                 : 2 bytes
     * the used currency                            : 1 byte
     * exchange rate between the reference currency 
     * and the trransaction currency                : 2*2 bytes
     * transaction amount                           : 2*2 bytes
     * transaction date                             : 3*1 bytes
     * transaction time                             : 2*1 bytes
     * bank certificate                             : 16 bytes
     * mutual authentication required? (boolean)    : 1 byte
     * bank authorization request? (boolean)        : 1 byte
     * transaction sent to the loyalty              : 1 byte
     * transaction status                           : 1 byte
     * transaction validity                         : 1 byte
     * number of remaining transactions             : 1 byte
     */
    /*@
      requires true ;
      ensures true ;
    */
	void admGetTransactionRecordResp(
		short number, byte[] terminalSN, byte[] terminalTC, byte type, byte[] partnerID,
		short typeProduit, byte devise, Decimal taux, Decimal montant, Date date,
		Heure heure, byte[] certificat, boolean mutualAuthentification,
		boolean bankAuthorization, boolean isGiven, byte status, boolean isValid,
		byte reste, APDU apdu
	) {
		short offset = (short)0;
		offset = Util.setShort(responseData, offset, number);
		offset = Util.arrayCopyNonAtomic(
			terminalSN, (short)0, responseData, offset, (short)4
		);
		offset = Util.arrayCopyNonAtomic(
			terminalTC, (short)0, responseData, offset, (short)4
		);
		responseData[offset++] = type;
		offset = Util.arrayCopyNonAtomic(
			partnerID, (short)0, responseData, offset, Transaction.PARTNER_ID_LENGTH
		);
		offset = Util.setShort(responseData, offset, typeProduit);
		responseData[offset++] = devise;
		offset = taux.getValue(responseData, offset);
		offset = montant.getValue(responseData, offset);
		offset = date.getDate(responseData, offset);
		offset = heure.getHeure(responseData, offset);
		offset = Util.arrayCopyNonAtomic(
			certificat, (short)0, responseData, offset, Transaction.CERTIFICATE_LENGTH
		);
		// boolean are coded over a byte
		// 1 <=> VRAI et 1 <=> FAUX
		responseData[offset++] = mutualAuthentification ? (byte)1 : (byte)0;
		responseData[offset++] = bankAuthorization ? (byte)1 : (byte)0;
		responseData[offset++] = isGiven ? (byte)1 : (byte)0; 
		responseData[offset++] = status;
		responseData[offset++] = isValid ? (byte)1 : (byte)0;
		responseData[offset++] = reste;
		addNeededSMAndSend(offset, apdu);
	}

	/** send to the terminal the number of the maximum transaction allowed in the purse*/
// re-added by LC 21/11/00

    /*@
      requires true ;
      ensures true ;
    */
	void admGetMaxTransactionCounterResp(short mt, APDU apdu) {
		short offset =  (short)0;
		offset = Util.setShort(responseData, offset, mt);
		addNeededSMAndSend(offset, apdu);
	}

/** modify the maximum number of transaction allowed in the purse */
// re-added by LC 21/11/00

    /*@
      requires true ;
      ensures true ;
    */
	void admSetMaxTransactionCounterResp( APDU apdu) {
		short offset =  (short)0;
		addNeededSMAndSend(offset, apdu);
	}


 /* response of the reading of an entry from te table containing all the exchange performed by
    the purse.
     the returned values are
     *	the number of the session            : 2 bytes
     *	The date                             : 3*1 bytes
     *	The time                             : 2*1 bytes
     *	the id of the change operator        :  4 bytes
     *	the old currency                     : 1 byte
     *	the new currency                     : 1 byte
     *	status of the transaction            : 1 byte
     *	validity of the transaction          : 1 byte
     *	number of remaining transactions     : 1 byte
     */
// re-added by LC 22/11/00
    /*@
      requires true ;
      ensures true ;
    */
	void admGetExchangeRecordResp(
		short atc, Date d, Heure h, byte[] id, byte ancDev, byte nouvDev, byte status,
		boolean isValid, byte reste,APDU apdu
	) {
		short offset = (short)0;
		offset = Util.setShort(responseData, offset, atc);
		offset = d.getDate(responseData, offset);
		offset = h.getHeure(responseData, offset);
		offset = Util.arrayCopyNonAtomic(
			id, (short)0, responseData, offset, ExchangeSession.ID_LENGTH
		);
		responseData[offset++] = ancDev;
		responseData[offset++] = nouvDev;
		responseData[offset++] = status;
		responseData[offset++] = isValid ? (byte)1 : (byte)0;
		responseData[offset++] = reste;
		addNeededSMAndSend(offset,  apdu);
	}


 /* return the current currency: 1 byte*/
// re-added by LC 21/11/00
    /*@
      requires true ;
      ensures true ;
    */
    void admGetCurrencyResp(byte dev, APDU apdu){
        short offset = (short) 0;
        responseData[offset++] = dev;
        addNeededSMAndSend(offset, apdu);        
    }

/* return the current exchange rate between the reference currency and the current currency
    2*2 bytes*/
	//re-added by LC 21/11/00
    /*@
      requires true ;
      ensures true ;
    */
	void admGetExchangeRateResp(Decimal er, APDU apdu) {
		short offset = (short)0;
		offset = er.getValue(responseData, offset);
		addNeededSMAndSend(offset, apdu);
	}

/*response to the terminal concerning an entry of the loyalty table
	returned data:
	 *	loyalty aid                                     : 16 bytes
	 *	a boolean set to true if the loyalty subscribes to the logfull service  : 1 byte
	 *	the list of the associated saler                 : 5*4 bytes
	 *	Number of remaining loyaltis                     : 1 byte
	 */
// re-added by LC 22/11/00
    /*@
      requires true ;
      ensures true ;
    */
	void admGetLoyaltiesTableResp(
		byte[] aid, boolean b, byte[] bArray, short len, byte reste, 
		APDU apdu
	) {
		short offset = (short)0;
		offset = Util.arrayCopyNonAtomic(
			aid, (short)0, responseData, offset, (short)16
		);
		responseData[offset++] = b ? (byte)1 : (byte)0;
		offset = Util.arrayCopyNonAtomic(bArray, (short)0, responseData, offset, len);
		responseData[offset++] = reste;
		addNeededSMAndSend(offset,  apdu);
	}

/* the response for suppressing an entry in the loyalty table*/
// re-added by LC 22/11/00

    /*@
      requires true ;
      ensures true ;
    */
    void admDelLoyaltiesTableResp(APDU apdu){
        short offset = (short) 0;
        addNeededSMAndSend(offset, apdu);           
    }

/* the response for adding an entry in the loyalty table*/
// re-added by LC 22/11/00

    /*@
      requires true ;
      ensures true ;
    */
	void admAddLoyaltiesTableResp( APDU apdu) {
		short offset = (short)0;
		addNeededSMAndSend(offset, apdu);
	}

/* the response for reading the max balance value.
	 return : 2*2 bytes for the max balance*/
// re-added by LC 22/11/00

    /*@
      requires true ;
      ensures true ;
    */
	void admGetMaxBalanceResp(Decimal d,  APDU apdu) {
		short offset = (short)0;
		offset = d.getValue(responseData, offset);
		addNeededSMAndSend(offset,  apdu);
	}


/* response for the modification of the max balance value*/
// re-added by LC 22/11/00
    /*@
      requires true ;
      ensures true ;
    */
    void admSetMaxBalanceResp(APDU apdu){
        short offset = (short) 0;     
        addNeededSMAndSend(offset, apdu); 
    }


/*response for the reading of the max debit whitout Pin value
  return 2*2 bytes for the max debit without pin */
// re-added by LC 22/11/00

    /*@
      requires true ;
      ensures true ;
    */
    void admGetMaxDebitWOPINResp(Decimal d, APDU apdu){
        short offset = (short) 0;     
        offset = d.getValue(responseData, offset);
        addNeededSMAndSend(offset,apdu); 
    }

/* response for the modification of the max debit without pin value*/
    /*@
      requires true ;
      ensures true ;
    */
    void admSetMaxDebitWOPINResp( APDU apdu){
        short offset = (short) 0;     
        addNeededSMAndSend(offset, apdu); 
    }


/* response for the reading of the maximum number of transaction allowed without re-entering the
    pin code.
  return this value: 1 byte*/
// re-added by LC 22/11/00
    /*@
      requires true ;
      ensures true ;
    */
    void admGetMaxTransactionWOPINResp(byte n,   APDU apdu){
        short offset = (short) 0;     
        responseData[offset++] = n;
        addNeededSMAndSend(offset,apdu); 
    }
    
/* response for the modification of the maximum number of transaction*/
// re-added by LC 22/11/00
    /*@
      requires true ;
      ensures true ;
    */
    void admSetMaxTransactionWOPINResp( APDU apdu){
        short offset = (short) 0;     
        addNeededSMAndSend(offset, apdu); 
    }


/*	read the expiration date of the purse
 *	returned data: the expiration date: 3*1 bytes*/
    /*@
      requires true ;
      ensures true ;
    */
	void admGetExpirationdateResp(Date d, APDU apdu) {
		short offset = (short)0;
		offset = d.getDate(responseData, offset);
		addNeededSMAndSend(offset, apdu);
	}

/* set the expiration date*/
    /*@
      requires true ;
      ensures true ;
    */
	void admSetExpirationdateResp(APDU apdu) {
		short offset = (short)0;
		addNeededSMAndSend(offset, apdu); 
	}

    /*@
      requires true ;
      ensures true ;
    */
	void admSetDebitKeyResp(APDU apdu) {
		short offset = (short)0;
		addNeededSMAndSend(offset, apdu);
	}

    /*@
      requires true ;
      ensures true ;
    */
	void admSetCreditKeyResp( APDU apdu){
		short offset = (short)0;
		addNeededSMAndSend(offset, apdu); 
	}

    /*@
      requires true ;
      ensures true ;
    */
	void admSetPINKeyResp(APDU apdu) {
		short offset = (short)0;
		addNeededSMAndSend(offset,  apdu); 
	}
	
/*return the state of the applet*/
    /*@
      requires true ;
      ensures true ;
    */
	void admGetStateResp(byte state, APDU apdu) {
		responseData[(short)0] = state;
		addNeededSMAndSend((short)1,  apdu);
	}

    /*@
      requires true ;
      ensures true ;
    */
    void admSetCreditSignatureKeyResp(APDU apdu){
        short offset = (short) 0;     
        addNeededSMAndSend(offset, apdu); 
    }

    /*@
      requires true ;
      ensures true ;
    */
    void admSetDebitSignatureKeyResp(APDU apdu){
        short offset = (short) 0;     
        addNeededSMAndSend(offset, apdu); 
    }

// re-added by LC 22/11/00
    /*@
      requires true ;
      ensures true ;
    */
	void admSetExchangeKeyResp(APDU apdu) {
		short offset = (short)0;
		addNeededSMAndSend(offset, apdu);
	}

// re-added by LC 22/11/00
    /*@
      requires true ;
      ensures true ;
    */
	void admSetExchangeKeyRateResp(APDU apdu) {
		short offset = (short)0;
		addNeededSMAndSend(offset, apdu);
	}


    /*@
      requires true ;
      ensures true ;
    */
	void admSetAdministrativeKeyResp(APDU apdu) {
		short offset = (short)0;     
		addNeededSMAndSend(offset,  apdu);
	}

    /*@
      requires true ;
      ensures true ;
    */
	void admSetSystemKeyResp(APDU apdu) {
		short offset = (short)0;
		addNeededSMAndSend(offset, apdu);
	}

    /*@
      requires true ;
      ensures true ;
    */
    void admSetCreditAuthorizationKeyResp(APDU apdu){
        short offset = (short) 0;     
        addNeededSMAndSend(offset, apdu); 
    }

    /*@
      requires true ;
      ensures true ;
    */
    void admSetAdministrativeCodeResp(APDU apdu){
        short offset = (short) 0;     
        addNeededSMAndSend(offset, apdu); 
    }

//re-added by LC 22/11/00
    /*@
      requires true ;
      ensures true ;
    */
    void admGetControlAccessTableResp(AccessControl ac,APDU apdu) 
    {
	    short offset = (short) 0;
	    responseData[offset++] = ac.getCondition();
	    addNeededSMAndSend(offset, apdu);
    }
// re-added by LC 22/11/00
    /*@
      requires true ;
      ensures true ;
    */
    void admSetControlAccessTableResp(APDU apdu) {
	    short offset = (short) 0;
	    addNeededSMAndSend(offset, apdu);
    }

    /*@
      requires true ;
      ensures true ;
    */
	void admSetAsnResp(APDU apdu) {
		short offset = (short)0;
		addNeededSMAndSend(offset,  apdu);
	}
	
    /*@
      requires true ;
      ensures true ;
    */
	void admSetStateResp(APDU apdu) {
		short offset = (short)0;       
		addNeededSMAndSend(offset,  apdu);
	}

	//----------------------------------------------------------
	//
	//                private methods of the purseApplet class
	//
	//----------------------------------------------------------

	/*check that the apdu buffer has the right size and add the secure messaging if necessary
	 *  @param buffer the byte table received
	 *  @param nb : the number of expected bytes
	 *  @exception ISOException an exception is thrown if the size is not correct
	 *  the returned status is a constant from the CardIssuerApplet class*/
    /*@
      requires true ;
      ensures true ;
    */
    private void verifyAPDU(byte[] buffer, byte nb) {
	// is it a specific command for the applet
	if((buffer[ISO7816.OFFSET_CLA] & PRIVATE_COMMAND_APDU) == PRIVATE_COMMAND_APDU)
	    {
		// check the size of the arguments
		// if there is secure messaging, it requires 8 bytes more
		byte taille = nb;
		if((buffer[ISO7816.OFFSET_CLA] & SM_COMMAND_APDU) == SM_COMMAND_APDU) {
		    taille += (byte)8;
		}
		byte bytesLeft = buffer[ISO7816.OFFSET_P1];
		if(bytesLeft != taille)
		    ISOException.throwIt(INCOMING_PARAM_SIZE_ERR);
	    } else
		ISOException.throwIt(ISO7816.SW_FUNC_NOT_SUPPORTED);
    }


	/* generate the secure messaging*/
    /*@
      requires src != null && dest != null;
      requires dOff >= 0;
      ensures true ;
      exsures (CryptoException e) true;
    */
	private short generateSecureMessaging(
		byte[] src, short off, short len, byte[] dest, short dOff
	) 
	    throws CryptoException {
	    /* now the secure messaging is performed in the security class*/
		short size= purse.securite.generateSecureMessaging(src,off,len,dest,dOff);
		return (short)(dOff + size);
	}

	/*add the secure messaging if necessary and send the apdu*/
    /*@
      requires true ;
      ensures true ;
    */
	private void addNeededSMAndSend(short offset, APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		// is secure messaging mandatory
		if((buffer[ISO7816.OFFSET_CLA] & SM_COMMAND_APDU) == SM_COMMAND_APDU) {
			// generate the secure messaging
			short offset_ = (short)0;
			temp[offset_++] = buffer[ISO7816.OFFSET_INS];
			offset_ = Util.arrayCopyNonAtomic(
				buffer, (short)ISO7816.OFFSET_CDATA,
				temp, offset_,
				(short)(buffer[ISO7816.OFFSET_P1] - (byte)8)
			);
			offset_ = Util.arrayCopyNonAtomic(
				responseData, (short)0, temp, offset_, offset
			);
			offset = generateSecureMessaging(
				temp, (short)0, offset_, responseData, offset
			);
		}

		short le = apdu.setOutgoing();
		// verification of the output size of the data with the expected size
		if (le != 0 && le < offset)
			ISOException.throwIt(OUTGOING_DATA_SIZE_ERR);
		apdu.setOutgoingLength(offset);
		apdu.sendBytesLong(responseData, (short)0, offset);
	}


/*	class SymContainer extends java.awt.event.ContainerAdapter
	{
		public void componentAdded(java.awt.event.ContainerEvent event)
		{
			Object object = event.getSource();
			if (object == PurseApplet.this)
				PurseApplet_componentAdded(event);
		}
	}

	void PurseApplet_componentAdded(java.awt.event.ContainerEvent event)
	{
		// to do: code goes here.
	}
*/
}

