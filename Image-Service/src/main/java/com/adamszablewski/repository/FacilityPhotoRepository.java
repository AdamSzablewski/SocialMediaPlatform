package com.adamszablewski.repository;

import com.adamszablewski.model.FacilityPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FacilityPhotoRepository extends JpaRepository<FacilityPhoto, Long> {

    void deleteAllByUserId(long id);
    Optional<FacilityPhoto> findByFacilityId(long id);
    void deleteByFacilityId(long id);

    void deleteAllByFacilityId(long id);
}
