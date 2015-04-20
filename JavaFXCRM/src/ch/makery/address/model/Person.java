package ch.makery.address.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final SimpleStringProperty queryTime;
    private final ObjectProperty<LocalDate> queryDate;
    private final ObjectProperty<LocalDateTime> nextCall;
	private final SimpleStringProperty whereFrom;
	private final SimpleStringProperty name1;
	private final SimpleStringProperty eMail;
	private final SimpleStringProperty status;
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
        this.queryTime =new SimpleStringProperty("15:50:21");
        this.nextCall =new SimpleObjectProperty<LocalDateTime>(nextCall);
        this.queryDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
        this.whereFrom = new SimpleStringProperty("Friends");
        this.name1 = new SimpleStringProperty("Алексей");
        this.eMail = new SimpleStringProperty("ryesh@sdfhgngkkyuert.ru");
        this.status = new SimpleStringProperty("3");
        this.comments = new SimpleStringProperty("Нет ответа");
        this.diffTime= new SimpleStringProperty("-2");
    }
    
    public Person(String initTel, String theIP,String promoCode, String whereFrom,
    		String queryTime, LocalDate queryDate, String name1, String comments, 
    		String region, String diffTime, LocalDateTime nextCall, String eMail, String status) {
        this.initTel = new SimpleStringProperty(initTel);
        this.theIP = new SimpleStringProperty(theIP);
        this.region =  new SimpleStringProperty(region);
        this.promoCode = new SimpleStringProperty(promoCode);
        this.queryTime =new SimpleStringProperty(queryTime);
        this.nextCall =new SimpleObjectProperty<LocalDateTime>(nextCall);
        this.queryDate = new SimpleObjectProperty<LocalDate>(queryDate);
        this.whereFrom = new SimpleStringProperty(whereFrom);
        this.name1 = new SimpleStringProperty(name1);
        this.eMail = new SimpleStringProperty(eMail);
        this.status = new SimpleStringProperty(status);
        this.comments = new SimpleStringProperty(comments);
        this.diffTime= new SimpleStringProperty(diffTime);
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

    public String geteMail() {
        return eMail.get();
    }
    public void seteMail(String eMail) {
        this.eMail.set(eMail);
    }
    public SimpleStringProperty eMailProperty() {
        return eMail;
    }
    
    public String getname1() {
        return name1.get();
    }
    public void setname1(String name1) {
        this.name1.set(name1);
    }
    public SimpleStringProperty name1Property() {
        return name1;
    }
    
    public String getstatus() {
        return status.get();
    }
    public void setstatus(String status) {
        this.status.set(status);
    }
    public SimpleStringProperty statusProperty() {
        return status;
    }
    
    
    public String getqueryTime() {
        return queryTime.get();
    }

    public void setqueryTime(String queryTime) {
        this.queryTime.set(queryTime);
    }

    public SimpleStringProperty queryTimeProperty() {
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
    
    public String getcomments() {
        return comments.get();
    }

    public void setcomments(String comments) {
        this.comments.set(comments);
    }

    public StringProperty commentsProperty() {
        return comments;
    }
    
    public String getwhereFrom() {
        return whereFrom.get();
    }

    public void setwhereFrom(String whereFrom) {
        this.whereFrom.set(whereFrom);
    }

    public StringProperty whereFromProperty() {
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