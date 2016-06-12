package com.smile.javase.datastructure.database;

public class PrimeNumber {

	protected int i ;
	
	/**
	 * 判断是否为质数(素数)
	 *
	 * @author wjie
	 * @data 2016年6月8日
	 * @param num
	 * @return boolean
	 */
	public static boolean isPrimeNumber(Long num){
		if(num <= 3){
			return num>1;
		}/*else{
			long mid =  (long) Math.sqrt(num);
			for(int i=2 ;i<=mid;i++){
				if(num%i==0){
					return false;
				}
			}
		}*/
		if(num % 2==0 || num % 3 ==0){
			return false;
		}
		for (int i = 5; i * i <= num; i += 6) {
	        if (num % i == 0 || num % (i + 2) == 0) {
	            return false;
	        }
	    }
		return true;
	}
	
	/**
	 * 判断两个数是否互质
	 *
	 * @author wjie
	 * @data 2016年6月8日
	 * @param a
	 * @param b
	 * @return boolean
	 */
	public static boolean isCoprime(Long a,Long b){
		if(Math.abs(b-a) <=1){
			return true;
		}
		if(isPrimeNumber(a)){
			if(a>b){
				return true;
			}else{
				if(b%a!=0){
					return true;
				}else{
					return false;
				}
			}
		}else{
			if(a<b){
				if(isPrimeNumber(b)){
					return true;
				}
			}else{
				if(a%b!=0&&isPrimeNumber(b)){
					return true;
				}
			}
		}
		if(maxCommonDivisor(a, b)==1){
			return true;
		}
		return false;
	}
	
	/**
	 * 求最大公约数(公因子)
	 *
	 * @author wjie
	 * @data 2016年6月8日
	 * @param a
	 * @param b
	 * @return Long
	 */
	public static Long maxCommonDivisor(Long a,Long b){
		if(a>b){
			if(a%b == 0)
				return b;
			else
				return maxCommonDivisor(b,a%b);
		}else{
			if(b%a==0)
				return a;
			else
				return maxCommonDivisor(a,b%a);
		}
	}
	
	
}
