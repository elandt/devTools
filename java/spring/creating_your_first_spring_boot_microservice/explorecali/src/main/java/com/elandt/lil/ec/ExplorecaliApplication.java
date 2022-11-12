package com.elandt.lil.ec;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.elandt.lil.ec.domain.Difficulty;
import com.elandt.lil.ec.domain.Region;
import com.elandt.lil.ec.service.TourPackageService;
import com.elandt.lil.ec.service.TourService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class ExplorecaliApplication implements CommandLineRunner {

	private static final Logger LOGGER = LogManager.getLogger(ExplorecaliApplication.class);

	private final TourPackageService tourPackageService;

	private final TourService tourService;

	public static void main(String[] args) {
		SpringApplication.run(ExplorecaliApplication.class, args);
	}

	public ExplorecaliApplication(TourPackageService tourPackageService, TourService tourService) {
		this.tourPackageService = tourPackageService;
		this.tourService = tourService;
	}

	@Override
	public void run(String... args) throws Exception {
		// Create initial Tour Packages
		createTourPackages();
		long numOfPackages = tourPackageService.total();

		// Create initial Tours - using relative path for fileName because of the project being nested within devtools
		createTours("java/spring/creating_your_first_spring_boot_microservice/explorecali/ExploreCalifornia.json");
		long numOfTours = tourService.total();

		LOGGER.info("Loaded {} tour packages, and {} tours.", numOfPackages, numOfTours);
	}

	/**
	 * Initialize all known Tour Packages
	 */
	private void createTourPackages() {
		tourPackageService.createTourPackage("BC", "Backpack Cal");
        tourPackageService.createTourPackage("CC", "California Calm");
        tourPackageService.createTourPackage("CH", "California Hot springs");
        tourPackageService.createTourPackage("CY", "Cycle California");
        tourPackageService.createTourPackage("DS", "From Desert to Sea");
        tourPackageService.createTourPackage("KC", "Kids California");
        tourPackageService.createTourPackage("NW", "Nature Watch");
        tourPackageService.createTourPackage("SC", "Snowboard Cali");
        tourPackageService.createTourPackage("TC", "Taste of California");
	}

	/**
	 * Create tour entities from an external file
	 *
	 * @param fileName external file name contianing data to import
	 * @throws IOException
	 */
	private void createTours(String fileName) throws IOException {
		TourFromFile.read(fileName).forEach(importedTour ->
			tourService.createTour(importedTour.getTitle(),
					importedTour.getDescription(),
					importedTour.getBlurb(),
					importedTour.getPrice(),
					importedTour.getLength(),
					importedTour.getBullets(),
					importedTour.getKeywords(),
					importedTour.getPackageType(),
					importedTour.getDifficulty(),
					importedTour.getRegion()));
	}

	/**
	 * Helper class for importing tours from ExploreCalifornia.json
	 */
	private static class TourFromFile {
		private String packageType;
		private String title;
		private String description;
		private String blurb;
		private String price;
		private String length;
		private String bullets;
		private String keywords;
		private String difficulty;
		private String region;

		//reader
        static List<TourFromFile> read(String fileToImport) throws IOException {
            return new ObjectMapper().setVisibility(FIELD, ANY).
                    readValue(new FileInputStream(fileToImport), new TypeReference<List<TourFromFile>>() {});
        }

		protected TourFromFile() {
			// Default constructor
		}

		public String getPackageType() {
			return packageType;
		}

		public String getTitle() {
			return title;
		}

		public String getDescription() {
			return description;
		}

		public String getBlurb() {
			return blurb;
		}

		public Integer getPrice() {
			return Integer.parseInt(price);
		}

		public String getLength() {
			return length;
		}

		public String getBullets() {
			return bullets;
		}

		public String getKeywords() {
			return keywords;
		}

		public Difficulty getDifficulty() {
			return Difficulty.valueOf(difficulty);
		}

		public Region getRegion() {
			return Region.findByLabel(region);
		}
	}
}
