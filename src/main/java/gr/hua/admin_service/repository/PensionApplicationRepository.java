package gr.hua.admin_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.hua.admin_service.model.PensionApplication;

public interface PensionApplicationRepository extends JpaRepository<PensionApplication, Long> {
}