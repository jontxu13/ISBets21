package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {

	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer questionNumber;
	private String question;
	private float betMinimum;
	private String result;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Vector<Forecast> forecasts = new Vector<Forecast>();
	private Event event;
	private boolean closed;

	public Question() {
		super();
	}

	public Question(Integer queryNumber, String query, float betMinimum, Event event) {
		super();
		this.questionNumber = queryNumber;
		this.question = query;
		this.betMinimum = betMinimum;
		this.event = event;
		closed = false;
	}

	public Question(String query, float betMinimum, Event event) {
		super();
		this.question = query;
		this.betMinimum = betMinimum;

		this.event = event;
		closed = false;
	}

	/**
	 * Get the number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public String getQuestion() {
		return question;
	}

	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @return the minimum bet ammount
	 */

	public float getBetMinimum() {
		return betMinimum;
	}

	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @param betMinimum minimum bet ammount to be setted
	 */

	public void setBetMinimum(float betMinimum) {
		this.betMinimum = betMinimum;
	}

	/**
	 * Get the result of the query
	 * 
	 * @return the the query result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Get the result of the query
	 * 
	 * @param result of the query to be setted
	 */

	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * Get the event associated to the bet
	 * 
	 * @return the associated event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * Set the event associated to the bet
	 * 
	 * @param event to associate to the bet
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	public Vector<Forecast> getForecasts() {
		return forecasts;
	}

	public void setForecasts(Vector<Forecast> forecasts) {
		this.forecasts = forecasts;
	}

	public Forecast addForecast(String forecast, float fee) {
		Forecast f = new Forecast(forecast, fee, this);
		forecasts.add(f);
		return f;
	}

	public boolean DoesForecastExists(String forecast) {
		for (Forecast f : this.getForecasts()) {
			if (f.getForecast().compareTo(forecast) == 0)
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return questionNumber + "; " + question + "; " + Float.toString(betMinimum) + " ➪ " + event.getDescription();
	}
	
	public void setClosed(boolean b) {
		this.closed=b;
	}
	
	public boolean getClosed() {
		return closed;
	}

}