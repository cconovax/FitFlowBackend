package com.conovax.sexbody.domain.repositories;

import com.conovax.sexbody.domain.entities.User;
import com.conovax.sexbody.domain.entities.GymInfo;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    
    User save(User user);
    
    Optional<User> findById(Long id);
    
    Optional<User> findByEmail(String email);

    Optional<User> findByNumDocument(String numDocument);
    
    Optional<User> findByEmailWithRolesAndPermissions(String email);

    /**
     * Busca por login flexible: email o número de documento.
     */
    Optional<User> findByLoginWithRolesAndPermissions(String login);

    /**
     * Gyms asociados al usuario a través de users_gyms.
     */
    List<GymInfo> findGymsByUserId(Long userId);
    
    List<User> findAll();
    
    boolean existsByEmail(String email);

    boolean existsByNumDocument(String numDocument);
    
    void deleteById(Long id);
    long count();
    boolean existsById(Long id);
}
