import com.oreilly.reactiveofficers.dao.OfficerRepository;
import com.oreilly.reactiveofficers.entities.Officer;
import com.oreilly.reactiveofficers.entities.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class OfficerInit implements ApplicationRunner {
    private OfficerRepository dao;

    @Autowired
    public OfficerInit(OfficerRepository dao) {
        this.dao = dao;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        dao.deleteAll()
            .thenMany(Flux.just(new Officer(Rank.CAPTAIN, "Jair", "Aviles"),
                    new Officer(Rank.CAPTAIN, "Aline", "Herculano"),
                    new Officer(Rank.CAPTAIN, "Renato", "Rosetti"),
                    new Officer(Rank.CAPTAIN, "Carlos Alberto", "Hernandez"),
                    new Officer(Rank.CAPTAIN, "Mariana", "Izquierdo")))
            .flatMap(dao::save)
            .then()
            .doOnEach(System.out::println)
        .block();
    }
}
