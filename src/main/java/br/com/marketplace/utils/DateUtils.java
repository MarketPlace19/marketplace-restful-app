package br.com.marketplace.utils;

import java.text.DateFormat;  
//import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import java.util.Date;  
  
/** 
* 
* @author Tomaz Lavieri 
*/  
public class DateUtils {  
    /** 
     * Formata��o "dd/MM/yyyy". 
     */  
    public static DateFormat BRAZIL_FORMAT = new SimpleDateFormat("dd/MM/yyyy");  
    /** 
     * Formata��o "dd/MM/yy". 
     */  
    public static DateFormat BRAZIL_SHORT_FORMAT =  
            new SimpleDateFormat("dd/MM/yy");  
  
    /** 
     * N�o � possivel instanciar DateUtils. 
     */  
    private DateUtils(){}  
  
    /** 
     * Retorna o valor do hor�rio minimo para a data de referencia passada. 
     * <BR> 
     * <BR> Por exemplo se a data for "30/01/2009 as 17h:33m:12s e 299ms" a data 
     * retornada por este metodo ser� "30/01/2009 as 00h:00m:00s e 000ms". 
     * @param date de referencia. 
     * @return {@link Date} que representa o hor�rio minimo para dia informado. 
     */  
    public static Date lowDateTime(Date date) {  
        Calendar aux = Calendar.getInstance();  
        aux.setTime(date);  
        toOnlyDate(aux); //zera os parametros de hour,min,sec,milisec  
        return aux.getTime();  
    }  
  
    /** 
     * Retorna o valor do hor�rio maximo para a data de referencia passada. 
     * <BR> 
     * <BR> Por exemplo se a data for "30/01/2009 as 17h:33m:12s e 299ms" a data 
     * retornada por este metodo ser� "30/01/2009 as 23h:59m:59s e 999ms". 
     * @param date de referencia. 
     * @return {@link Date} que representa o hor�rio maximo para dia informado. 
     */  
    public static Date highDateTime(Date date) {  
        Calendar aux = Calendar.getInstance();  
        aux.setTime(date);  
        toOnlyDate(aux); //zera os parametros de hour,min,sec,milisec  
        aux.roll(Calendar.DATE, true); //vai para o dia seguinte  
        aux.roll(Calendar.MILLISECOND, false); //reduz 1 milisegundo  
        return aux.getTime();  
    }  
  
    /** 
     * Zera todas as referencias de hora, minuto, segundo e milesegundo do 
     * {@link Calendar}. 
     * @param date a ser modificado. 
     */  
    public static void toOnlyDate(Calendar date) {  
        date.set(Calendar.HOUR_OF_DAY, 0);  
        date.set(Calendar.MINUTE, 0);  
        date.set(Calendar.SECOND, 0);  
        date.set(Calendar.MILLISECOND, 0);  
    }  
  
    /** 
     * Converte uma {@link String} no formato "dd/MM/yyyy" ou "dd/MM/yy" em um 
     * {@link Date}. 
     * @param date a {@link String} contendo a data. 
     * @return o objeto {@link Date} para a string passada. 
     * @throws  IllegalArgumentException caso a string n�o seja em dos dois 
     *          formatos permitidos. 
     */  
//    public static Date toDate(String date) throws IllegalArgumentException {  
//        if (date == null)  
//                return null;  
//        try {  
//            return date.length() > 8 ?  
//                BRAZIL_FORMAT.parse(date) : BRAZIL_SHORT_FORMAT.parse(date);  
//        } catch (ParseException ex) {  
//            throw ExceptionUtils  
//                    .toRuntimeException(IllegalArgumentException.class, ex);  
//        }  
//    }  
  
    /** 
     * Formata a data passada em uma {@link String} com o formato "dd/MM/yyyy". 
     * @param date a data a ser formatada. 
     * @return {@link String} com a data formatada. 
     */  
    public static String toString(Date date) {  
        if (date == null)  
                return null;  
        return BRAZIL_FORMAT.format(date);  
    }  
    /** 
     * Formata a data passada em uma {@link String} com <tt>shortFormat</tt> 
     * "dd/mm/yy", ou com o formato longo "dd/mm/yyyy". 
     * @param date a data a ser formatada. 
     * @param shortFormat <tt>true</tt> se o formato � o curto, ou 
     *          <tt>false</tt> caso o formato seja o longo. 
     * @return {@link String} com a data formatada. 
     */  
    public static String toString(Date date, boolean shortFormat) {  
        if (date == null)  
                return null;  
        return shortFormat ?  
            BRAZIL_SHORT_FORMAT.format(date) : BRAZIL_FORMAT.format(date);  
    }  
}  