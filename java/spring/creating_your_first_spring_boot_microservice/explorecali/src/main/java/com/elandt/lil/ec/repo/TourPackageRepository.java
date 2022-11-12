package com.elandt.lil.ec.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.elandt.lil.ec.domain.TourPackage;

// Allows overriding the default URL path
@RepositoryRestResource(collectionResourceRel = "packages", path = "packages")
public interface TourPackageRepository extends CrudRepository<TourPackage, String> {
    // By extending CrudRepository a number of db operations are provided out-of-the-box

    /**
     * find a Tour Package by name
     *
     * @param name name of Tour Package to find
     * @return Optional of Tour Package
     */
    // Implementation is handled by Spring through introspection and reflection as long as
    // rules are followed for declaring methods, and correctly map entity properties to the method signature
    Optional<TourPackage> findByName(@Param("name") String name);

    @Override
    // Removes access to these endpoints from outside our code.
    // Securing these could also be handled through Spring Security, but it's outside the scope of this course.
    @RestResource(exported = false)
    void delete(TourPackage entity);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends TourPackage> entities);

    @Override
    @RestResource(exported = false)
    void deleteAllById(Iterable<? extends String> ids);

    @Override
    @RestResource(exported = false)
    void deleteById(String id);

    @Override
    @RestResource(exported = false)
    <S extends TourPackage> S save(S entity);

    @Override
    @RestResource(exported = false)
    <S extends TourPackage> Iterable<S> saveAll(Iterable<S> entities);
}
