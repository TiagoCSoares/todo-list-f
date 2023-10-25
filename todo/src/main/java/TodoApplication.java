import br.edu.unifalmg.repository.ChoreRepository;
import br.edu.unifalmg.repository.impl.FileChoreRepository;
import br.edu.unifalmg.service.ChoreService;

import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;

public class TodoApplication {



    public static void main(String[] args) throws IOException {
        ChoreRepository repository =  new FileChoreRepository();
        ChoreService service = new ChoreService(repository);
        service.loadChores();
        service.addChore("Testing 'write on file' feature", LocalDate.now());
        System.out.println("Tamanho da lista de chores:  " + service.getChores().size());
        service.saveChores();
    }
}
