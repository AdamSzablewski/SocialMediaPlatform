package com.adamszablewski.repository;

import com.adamszablewski.model.PortfolioPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioPhoto, Long> {

    Optional<PortfolioPhoto> findByFacility(long facilityId);

    void deleteAllByUserId(long userId);
}
