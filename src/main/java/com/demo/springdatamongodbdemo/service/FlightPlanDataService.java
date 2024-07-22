package com.demo.springdatamongodbdemo.service;

import com.demo.springdatamongodbdemo.entities.FlightPlan;
import com.demo.springdatamongodbdemo.factories.AircraftFactory;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightPlanDataService {
    private final MongoOperations mongoOperations;

    public void insertInitialFlightPlans() {
        insertSingleDocument();
        insertMultipleDocument();
    }

    public FlightPlan findById(String id) {
        return mongoOperations.findById(id, FlightPlan.class);
    }

    public List<FlightPlan> findInternationalCrossingFrance() {
        Criteria isInternational = where("isInternational").is(true);
        Criteria crossingFrance = where("crossedCountries").in("France");
        Criteria internationalCrossingFrance = new Criteria()
                .andOperator(isInternational, crossingFrance);
        Query query = new Query(internationalCrossingFrance);
        return mongoOperations.find(query, FlightPlan.class);
    }

    public List<FlightPlan> findFirstTwoFlightsWhichLastBetweenOneAndThreeHours() {
        Criteria flightDurationBetweenOneNThree = where("flightDuration")
                .gte(60)
                .lte(180);
        Query query = new Query(flightDurationBetweenOneNThree)
                .with(PageRequest.of(0, 2));
        return mongoOperations.find(query, FlightPlan.class);
    }

    public List<FlightPlan> findBoeingFlightsAndOrderBySeatCapacity() {
        Criteria withBoeing = where("aircraft.model").regex("Boeing");
        Query query = new Query(withBoeing)
                .with(Sort.by("aircraft.seatCapacity").descending());
        return mongoOperations.find(query, FlightPlan.class);
    }

    public List<FlightPlan> findByFullTextSearch(String value) {
        TextCriteria searchCriteria = TextCriteria.forDefaultLanguage()
                .matching(value);
        TextQuery textQuery = TextQuery.queryText(searchCriteria)
                .sortByScore();
        return mongoOperations.find(textQuery, FlightPlan.class);
    }

    public void incrementDepartureTime(String id, LocalDateTime newDepartureTime) {
//        // non-efficient way
//        FlightPlan byId = findById(id);
//        byId.setDepartureDateTime(newDepartureTime);
//        byId = mongoOperations.save(byId);
//        log.info("Updated:{}", byId.getId());
//        return byId;
        // efficient way
        Query query = new Query(where("id").is(id));
        Update update = new Update().set("departureDateTime", newDepartureTime);
        UpdateResult updateRes = mongoOperations.updateFirst(query, update, FlightPlan.class);
        log.info("Updated {}", updateRes);
    }

    public void changeDurationForFlightsInParis(int minutesToAdd) {
        Query query = new Query(where("departure").regex("Paris"));
        Update update = new Update().inc("flightDuration", minutesToAdd);
        UpdateResult updateRes = mongoOperations.updateMulti(query, update, FlightPlan.class);
        log.info("Updated {}", updateRes);
    }

    private void insertMultipleDocument() {
        // Insert a list of documents
        var parisToNice = new FlightPlan(null,
                "Paris, France",
                "Nice, France",
                LocalDateTime.of(2023, 7, 3, 9, 0),
                100,
                List.of("France"),
                false,
                AircraftFactory.buildEmbraerE175()
        );

        var istanbulToPhuket = new FlightPlan(null,
                "Istanbul, Turkey",
                "Phuket, Thailand",
                LocalDateTime.of(2023, 12, 15, 22, 50),
                600,
                List.of("Turkey", "Iran", "Pakistan", "India", "Thailand"),
                true,
                AircraftFactory.buildAirbusA350()
        );

        var istanbulToBucharest = new FlightPlan(null,
                "Istanbul, Turkey",
                "Bucharest, Romania",
                LocalDateTime.of(2023, 12, 15, 21, 30),
                600,
                List.of("Turkey", "Romania"),
                true,
                AircraftFactory.buildBoeing737()
        );

        var berlinToNewYork = new FlightPlan(null,
                "Berlin, Germany",
                "New York, United States",
                LocalDateTime.of(2023, 9, 5, 15, 0),
                420,
                List.of("Germany", "England", "United States"),
                true,
                AircraftFactory.buildBoeing747()
        );

        var viennaToBucharest = new FlightPlan(null,
                "Vienna, Austria",
                "Bucharest, Romania",
                LocalDateTime.of(2023, 8, 1, 11, 30),
                75,
                List.of("Austria", "Hungary", "Romania"),
                true,
                AircraftFactory.buildBoeing737()
        );

        var flightPlans = List.of(
                parisToNice,
                viennaToBucharest,
                berlinToNewYork,
                istanbulToPhuket,
                istanbulToBucharest
        );
        Collection<FlightPlan> insertedDocuments = mongoOperations.insert(flightPlans, FlightPlan.class);
        log.info("Inserted multiple documents: {}", insertedDocuments);
    }

    private void insertSingleDocument() {
        var parisToLondon = new FlightPlan(null, "Paris", "London",
                LocalDateTime.of(2023, 6, 1, 20, 15), 90,
                List.of("France", "England"), true, AircraftFactory.buildBoeing737());
        parisToLondon = mongoOperations.insert(parisToLondon);
        log.info("Inserted single document: {}", parisToLondon);
    }
}
