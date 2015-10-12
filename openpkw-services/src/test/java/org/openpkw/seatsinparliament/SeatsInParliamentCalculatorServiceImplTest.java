package org.openpkw.seatsinparliament;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by Kamil Dolinski on 10/04/2015.
 */
public class SeatsInParliamentCalculatorServiceImplTest {

	SeatsInParliamentCalculatorServiceImpl seatsInParliamentCalculatorServiceImpl;
	@BeforeClass
	public void init() {
		 seatsInParliamentCalculatorServiceImpl = new SeatsInParliamentCalculatorServiceImpl();
	}

	
	@DataProvider(name = "testdHoundt")
	public static Object[][] primeNumbers() {
		Map<String,Integer> a =new HashMap<String,Integer>();
		a.put("A", new Integer(1000));
		a.put("B", new Integer(400));
		a.put("C", new Integer(500));
		a.put("D", new Integer(600) );
		a.put("E", new Integer(100) );
		Map<String,Integer> aExpected =new HashMap<String,Integer>();
		aExpected.put("A", new Integer(5));
		aExpected.put("B", new Integer(1));
		aExpected.put("C", new Integer(2));
		aExpected.put("D", new Integer(2));
		aExpected.put("E", new Integer(0));
		
		
		Map<String,Integer> b =new HashMap<String,Integer>();
		b.put("A", new Integer(1228));
		b.put("B", new Integer(1012));
		b.put("C", new Integer(850));
		b.put("D", new Integer(543) );
		b.put("E", new Integer(352) );
		Map<String,Integer> bExpected =new HashMap<String,Integer>();
		bExpected.put("A", new Integer(2));
		bExpected.put("B", new Integer(2));
		bExpected.put("C", new Integer(2));
		bExpected.put("D", new Integer(1));
		bExpected.put("E", new Integer(0));
		
		return new Object[][] { 
			{ 10,a,aExpected },
			{ 7,b,bExpected },
			 };
	}

	@Test(dataProvider = "testdHoundt")
	public void dHoundtTest(int mandate,Map<String,Integer> partyVoices,Map<String,Integer> expected) {
		//given
		//when
		Map<String, Integer> dHondt = seatsInParliamentCalculatorServiceImpl.dHondt(mandate, partyVoices);
//		Set<String> keySet = dHondt.keySet();
//		for (String party : keySet) {
//			 System.out.println(party+" - "+dHondt.get(party));
//		}
		//then
		Assert.assertEquals(dHondt, expected);
	}

}
