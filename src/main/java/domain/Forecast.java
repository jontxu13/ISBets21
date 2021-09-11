package domain;

import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Forecast {

	@GeneratedValue
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer forecastNumber;
	private String forecast;
	private float fee;
	private Question question;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Vector<Bet> bets = new Vector<Bet>();
	private boolean closed;
	
	public Forecast() {
		super();
	}

	public Forecast(int n, String s, float f, Question q) {
		super();
		forecast = s;
		question = q;
		fee = f;
		forecastNumber = n;
		closed = false;
	}

	public Forecast(String s, float f, Question q) {
		super();
		forecast = s;
		fee = f;
		question = q;
		closed = false;

	}

	public String getForecast() {
		return forecast;
	}

	public void setForecast(String s) {
		forecast = s;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question q) {
		question = q;
	}

	public Integer getForecastNumber() {
		return forecastNumber;
	}

	public void setForecastNumber(int n) {
		forecastNumber = n;
	}

	public float getFee() {
		return fee;
	}

	public void setFee(float f) {
		fee = f;
	}

	@Override
	public String toString() {
		return "Forecast: " + forecast + "; " + fee + " ➪ " + question.toString();
	}

	public Bet addBet(Forecast forecast, RegularUser u, float amount) {
		Bet b = new Bet(this, u, amount);
		bets.add(b);
		return b;
	}
	
	public Vector<Bet> getBets() {
		return bets;
	}

	public void setForecasts(Vector<Bet> bets) {
		this.bets = bets;
	}
	
	public void setClosed(boolean b) {
		this.closed=b;
	}
	
	public boolean getClosed() {
		return closed;
	}
}
