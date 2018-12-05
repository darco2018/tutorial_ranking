package pl.ust.tr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}


/*




	private void createDefaultSkills(){
		this.skillService.createSkill("DO", "Docker");
		this.skillService.createSkill("SQ", "Sql");
		this.skillService.createSkill("JE", "Jenkins");
		this.skillService.createSkill("GR", "Gradle");
		this.skillService.createSkill("BA", "Bash");
		this.skillService.createSkill("IJ", "Intellij");
		this.skillService.createSkill("GE", "General");
		this.skillService.createSkill("SB", "Spring Boot");
	}



	*/
}
