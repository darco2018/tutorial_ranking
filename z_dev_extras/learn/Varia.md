
### ----- open another tomcat instance on a different port

  java -jar -Dserver.port=9090 target/tutorial_ranking-0.0.1-SNAPSHOT.jar

### ----- QUERY METHODS IN SPRING DATA - SIGNATURE RULES  (fail fast at compile)
- return type
- start with findBy
- entity atribute name (camelCase)
- parameter w/h datatype of the entity attribute
Remember to use list if result is more than one.
or
@Query("Select t from Tour t where t.tourPackage.code = ?1 and t.difficulty = ?2 and t.region = ?3 and t.price <= ?4)
List<Tour> lookupTour(String code, Difficulty difficulty, Region region, Integer maxPrice);
eqivalent to
List<Tour> findByTourPackageCodeAndDifficultyAndRegionAndPriceLessThan(String code, Difficulty difficulty, Region region, Integer maxPrice);

-------------------------------------------------------------
http://localhost:8080/technologies/search
shows custom methods in repo:
 "_links": {
        "findByName": {
            "href": "http://localhost:8080/technologies/search/findByName"

-------------------------------------------------------------------

Coding%20Tutorials - space is %20

--------------------------PAGING AND SORTING----------------------------

public interface TutorialRepository extends PagingAndSortingRepository<Tutorial, Integer> {

    //List<Tutorial> findByTechnologyCode(@Param("code") String code);
    Page<Tutorial> findByTechnologyCode(@Param("code") String code, Pageable pageable);
}

size (size of pages) default=20
page (0th to ...)  default=0
sort           sort=title  default=entity id
sort direction  asc or desc    default=asc

http://localhost:8080/tutorials?size=3&page=1&sort=title,asc
http://localhost:8080/tutorials/search/findByTechnologyCode?code=GE&size=3&sort=title,asc
at the end of page
"_links": {
        "first": {
            "href": "http://localhost:8080/tutorials/search/findByTechnologyCode?code=GE&page=0&size=3&sort=title,asc"
        },
        "self": {
            "href": "http://localhost:8080/tutorials/search/findByTechnologyCode?code=GE&size=3&sort=title,asc"
        },
        "next": {
            "href": "http://localhost:8080/tutorials/search/findByTechnologyCode?code=GE&page=1&size=3&sort=title,asc"
        },
        "last": {
            "href": "http://localhost:8080/tutorials/search/findByTechnologyCode?code=GE&page=1&size=3&sort=title,asc"
        }
    },
 "page": {
        "size": 3,
        "totalElements": 4,
        "totalPages": 2,
        "number": 0
    }

-------- CONTROLLING API EXPOSURE -------------------------

@RepositoryRestResource(exported=false) Class level

@RestResource(exported=false)  MEthod level, eg
    @Override
    @RestResource(exported=false)
    <S extends Technology//> S save(S s);

    POST with this will give   405 Method Not Allowed
--------- OVERRIDE ENDPOINT NAME-------------------------------------------------------
@RepositoryRestResource(collectionResourceRel = "technology-tool", path = "technology-tool")
public interface TechnologyRepository extends CrudRepository<Technology, String> {}
---- USE SPRING WEB MVC OVER SPRING DATA REST--------------------------------
- not using Spring Data
- API launczes an algorithm
- hide internal Data Model
- require business service layer

------------------------ @EmbeddedId --------------------------------
denotes a composite primary key that is an embeddable class. The embeddable class must be annotated as Embeddable.
public class TutorialRanking {

    @EmbeddedId
    private TutorialRakingPk pk;

@Embeddable
public class TutorialRakingPk {

    @ManyToOne
    private Tutorial tutorial;

    @Column(insertable = false, updatable = false, nullable = false)
    private Integer userId;

------------- DEFAULT CONSTRUCTOR --------------------------
In Hibernate we need a default NON_PRIVATE constructor?!
when I use @Entity, @Embedded, @Embeddable

-------------- SPRING BOOT 2 vs SPRING BOOT 1 --------------
findBy queries return Optional
CrudRepository<T, ID> zamiast <T, ID extends Serializable>
saveAll << save  (Iterable)
findById << findOne
existsById << exists
findAllById << findAll (Iterable)
deleteById << delete
deleteAll delete (Iterable)

-------------- OPTIONAL -----------------
skillRepo.findByName("blah").orElseThrow(()->new RuntimeException("Blah"))
                       .orElse(new Skill("bla", "bla"));
                       .get() // throws exception if "blah" not found
                       .ifPresent(skill -> System.out.println(skill.getCode());
                       .isPresent() // boolean
                       .filter(skill -> skill.getCode().equals("DO")).isPresent();

-------------- LAMBDAS ---------------
findAll().forEach(System.out :: prinln
------------- REPOSITORY INTERFACES ----------------------
JPARepository extends CRUDRepository extends Repository
SortingAndPAginRepository extends CRUD
------------------------- JPA REPO additions -----------
JPARepository dorzuca:
- flush()
- saveAndFlush()
- deleteInBatch(Iterable)
- deleteAllInBatch())
BEnefits of using JPARepository:
- No need to access EntityManagerFactory when you want to flush data immediately
@PersistanceUnit
EntityManagerFActory emf;
- helps to access other data repositories (eg MongoDb)
------------------------- CONVERTER ------------------------------------
@Converter(autoApply = true)
public class MediumConverter implements AttributeConverter<Medium, String> {

    @Override
    public String convertToDatabaseColumn(Medium medium) {
        return medium.getLabel();
    }

    @Override
    public Medium convertToEntityAttribute(String label) {
        return Medium.findByLabel(label);
    }
}

----------------------------------------@Conveerter , ConverterAttribute-----------------------------------------------------------
@Converter cant be used with @Enumerated

 //@Enumerated(EnumType.STRING)
    private Medium medium;
---------------------------------------------------