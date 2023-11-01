import br.edu.unifalmg.repository.ChoreRepository;
import br.edu.unifalmg.repository.impl.FileChoreRepository;
import br.edu.unifalmg.repository.impl.MySQLChoreRepository;
import br.edu.unifalmg.service.ChoreService;

import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;

public class TodoApplication {



    public static void main(String[] args) throws IOException {
        // ChoreRepository repository =  new FileChoreRepository();
        ChoreRepository repository =  new MySQLChoreRepository();
        ChoreService service = new ChoreService(repository);
        service.loadChores();
        //service.addChore("Testing write on database feature", LocalDate.now());
        service.addChore("Chore 02", LocalDate.now().plusDays(4));



        System.out.println("Tamanho da lista de chores:  " + service.getChores().size());
        // service.deleteChore9"Chore #02", LocalDate.now()plusDays(8);
        // *service.saveChores();
    }
}
