package ec.edu.ups.icc.fundamentos01.users.repositories;

import ec.edu.ups.icc.fundamentos01.users.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByIdAndDeletedFalse(Long id);

    Optional<UserEntity> findByIdAndDeleted(Long id, boolean delete);

    boolean existsByIdAndDeletedFalse(Long id);

}
