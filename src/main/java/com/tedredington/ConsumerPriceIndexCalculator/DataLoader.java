package com.tedredington.ConsumerPriceIndexCalculator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final CpiRecordRepository recordRepository;
    private final CsvService csvService;

    @Autowired
    public DataLoader(CpiRecordRepository recordRepository, CsvService csvService) {
        this.recordRepository = recordRepository;
        this.csvService = csvService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Read CSV file and save records to database
        List<CpiRecord> records = csvService.readCsv();
        logger.info("Importing records and saving to H2 DB. Record Count: %d".formatted(records.stream().count()));
        recordRepository.saveAll(records);
    }
}
