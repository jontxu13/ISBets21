package domain;

public class CreditCard {
	private int[] nums;
	private long num;
	private String type = "";
	private int length = 0;
	private boolean bool = false;
	
	public CreditCard(String cardType, long cardNum)
	{
		type = cardType;
		num = cardNum;
		length();
		nums = new int[length];
		setNum();
	}
	
	//Given by Mr. Guinaugh around 2.28.2013
	public int length()
	{
		long quotient = 1;
		long sentnumber = num;
		while (quotient != 0)
		{
			quotient = (sentnumber / 10);
			sentnumber /= 10;
			length++;
		}
		
		return length;
	}
	
	public int[] setNum()
	{
		long numb = num;
		long numbe = num;
		int number = 0;
		for(int i = 0; i < length; i++)
		{
			numb /= 10;
			numb *= 10;
			
			number = (int)(numbe - numb);
			nums[i] = number;
			
			
			numb /= 10;
			numbe /= 10;
		}
		return nums;
	}
	
	//Show the card number
	public String showNum()
	{
		String str = "";
		for(int i = (length - 1); i >= 0; i--)
		{
			str += nums[i];
		}
		return str;
	}
	
	//For bug-testing. Remove after checking to see if setNums works.
	public String checkNums()
	{
		String str = "";
		for(int i = 0; i < length; i++)
		{
			System.out.println(nums[i]);
			str += nums[i] + "\n";
		}
		str += "\n\n";
		return str;
	}
	
	//Validate the card.
	public boolean validate()
	{
		int sum, sum1 = 0, sum2 = 0;
		int times = 0;
		boolean flag1 = false, flag2 = false;
		
		for(int i = 1; i < length; i += 2)
		{
			times = (int)nums[i] * 2;
			if(times >= 10) //Check if the number is double-digit
				times = times - 9;
			sum1 += times;
		}
		for(int i = 0; i < length; i += 2)
		{
			sum2 += nums[i];
		}
		sum = sum1 + sum2; //Should be divisible by 10 if valid
		if(sum % 10 == 0)
			flag1 = true; //One part of the validation is correct.
		
		//If any of the below are true, then the other part is valid.
		if(type == "Visa" && nums[(length - 1)] == 4)
			flag2 = true;
		else if(type == "MasterCard" && nums[(length - 1)] == 5)
			flag2 = true;
		else if(type == "American Express" && nums[(length - 1)] == 3 && nums[(length - 2)] == 7)
			flag2 = true;
		else if(type == "Discover" && nums[(length - 1)] == 6)
			flag2 = true;
		else
			flag2 = false;
		
		if(flag1 && flag2)
			bool = true;
		
		return bool;
	}
}
