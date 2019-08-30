package com.gemplus.pacap.utils;

import javacard.framework.TransactionException;
import javacard.framework.Util;
import javacard.framework.ISOException;
import javacard.framework.JCSystem;

public class Decimal /*extends Object*/{    
    private com.gemplus.pacap.utils.DecimalException decimal_exception = new DecimalException();
    
    public static final short DECIMAL_OVERFLOW   = (short)0x9F15;
    public static final short MAX_DECIMAL_NUMBER = (short)32767;    
    private static final byte MAX_DEPTH = (byte) 1;
    public static final short PRECISION      = (short) 1000;
    
    private short intPart = (short) 0;
    private short decPart = (short) 0;

    private short intPart_ = intPart;
    private short decPart_ = decPart;
    
    private byte depth = (byte) 0;
       
    public Decimal() {
    	super();
    	intPart = (short) 0;
    	decPart = (short) 0;
    }

    public Decimal(short v) 
	throws ISOException  {
        this();
        try{
            this.setValue(v);
        }
        catch(DecimalException e){
            ISOException.throwIt((short)0x9F15);			
        }
    }

    public Decimal(short i, short d)
	throws ISOException {
        this();
        try{
            this.setValue(i,d);
        }
        catch(DecimalException e){
            ISOException.throwIt((short)0x9F15);			
        }
    }

    public Decimal(Decimal d)
	throws ISOException {
        this();
        try{
            setValue(d);
        }
        catch(DecimalException e){
            ISOException.throwIt((short)0x9F15);			
        }
    }

    /*@
      modifies intPart, decPart;
      modifies decimal_exception.instance, decimal_exception.instance.type;//!!
    */
    public Decimal add(com.gemplus.pacap.utils.Decimal d) throws DecimalException{
	add(d.getIntPart(), d.getDecPart());            
	if(intPart < 0) 
	    decimal_exception.throwIt(decimal_exception.DECIMAL_OVERFLOW);
	return this;
    }
    
    /*@
      modifies intPart, decPart;
      modifies decimal_exception.instance, decimal_exception.instance.type;//!!
    */
     public Decimal sub(com.gemplus.pacap.utils.Decimal d) throws DecimalException{
	 add( (short) -d.getIntPart(), (short) -d.getDecPart());
	 if(intPart < 0) 
	     decimal_exception.throwIt(decimal_exception.DECIMAL_OVERFLOW);
	 return this;
     }
    
    /*@
      modifies intPart, decPart;
      modifies decimal_exception.instance, decimal_exception.instance.type;//!!
    */
    public Decimal mul(com.gemplus.pacap.utils.Decimal d) throws DecimalException{
        mul(d.getIntPart(), d.getDecPart());
        if(intPart < 0)
            decimal_exception.throwIt(decimal_exception.DECIMAL_OVERFLOW);
        return this;
     }
    
    /*@ 
      modifies intPart, decPart;  
    */
    private Decimal oppose(){
        intPart = (short) -intPart;
        decPart = (short) -decPart;
        return this;
    }

    /*@ 
      modifies intPart, decPart;
    */
    public Decimal round(){   
	if (decPart >= (PRECISION/2) && intPart != MAX_DECIMAL_NUMBER) 
          intPart++;
        decPart = (short) 0;
        return this;
    }        
    
    /*@ 
      modifies \nothing; 
    */
    public short compareTo(short ref){
        short resu = (short) (intPart - ref );
        if ( resu == (short ) 0 ){          
            if ( decPart != 0 ) {
                resu = (short) ( decPart > (short) 0 ? (short) 1 : (short) -1 );           
	    }
        }
        else  resu = (short) ( resu > (short) 0 ? (short) 1 : (short) -1 );
        return resu;
    }        
    
    /*@
      modifies \nothing;
    */
    public boolean isNull(){
        return ( compareTo((short) 0) == (short) 0);
    }    
    
    /*@ 
      modifies \nothing;
    */
    public boolean isPositif(){
        return (compareTo((short) 0) >= (short) 0 );
    }    
    
    /*@ 
      modifies \nothing;
    */
    public boolean isNegatif(){
        return (compareTo((short) 0) < (short) 0 );
    }

    /*@
      modifies \nothing;
    */
    public boolean isGreaterEqualThan(com.gemplus.pacap.utils.Decimal d){
        boolean resu = false;
        if      (intPart > d.getIntPart())   resu = true;
        else if (intPart < d.getIntPart())   resu = false;
        else if (intPart == d.getIntPart()){       
            if      ((decPart > d.getDecPart())||(decPart == d.getDecPart()))   resu = true;
            else if (decPart < d.getDecPart())   resu = false;
        }
        return resu;
    }
    
    /*@ 
      modifies \nothing;
    */
    public boolean isSmallerEqualThan(com.gemplus.pacap.utils.Decimal d){
        boolean resu = false;
        resu = !isGreaterThan(d);
	return resu;
    }
    
    /*@ 
      modifies \nothing;
    */
    public boolean isGreaterThan(com.gemplus.pacap.utils.Decimal d) {
	return ( isGreaterEqualThan(d) && ! equal(d) );
    }
    
    /*@ 
      modifies \nothing;
    */
    public boolean isSmallerThan(com.gemplus.pacap.utils.Decimal d){
        return ( isSmallerEqualThan(d) && ! equal(d));
    }
    
    /*@ 
      modifies \nothing;
    */
    public boolean equal(com.gemplus.pacap.utils.Decimal d){
	return ( intPart == d.getIntPart() && decPart == d.getDecPart());
    }    
    
