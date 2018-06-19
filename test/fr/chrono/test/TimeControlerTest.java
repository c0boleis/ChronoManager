package fr.chrono.test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import fr.chrono.controlers.TimeControler;

public class TimeControlerTest {

	@Ignore
	@Test
	public void testRegexTime() throws IOException {
		String[] texts = new String[] {
				"15:30:56,561","15:30:66,561","15:30:00,561",
				"151:30:00,561","a5:30:66,561","15:70:00,561",
				"01:30:56,561","00:00:00,000","15:70:00,5-1",
				"01:380:56,561","0:00:00,000","15:50:00,51"};
		boolean[] results = {
				true,false,true,
				false,false,false,
				true,true,false,
				false,false,false};

		for(int index = 0;index<texts.length;index++) {
			System.out.println(texts[index]);
			Assert.assertEquals(results[index], TimeControler.isTimeString(texts[index]));
		}
	}

	@Ignore
	@Test
	public void testParseStringToLong() throws IOException {

		long time = TimeControler.parseTimeTolong("15:56:03,345");
		Assert.assertEquals(57363345l, time);
		time = TimeControler.parseTimeTolong("00:00:00,000");
		Assert.assertEquals(0l, time);
		time = TimeControler.parseTimeTolong("15:56:04,345");
		Assert.assertNotEquals(57363345l, time);
		time = TimeControler.parseTimeTolong("23:59:56,897");
		Assert.assertEquals(86396897l, time);
		
		time = TimeControler.parseTimeTolong("00:00:00,890");
		Assert.assertEquals(890l, time);
	}

	@Test
	public void testParseLongToString() throws IOException {
		String time = TimeControler.parseTimeToString(0l);
		Assert.assertEquals("00:00:00,000", time);
		time = TimeControler.parseTimeToString(57363345l);
		Assert.assertEquals("15:56:03,345", time);
		time = TimeControler.parseTimeToString(57363345l);
		Assert.assertNotEquals("15:56:04,345", time);
		time = TimeControler.parseTimeToString(86396897l);
		Assert.assertEquals("23:59:56,897", time);

	}



}
