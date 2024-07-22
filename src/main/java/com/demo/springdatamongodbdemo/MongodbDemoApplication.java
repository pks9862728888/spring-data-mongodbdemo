package com.demo.springdatamongodbdemo;

import com.demo.springdatamongodbdemo.service.FlightPlanDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class MongodbDemoApplication implements CommandLineRunner {
	private final FlightPlanDataService flightPlanDataService;

	public static void main(String[] args) {
		SpringApplication.run(MongodbDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		flightPlanDataService.insertInitialFlightPlans();

		// Search
		log.info("Find by id: {}", flightPlanDataService.findById("669d42d5f3f5dc32f34a7ab8"));
		log.info("Find international flights crossing france: {}", flightPlanDataService.findInternationalCrossingFrance());
		log.info("Find 2 flights between 1 & 3 hours: {}", flightPlanDataService.findFirstTwoFlightsWhichLastBetweenOneAndThreeHours());
		log.info("Find Boeing flights order by seat capacity: {}", flightPlanDataService.findBoeingFlightsAndOrderBySeatCapacity());
		log.info("Find flights having word Turkey: {}", flightPlanDataService.findByFullTextSearch("Turkey"));

		// update
		flightPlanDataService.incrementDepartureTime("669d4251a73145783bb01994", LocalDateTime.now());
		flightPlanDataService.changeDurationForFlightsInParis(10);
	}
}
