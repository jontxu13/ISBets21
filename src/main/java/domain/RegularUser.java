package domain;

import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class RegularUser extends User {

	private String birthDate;

	private String email;
	private String bankAccount;
	private Integer phoneNumb;
	private CreditCard creditCard;
	private String address;
	private float saldo;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Vector<Bet> bets = new Vector<Bet>();

	public RegularUser(String userName, String userPass, String firstName, String lastName, String birthDate,
			String email, String bankAccount, Integer phoneNumber, String address, float balance) {
		super(userName, userPass, firstName, lastName);
		this.birthDate = birthDate;
		this.email = email;
		this.bankAccount = bankAccount;
		this.phoneNumb = phoneNumber;
		this.address = address;
		this.saldo = balance;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Integer getPhoneNumb() {
		return phoneNumb;
	}

	public void setPhoneNumb(Integer phoneNumb) {
		this.phoneNumb = phoneNumb;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getBalance() {
		return saldo;
	}

	public void setBalance(float balance) {
		this.saldo = balance;
	}
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard c) {
		this.creditCard = c;
	}
	
	@Override
	public String toString() {
		String st = super.toString() + "\nbirthDate: " + birthDate + "\nemail:" + email + "\nbankAccount: "
				+ bankAccount + "\nphoneNumb: " + phoneNumb + "\naddress: " + address + "\nsaldo: " + saldo;
		return st;
	}

	public Bet addBet(Bet b) {
		bets.add(b);
		return b;
	}

	public Vector<Bet> getBets() {
		return this.bets;
	}

	public void setBets(Vector<Bet> bets) {
		this.bets = bets;
	}

}