    /*@ 
      modifies intPart, decPart; //!
      modifies decimal_exception.instance, decimal_exception.instance.type;//!!
    */
    public Decimal setValue(short v) throws DecimalException{
	if(v < 0)
	    decimal_exception.throwIt(decimal_exception.DECIMAL_OVERFLOW);
	intPart = v;
	decPart = (short) 0;
	return this;
    }
    
    /*@ 
      modifies intPart,decPart;//!!
      modifies decimal_exception.instance, decimal_exception.instance.type;//!!
    */ 
    public Decimal setValue(short i, short d) throws DecimalException{
	if(i < 0 || d < 0 || d >= PRECISION ||
           (i == MAX_DECIMAL_NUMBER && d != 0))
	    decimal_exception.throwIt(decimal_exception.DECIMAL_OVERFLOW);
	
	intPart = i;
	decPart = d;
	return this;
    }
    
    /*@ 
      modifies intPart,decPart;//!!
      modifies decimal_exception.instance, decimal_exception.instance.type;//!!
    */ 
    public Decimal setValue(com.gemplus.pacap.utils.Decimal d) throws DecimalException{
	 return setValue(d.getIntPart(),d.getDecPart());
    }     

    //@modifies \nothing;
    public short setValue(byte buffer[], short d){
	return d ;
    }    
    
    /*@ 
      modifies \nothing;
    */
    public short getIntPart(){
	return intPart;
    }

    /*@ 
      modifies \nothing;
    */
    public short getDecPart(){
	return decPart;
    }    
    
    /*@
      modifies intPart, decPart;
    */
    public short getRoundedValue(){
        short resu = 0;
        short int_ = intPart;
        short dec_ = decPart;
	//commented out by Néstor CATAÑO
        //resu = round().getIntPart();
        intPart = int_;
        decPart = dec_;
        return resu;
    }    
 
    /*@
      modifies bArray[off], bArray[off+1], bArray[off+2], bArray[off+3];
      modifies bArray[resu], bArray[resu+1];//-!!-
      modifies TransactionException.systemInstance._reason;
    */
    public short getValue(byte [] bArray, short off) 
	throws TransactionException,
	       ArrayIndexOutOfBoundsException{
        short resu = off;
        resu  = javacard.framework.Util.setShort(bArray,resu,intPart);        
	resu  = javacard.framework.Util.setShort(bArray,resu,decPart);
        return resu;
    }    
    
    /*@
      modifies intPart_, decPart_, depth;
    */
    public void saveValue(){
	if ( depth < MAX_DEPTH ) {
	    intPart_ = intPart;
	    decPart_ = decPart;
	    depth++;
	}
    }
    
    /*@
      modifies intPart, decPart, depth;
    */
    public void restoreValue(){
	if (depth > 0 ){
	    intPart = intPart_;
	    decPart = decPart_;
	    depth--;
	}
    }	
	
    /*@
      modifies intPart, decPart ;
    */
    private void add(short e, short f){
        intPart += e;
	
        if ( intPart > 0 && decPart < 0 ) {                        
            intPart--;
            decPart = (short) (decPart + PRECISION);            
        }
        else if ( intPart < 0 && decPart > 0 ){
            intPart++;
            decPart =(short) (decPart - PRECISION);
        }
        
        decPart += f;
        if ( intPart > 0 && decPart < 0 ) {                        
            intPart--;
            decPart = (short) (decPart + PRECISION);            
        }
        else if ( intPart < 0 && decPart > 0 ){
            intPart++;
            decPart = (short) (decPart - PRECISION);
        }
        else {	    
            short retenue = (short) 0;
            short signe = 1;
            if ( decPart < 0 ) {
                signe = (short) -1;
                decPart = (short) -decPart;
            }        
            retenue = (short) (decPart / PRECISION);
            decPart = (short) (decPart % PRECISION);
            retenue *= signe;
            decPart *= signe;        
            intPart += retenue;        
        }   
    }    

    /*@
      modifies intPart, decPart;
    */
    private void mul(short e, short f){	
        short intBackup = intPart; 
        short decBackup = decPart;
        
        short nbIter = e;        
        if ( nbIter < 0 ) { nbIter = (short) -nbIter;}
        intPart = (short) 0;
        decPart = (short) 0;
        for ( short i = (short) 0; i < nbIter; i++){
	    add(intBackup, decBackup);
        }
        if ( e < 0 ) { oppose(); }
        
        short intPart_ = intPart;
        short decPart_ = decPart;
        intPart = (short) 0;
        decPart = (short) 0;
        nbIter = intBackup;
        if ( nbIter < 0 ) { nbIter = (short) -nbIter; }
        for ( short i = (short) 0; i < nbIter; i++ ){
            add((short) 0, f);
        }
        if (intBackup < 0 ) { oppose(); }
        
        add(intPart_, decPart_);
        
        short signe = (short)1;
        
        short arrondis1 = decBackup;                
        if ( arrondis1 < 0 ) {
           arrondis1 = (short)  -arrondis1;
           signe = (short) -signe;
        }
        short arrondis2 = f;
        if ( arrondis2 < 0 ) {
            arrondis2 = (short) -arrondis2;
            signe = (short) -signe;
        }
        
        short decal = (short) 0;
        while ( arrondis1 > 100 ) {
            arrondis1 /= (short) 10;
            decal++;
        }
        while ( arrondis2 > 100 ) {
            arrondis2 /= (short) 10;        
            decal++;
        }
        short temp = (short)  (arrondis1 * arrondis2);        
        
        short aux = (short) 1000;
        while ( decal > 0 ) {
            aux /= (short) 10;
            decal--;
        }
        temp /= aux;
        temp *= signe;
	add((short) 0, temp);
        add((short) 0, temp);        
    }

}
