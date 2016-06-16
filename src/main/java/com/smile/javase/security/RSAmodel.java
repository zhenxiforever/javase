package com.smile.javase.security;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.smile.javase.datastructure.database.PrimeNumber;

/**
 * RSA 原理：</br>
 * 1，随机两个质数(素数)p，q，并求积n=p*q；</br>
 * 2，求n的欧拉函数ψ(n)=ψ(pq)=(p-1)*(q-1)；</br>
 * 3，随机选择整数e，满足 1<e<ψ(n)，且e与ψ(n)互质；</br>
 * 4，计算e对ψ(n)的模反元素d。 e*d ≡ 1 mod ψ(n)；  </br>
 * 5，公钥：(n,e)，私钥(n,d)；</br>
 * 6，加密 ：</br>
 * 	&nbsp;&nbsp;&nbsp; a，明文转ASCII(取整数)；且须小于n，如果m转整数后大于n：</br>
 * 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一种是把长信息分割成若干段短消息，每段分别加密；</br>
 * 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一种是先选择一种"对称性加密算法"（比如DES），用这种算法的密钥加密信息，再用RSA公钥加密DES密钥。</br>
 * 	&nbsp;&nbsp;&nbsp; b，根据  m^e ≡ c (mod n) 算出密文C，也即 c= m^e%n</br>
 * 7，解密：</br>
 * 	&nbsp;&nbsp;&nbsp; a，根据  c^d ≡ m (mod n) 算出密文C，也即 m= c^d%n</br>
 * 	&nbsp;&nbsp;&nbsp; b，此时解密出应是ASCII，再转对应的字符串。</br>
 * 
 * @author wjie
 * @date 2016年6月12日
 */
public class RSAmodel {

	private final String publicKey = "RSAPublicKey";
	private final String privateKey = "RSAPrivateKey";
	
	
	
	public Map<String,RSAkey> initRSAkey(){
		Map<String, RSAkey> map = new HashMap<String, RSAkey>(); 
		int bitLength = 3;
//		BigInteger p = PrimeNumber.getPrimeNumber(bitLength);
//		BigInteger q = PrimeNumber.getPrimeNumber(bitLength);
//		BigInteger n = p.multiply(q);
//		BigInteger on = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
//		BigInteger e = on.subtract(BigInteger.ONE);
//		BigInteger d = e.add(on);
		RSAkey publickey = new RSAkey(new BigInteger("3233"),new BigInteger("17"));
		RSAkey privatekey = new RSAkey(new BigInteger("3233"),new BigInteger("2753"));
//		System.out.println("p:"+p+",q:"+q+",on:"+on);
		map.put(publicKey, publickey);
		map.put(privateKey, privatekey);
		return map;
	}
	
	
	public int encryptByPublicKey(int data,RSAkey publickey){
		BigInteger dataBig = new BigInteger(data+"");
		return dataBig.modPow(publickey.exponent, publickey.modulus).intValue();
	}
	
	public int decryptByPrivateKey(int data,RSAkey privatekey){
		BigInteger dataBig = new BigInteger(data+"");
		return dataBig.modPow(privatekey.exponent, privatekey.modulus).intValue();
	}
	
	class RSAkey{
		
		/**	模  */
		private BigInteger modulus;
		
		/**	指数	*/
		private BigInteger exponent;
		
		public RSAkey(){}
		
		public RSAkey(String modulus,String exponent){
			this.modulus = new BigInteger(modulus);
			this.exponent = new BigInteger(exponent);
		}

		public RSAkey(BigInteger modulus,BigInteger exponent){
			this.modulus = modulus;
			this.exponent = exponent;
		}
		
		public BigInteger getModulus() {
			return modulus;
		}

		public BigInteger getExponent() {
			return exponent;
		}
		
		@Override
		public String toString() {
			return modulus+","+exponent;
		}
	}
	
	public static void main(String[] args) {
		
		RSAmodel test = new RSAmodel();
		Map<String,RSAkey> map = test.initRSAkey();
		int ming = 665;
		int mi = test.encryptByPublicKey(ming, map.get(test.publicKey));
		System.out.println(ming+" , "+mi);
		System.out.println(map.get(test.publicKey).toString()+";"+map.get(test.privateKey).toString());
		System.out.println(test.decryptByPrivateKey(mi, map.get(test.privateKey)));
		
	}
}
