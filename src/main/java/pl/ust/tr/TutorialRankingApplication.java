package pl.ust.tr;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.ust.tr.domain.Level;
import pl.ust.tr.domain.Type;
import pl.ust.tr.service.TechnologyService;
import pl.ust.tr.service.TutorialService;

import java.io.IOException;
import java.util.List;


@SpringBootApplication
public class TutorialRankingApplication implements CommandLineRunner {

	@Autowired
	private TutorialService tutorialService;
	@Autowired
	private TechnologyService technologyService;

	public static void main(String[] args) {
		SpringApplication.run(TutorialRankingApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		createDefaultTechnologies();
		this.technologyService.lookup().forEach(technology -> System.out.println(technology));

		persistTutorials();
		System.out.println("Number of created tutorials =" + tutorialService.total());
	}

	private void persistTutorials() throws IOException {

		TutorialFromFile.createTutorialsFromFile().forEach(t -> tutorialService.createTutorial(
				t.title,
				t.description,
				t.link,
				Integer.parseInt(t.price),
				t.duration,
				t.keywords,
				t.technologyName,
				Type.findByLabel(t.type),
				Level.valueOf(t.level)
		));


	}

	private void createDefaultTechnologies(){
		this.technologyService.createTechnology("DO", "Docker");
		this.technologyService.createTechnology("SQ", "Sql");
		this.technologyService.createTechnology("JE", "Jenkins");
		this.technologyService.createTechnology("GR", "Gradle");
		this.technologyService.createTechnology("BA", "Bash");
		this.technologyService.createTechnology("IJ", "Intellij");
		this.technologyService.createTechnology("GE", "General");
		this.technologyService.createTechnology("SB", "Spring Boot");
	}

	static class TutorialFromFile{

		private String technologyName, title, link, description, price, duration, keywords, level, type;

		static List<TutorialFromFile> createTutorialsFromFile() throws IOException {
			return new ObjectMapper()
					.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
					.readValue(TutorialFromFile.class.getResourceAsStream("/tutorials_data.json"),
							new TypeReference<List<TutorialFromFile>>(){});
		}


	}
}
