package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Bet {
	@GeneratedValue
	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer betNumber;
	private Forecast forecast;
	private User user;
	private float amount;
	private String estadoApuesta; // Awaiting Win Lose Canceled
	
	public  String getEstadoApuesta() {
		return estadoApuesta;
	}

	public void setEstadoApuesta(String estadoApuesta) {
		this.estadoApuesta = estadoApuesta;
	}

	public Bet() {
		super();
	}

	public Bet(Integer n, Forecast forecast, User user, float amount) {
		super();
		this.betNumber = n;
		this.forecast = forecast;
		this.user = user;
		this.amount = amount;
		this.estadoApuesta = "Awaiting";
	}

	public Bet(Forecast forecast, User user, float amount) {
		super();
		this.forecast = forecast;
		this.user = user;
		this.amount = amount;
		this.estadoApuesta = "Awaiting";

	}

	public Forecast getForecast() {
		return forecast;
	}

	public void setForecast(Forecast forecast) {
		this.forecast = forecast;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Bet [forecast: " + forecast + ", user: " + user + ", amount: " + amount + "forecast: "
				+ forecast.getForecast() + "question: " + forecast.getQuestion().getQuestion() + "event: "
				+ forecast.getQuestion().getEvent().getDescription() + "]";
	}

	public String toString2() {

		

		

		return forecast.getQuestion().getEvent().getEventDate().toString().substring(0, 11) + "             "
				+ forecast.getQuestion().getEvent().getDescription() + "                             "
				+ forecast.getQuestion().getQuestion() + "                                                  "
				+ this.forecast.getForecast() + "                                           "
				+ estadoApuesta + "                                "
				+ forecast.getQuestion().getResult() + "                    " + this.getAmount()
				+ "                         " + forecast.getFee();

//				+ ""
//				+ ""
//				+ "forecast: " + forecast + ", amount: " + amount + "forecast: "
//				+ "question: " + forecast.getQuestion().getQuestion() + "event: "
//				+ forecast.getQuestion().getEvent().getDescription() + "]";
//	}

	}

	public String toString3() {

		return forecast.getQuestion().getEvent().getEventDate().toString().substring(0, 11)
				+ "                                   " + forecast.getQuestion().getEvent().getDescription()
				+ "                                        " + forecast.getQuestion().getQuestion()
				+ "                                    " + this.forecast.getForecast()
				+ "                                                         " + this.getAmount()
				+ "                                                             " + forecast.getFee();
	}
}
