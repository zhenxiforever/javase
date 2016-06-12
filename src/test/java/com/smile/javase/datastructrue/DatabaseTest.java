package com.smile.javase.datastructrue;

import org.junit.Test;

import com.smile.javase.datastructure.database.PrimeNumber;

public class DatabaseTest {

	@Test
	public void testPrimeNumber(){
		Long a = (long)7382673,b=(long)8976532;
		
		System.out.println(PrimeNumber.isPrimeNumber(a)+" , "+PrimeNumber.isPrimeNumber(b));
		
		System.out.println(PrimeNumber.maxCommonDivisor(a, b));
		
		System.out.println(PrimeNumber.isCoprime(a, b));
	}
}
