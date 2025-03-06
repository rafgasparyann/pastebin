package am.logiclab.pastebin.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

//    void sendTextMessage(String message);

}
