package com.pino.project.ocpairprogramming.java8.ocp.chapter5.localization;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class WorkingWithDatesAndTimes {

	public static void main(String[] args) {
		ZoneId zone = ZoneId.of("US/Eastern");
		ZonedDateTime zoned1 = ZonedDateTime.of(2020, 12, 25,
								6, 49, 30, 200, zone);
		System.out.println("Zone Id : "+zone);
		ZoneId DefZone = ZoneId.systemDefault();
		System.out.println("Default Zone Id : "+zone);
		
		ZoneId.getAvailableZoneIds().stream()
			.filter(z -> z.contains("US") || z.contains("America"))//try the country or city name
			.sorted()
			.limit(1)//Without the limit 177 lines are being printed out
			.forEach(System.out::println);//America/Adak
		
		//LocalDate d = new LocalDate();//DOES NOT COMPILE as it has private consructor and it needs the factory
//		LocalDate d = LocalDate.of(2015, Month.JANUARY, 32); // throws DateTimeException
		
		//Manipulating Dates and Times
		//NB: The Date and time classes are IMMUTABLE
		System.out.println("\nManipulating Dates and Times ..");
		LocalDate date = LocalDate.of(2014, Month.JANUARY, 20);
		System.out.println(date);//2014-01-20
		date = date.plusDays(2);
		System.out.println(date);//2014-01-22
		date = date.plusWeeks(1);
		System.out.println(date);//2014-01-29
		date = date.plusMonths(1);
		System.out.println(date);//2014-02-28 because Feb is made of 28 days, and that year wasn't leap
		//NB: Leap years are the ones multiple of 4 or 400
		date = date.plusYears(5);
		System.out.println(date);//2019-02-28
		
		//How to go backward in time ?
		LocalDate date1 = LocalDate.of(2020, Month.JANUARY, 20);
		LocalTime time1 = LocalTime.of(5, 15);
		LocalDateTime dateTime = LocalDateTime.of(date1, time1);
		System.out.println(dateTime);//2020-01-20T05:15
		dateTime = dateTime.minusDays(1);
		System.out.println(dateTime);//2020-01-19T05:15
		dateTime = dateTime.minusHours(10);
		System.out.println(dateTime);//2020-01-18T19:15
		dateTime = dateTime.minusSeconds(30);
		System.out.println(dateTime);//2020-01-18T19:14:30
		
		//Moreover, it is common for date and time methods to be chained
		LocalDateTime dateTime1 = LocalDateTime.of(date1, time1)
				.minusDays(1).minusHours(10).minusSeconds(30);
		System.out.println("With method chaining : "+dateTime1);//2020-01-18T19:14:30 The very same 
		//TRICKY Examples
		date1.plusDays(10);
		System.out.println(date);//2020-01-20. Adding days was useless because of immutability
		//date1 = date1.plusMinutes(1);//IT DOES NOT COMPILE because it does not contain times
		/*
		 * Table 5. 2 Methods in LocalDate, LocalTime, LocalDateTime, and ZonedDateTime
		 * +-------------------------------------------------------------------------------------
									Can Call on   Can Call on 	Can Call on 
									LocalDate?    LocalTime?  	LocalDateTime or ZonedDateTime?
		   +-----------------------+------------+--------------+---------------------------------
			plusYears/ minusYears 	  Yes 			  No 			Yes
			plusMonths/ minusMonths   Yes 			  No 			Yes
			plusWeeks/ minusWeeks     Yes 			  No 			Yes
			plusDays/ minusDays       Yes			  No 			Yes
			plusHours/ minusHours     No 			  Yes 			Yes
			plusMinutes/ minusMinutes No              Yes            Yes
			plusSeconds/ minusSeconds No              Yes            Yes
			plusNanos/ minusNanos     No              Yes            Yes
		 */
		
		//Working with Periods
		LocalDate start = LocalDate.of(2015, Month.JANUARY, 1);
		LocalDate end = LocalDate.of(2015, Month.MARCH, 30);
		performAnimalEnrichment(start,end);//this method can't be reused!
		System.out.println();
		Period period = Period.ofMonths(1);
		performAnimalEnrichment2(start,end, period);//this method CAN be reused, as can add an arbitrary period of time
		
		//5 ways to create a Periods
		Period annually = Period.ofYears(1); // every 1 year
		Period quarterly = Period.ofMonths(3); // every 3 months
		Period everyThreeWeeks = Period.ofWeeks(3); // every 3 weeks
		Period everyOtherDay = Period.ofDays(2); // every 2 days
		Period everyYearAndAWeek = Period.of(1, 0, 7); // every year and 7 days
		System.out.println(everyYearAndAWeek);
		
		//You CANNOT CHAIN methods when creating Period 
		Period wrong = Period.ofYears(1).ofWeeks(1);//Compiler Warning
		System.out.println(wrong);//P7D
		//It's like writing something like that
		wrong = Period.ofYears(1);
		System.out.println(wrong);//P7D
		wrong = Period.ofWeeks(1);
		System.out.println(wrong);//P7D
		//NB: The 'P' always starts out the String to show it is a period measure.
		//Then come the number of years, number of months, and number of days. 
		//If If any of these are zero, they are omitted.
		System.out.println(Period.of(0, 20, 47));//P20M47D
		System.out.println(Period.ofWeeks(3));//Tricky P21D
		
		//What objects can the 'Period' be used with ?
		LocalDate date2 = LocalDate.of(2015, 1, 20);
		LocalTime time2 = LocalTime.of(6, 15);
		LocalDateTime dateTime2 = LocalDateTime.of(date2, time2);
	    Period period2 = Period.ofMonths(1);
	    System.out.println(date2.plus(period2));// 2015-02-20
	    System.out.println(dateTime2.plus(period2));// 2015-02-20T06:15
	    //System.out.println(time2.plus(period2));//It throws UnsupportedTemporalTypeException
	    
	    //Working with 'Durations'
	    Duration daily = Duration.ofDays(1); // PT24H
	    Duration hourly = Duration.ofHours(1); // PT1H
	    Duration everyMinute = Duration.ofMinutes(1); // PT1M
	    Duration everyTenSeconds = Duration.ofSeconds(10); // PT10S
	    Duration everyMilli = Duration.ofMillis(1); // PT0.001S
	    Duration everyNano = Duration.ofNanos(1); // PT0.000000001S
	    daily = Duration.of(1, ChronoUnit.DAYS);
	    System.out.println(daily);// PT24H
	    daily = Duration.of(1, ChronoUnit.HALF_DAYS);
	    System.out.println(daily);// PT12H
	    hourly = Duration.of(1, ChronoUnit.HOURS);
	    System.out.println(hourly);// PT1H
	    //Every hour and helf can be expressed only in minutes
	    Duration everyHourAndHalf = Duration.of(90, ChronoUnit.MINUTES);
	    System.out.println("One hour and half with Duration: "+everyHourAndHalf);// PT90M
	    everyMinute = Duration.of(1, ChronoUnit.MINUTES);
	    System.out.println(everyMinute);// PT1M
	    everyTenSeconds = Duration.of(10, ChronoUnit.SECONDS);
	    System.out.println(everyTenSeconds);// PT1S
	    everyMilli = Duration.of(1, ChronoUnit.MILLIS);
	    System.out.println(everyMilli);// PT0.001S
	    everyNano = Duration.of(1, ChronoUnit.NANOS);
	    System.out.println(everyNano);// PT0.000000001S
	    
	    System.out.println("\nChronoUnit for Differences ..");
	    //ChronoUnits to Determine how far apart two Temporal values are
	    LocalTime one = LocalTime.of(5, 15);
	    LocalTime two = LocalTime.of(6, 30);
	    LocalDate date3 = LocalDate.of(2016, 1, 20);
	    System.out.println(ChronoUnit.HOURS.between(one, two)); // 1
	    System.out.println(ChronoUnit.MINUTES.between(one, two)); // 75
	    //System.out.println(ChronoUnit.MINUTES.between(one, date3)); // DateTimeException
	    date3 = LocalDate.of(2015, 1, 20);
	    LocalTime time3 = LocalTime.of(6, 15);
	    LocalDateTime dateTime3 = LocalDateTime.of(date3, time3);
	    System.out.println(dateTime3);
	    Duration duration = Duration.ofHours(6);
	    System.out.println(duration);
	    System.out.println("Adding Duration : "+dateTime3.plus(duration));//2015-01-20T12:15
	    System.out.println("Adding Duration : "+time3.plus(duration));//12:15
//	    System.out.println("Adding Duration : "+date3.plus(duration));// UnsupportedTemporalException
	    date3 = LocalDate.of(2015, 1, 20);
	    time3 = LocalTime.of(6, 15);
	    dateTime3 = LocalDateTime.of(date3, time3);
	    System.out.println(dateTime3);
	    duration = Duration.ofHours(23);
	    System.out.println("\n"+duration);
	    System.out.println(dateTime3.plus(duration)); //2015-01-21T05:15 moves forward past the end of the day
	    System.out.println(time3.plus(duration)); //05:15 the time just WRAPS AROUND
//	    System.out.println(date3.plus(duration));//UnsupportedTemporalException because it does not have any time
	    //NB: Period and Duration are not equivalent.
	    //Example or a Period and Duration of the same length
	    LocalDate date4 = LocalDate.of(2015, 5, 25);//2015-05-25
	    Period period4 = Period.ofDays(1);//P1D
	    Duration duration4 = Duration.ofDays(1);//PT24H
	    System.out.println(date4.plus(period4));//2015-05-26
	    //System.out.println(date4.plus(duration4));//Unsupported unit: Seconds
	    //NB: When working with a LocalDate, we are required to use a Period.
	    
	    /*
	     * Table 5. 4 Where to use Duration and Period
						Can Use with Period? 	Can Use with Duration?
			LocalDate 		Yes 					No
			LocalDateTime 	Yes 					Yes
			LocalTime 		No 					Yes
			ZonedDateTime 	Yes 					Yes
	     */
	    
	    //Working With Istants
	    System.out.println("\nWorking With Istants ..");
	    Instant now = Instant.now();
	    System.out.println(now);
	    // do something time consuming
	    for(int i=0; i<1000; i++) { System.out.print(" "+i); }
	    Instant later = Instant.now();
	    System.out.println("\n"+later);
	    Duration duration1 = Duration.between(now, later);
	    System.out.println("\n"+duration1.toMillis());
	    System.out.println();
	    LocalDate date5 = LocalDate.of(2015, 5, 25);
	    LocalTime time5 = LocalTime.of(11, 55, 00);
	    ZoneId zone5 = ZoneId.of("US/Eastern");
	    ZonedDateTime zdt = ZonedDateTime.of(date5, time5, zone5);
	    Instant inst = zdt.toInstant();
	    //The next two lines represent the SAME MOMENT in time!
	    System.out.println(zdt);//2015-05-25T11:55-04:00[US/Eastern]
	    System.out.println(inst);//2015-05-25T11:55:00Z
	    //NB:You can't turn a LocalDateTime into an Instant, but only a ZoneDateTime
	    //Or you can get the Instant from the number of seconds since 1970, if provided.
//	    LocalDate localDate = LocalDate.now(ZoneId.of("GMT+02:30"));
	    //long epochSeconds = Instant.now().getEpochSecond()/*LocalDateTime.now(zone5)*/;
	    long epochSeconds = 1609005509;
	    System.out.println("epochSeconds: "+epochSeconds);
	    Instant instant = Instant.ofEpochSecond(epochSeconds);
	    System.out.println("instant: "+instant); // 2020-12-26T17:58:29Z
	    
	    //Doing math with Instant
	    Instant nextDay = instant.plus(1, ChronoUnit.DAYS);
	    System.out.println("nextDay: "+nextDay);//nextDay: 2020-12-27T17:58:29Z
	    Instant nextHour = instant.plus(1, ChronoUnit.HOURS); 
	    System.out.println("nextHour: "+nextHour);//nextHour: 2020-12-26T18:58:29Z
	    //TRICKY one : Watch from doing math with WEEKS, YEAR AND MONTH: They cannot be added despite they are displayed
	    //Instant nextWeek = instant.plus(1,ChronoUnit.WEEKS);//UnsupportedTemporalTypeException: Unsupported unit: Weeks
	
	    //Accounting for Daylight Savings Time (Fall back and Spring forward )
	    System.out.println("\nDaylight Savings Time (Fall back and Spring forward ) .. ");
	    System.out.println("Spring forward .. ");
	    LocalDate date6 = LocalDate.of(2016, Month.MARCH, 13);
	    LocalTime time6 = LocalTime.of(1, 30);
	    ZoneId zone6 = ZoneId.of("US/Eastern");
	    ZonedDateTime dateTime6 = ZonedDateTime.of(date6, time6, zone6);
	    System.out.println(dateTime6); // 2016–03–13T01:30–05:00[US/Eastern]
	    dateTime6 = dateTime6.plusHours(1);
	    System.out.println(dateTime6); // 2016–03–13T03:30–04:00[US/Eastern]
	    System.out.println("\nFall back in Autumn .. ");
	    LocalDate date7 = LocalDate.of(2016, Month.NOVEMBER, 6);
	    LocalTime time7 = LocalTime.of(1, 30);
	    ZoneId zone7 = ZoneId.of("US/Eastern");
	    ZonedDateTime dateTime7 = ZonedDateTime.of(date7, time7, zone7);
	    System.out.println(dateTime7); // 2016–11–06T01:30–04:00[US/Eastern]
	    dateTime7 = dateTime7.plusHours(1);
	    System.out.println(dateTime7); // 2016–11–06T01:30–05:00[US/Eastern]
	    dateTime7 = dateTime7.plusHours(1);
	    System.out.println(dateTime7); // 2016–11–06T02:30–05:00[US/Eastern]ß
	    //TRICKY: Attempting to create a time that does not exist
	    System.out.println("\nTRICKY: When creating a time that does not exist .. ");
	    LocalDate date8 = LocalDate.of(2016, Month.MARCH, 13);
	    LocalTime time8 = LocalTime.of(2, 30);//IT DOES NOT Actually EXISTS 
	    ZonedDateTime dateTime8 = ZonedDateTime.of(date8, time8, zone7);
	    System.out.println(dateTime8);//2016-03-13T03:30-04:00[US/Eastern]
	    
	    //Reviewing the String class
	}
	
	
	private static void performAnimalEnrichment(LocalDate start, LocalDate end) {
		LocalDate upTo = start;
		while (upTo.isBefore(end)) {
			System.out.println("give new toy: " + upTo);
			upTo = upTo.plusMonths(1);
		}
	}
	
	private static void performAnimalEnrichment2(LocalDate start, LocalDate end, Period period) {
		LocalDate upTo = start;
		while (upTo.isBefore(end)) {
			System.out.println("give new toy: " + upTo);
			upTo = upTo.plus(period);//that's how the method can be reused
		}
	}

}
