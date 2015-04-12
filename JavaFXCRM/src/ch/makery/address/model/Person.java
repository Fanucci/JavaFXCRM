package ch.makery.address.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Person.
 *
 * @author Marco Jakob
 */
public class Person {

    private final StringProperty initTel;
    private final StringProperty theIP;
    private final StringProperty region;
    private final SimpleStringProperty promoCode;
    private final ObjectProperty<LocalTime> queryTime;
    private final ObjectProperty<LocalDate> queryDate;
    private final ObjectProperty<LocalDateTime> nextCall;
	private final SimpleStringProperty whereFrom;
	private final SimpleStringProperty partnersMail;
	private final SimpleStringProperty comments;
	private final SimpleStringProperty diffTime;

    /**
     * Default constructor.
     */
    public Person() {
        this(null, null, null);
    }

    /**
     * Constructor with some initial data.
     * 
     * @param initTel
     * @param theIP
     */
    public Person(String initTel, String theIP, LocalDateTime nextCall) {
        this.initTel = new SimpleStringProperty(initTel);
        this.theIP = new SimpleStringProperty(theIP);

        // Some initial dummy data, just for convenient testing.
        this.region = new SimpleStringProperty("some region");
        this.promoCode = new SimpleStringProperty("88889");
        this.queryTime =new SimpleObjectProperty<LocalTime>(LocalTime.of(11, 30, 12));
        this.nextCall =new SimpleObjectProperty<LocalDateTime>(nextCall);
        this.queryDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
        this.whereFrom = new SimpleStringProperty("Friends");
        this.partnersMail = new SimpleStringProperty("rwe@sfdgh.ru");
        this.comments = new SimpleStringProperty("Нет ответа");
        this.diffTime= new SimpleStringProperty("-2");
    }
    
    public Person(String initTel, String theIP,String promoCode, String whereFrom,
    		LocalTime queryTime, LocalDate queryDate, String partnersMail, String comments, String region, String diffTime, LocalDateTime nextCall) {
        this.initTel = new SimpleStringProperty(initTel);
        this.theIP = new SimpleStringProperty(theIP);
        this.region =  new SimpleStringProperty(region);
        this.promoCode = new SimpleStringProperty(promoCode);
        this.queryTime =new SimpleObjectProperty<LocalTime>(queryTime);
        this.nextCall =new SimpleObjectProperty<LocalDateTime>(nextCall);
        this.queryDate = new SimpleObjectProperty<LocalDate>(queryDate);
        this.whereFrom = new SimpleStringProperty(whereFrom);
        this.partnersMail = new SimpleStringProperty(partnersMail);
        this.comments = new SimpleStringProperty(comments);
        this.diffTime= new SimpleStringProperty(diffTime);
        System.out.println(diffTime);
    }
    

    public String getinitTel() {
        return initTel.get();
    }

    public void setinitTel(String initTel) {
        this.initTel.set(initTel);
    }

    public StringProperty initTelProperty() {
        return initTel;
    }

    public String gettheIP() {
        return theIP.get();
    }

    public void settheIP(String theIP) {
        this.theIP.set(theIP);
    }

    public StringProperty theIPProperty() {
        return theIP;
    }

    public String getregion() {
        return region.get();
    }

    public void setregion(String region) {
        this.region.set(region);
    }

    public StringProperty regionProperty() {
        return region;
    }

    public String getpromoCode() {
        return promoCode.get();
    }

    public void setpromoCode(String promoCode) {
        this.promoCode.set(promoCode);
    }

    public SimpleStringProperty promoCodeProperty() {
        return promoCode;
    }

    public LocalTime getqueryTime() {
        return queryTime.get();
    }

    public void setqueryTime(LocalTime queryTime) {
        this.queryTime.set(queryTime);
    }

    public ObjectProperty<LocalTime> queryTimeProperty() {
        return queryTime;
    }

    public LocalDate getqueryDate() {
        return queryDate.get();
    }

    public void setqueryDate(LocalDate queryDate) {
        this.queryDate.set(queryDate);
    }

    public ObjectProperty<LocalDate> queryDateProperty() {
        return queryDate;
    }
    
    public String getwhereFrom() {
        return whereFrom.get();
    }

    public void setwhereFrom(String whereFrom) {
        this.whereFrom.set(whereFrom);
    }

    public StringProperty whereFromroperty() {
        return whereFrom;
    }

	public String getdiffTime() {
		return diffTime.get();
	}
    public LocalDateTime getnextCall() {
        return nextCall.get();
    }

    public void setnextCall(LocalDateTime nextCall) {
        this.nextCall.set(nextCall);
    }

    public ObjectProperty<LocalDateTime> nextCallProperty() {
        return nextCall;
    }
}