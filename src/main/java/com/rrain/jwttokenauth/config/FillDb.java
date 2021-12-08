package com.rrain.jwttokenauth.config;

import com.rrain.jwttokenauth.entity.User;
import com.rrain.jwttokenauth.repo.UserRepo;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;

@CommonsLog
@Configuration
public class FillDb {



    /*private <T,R extends JpaRepository<T,?>,K> void create(Iterable<T> documents, R repo, Function<K,T> findFun, Function<T,K> keyExtractor){
        for(var document : documents){
            var docInDB = findFun.apply(keyExtractor.apply(document));
            if (docInDB == null) log.info("PRELOADING: new " + repo.save(document));
            else log.info("PRELOADING: already exists " + docInDB);
        }
    }*/

    private <E,R extends JpaRepository<E,?>,K> void create(Iterable<E> entities, R repo, Function<K, Optional<E>> findFun, Function<E,K> keyExtractor, BiPredicate<E,E> eq){
        for(var entity : entities){
            var dbEntityOpt = findFun.apply(keyExtractor.apply(entity));
            if (dbEntityOpt.isEmpty()){
                log.info("PRELOADING: new " + repo.save(entity));
            } else {
                var dbEntity = dbEntityOpt.get();
                if (!eq.test(dbEntity,entity)){
                    log.info("PRELOADING: replace with " + repo.save(entity));
                } else {
                    log.info("PRELOADING: already exists " + dbEntity);
                }
            }
        }
    }




    @Bean
    CommandLineRunner initUsersDB(UserRepo repo){
        return args -> {
            create(
                Set.of(new User(
                    "admin",
                    "$2y$12$wbZM9rIYHXQ2JxCYsFuRbOJZxTNFYf2yj9t9uZ/xymlIvVZvIxyEK", // пароль 100
                    "ADMIN",
                    "admin"
                )),
                repo,
                repo::findUserByUsername,
                User::getUsername,
                User::fullyEq
            );
        };
    }


}
