package gr.hua.admin_service.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.admin_service.model.ApplicationStatus;
import gr.hua.admin_service.model.PensionApplication;
import gr.hua.admin_service.repository.PensionApplicationRepository;
import gr.hua.admin_service.service.EmailService;

@RestController
@RequestMapping("/applications")
public class PensionApplicationController {

    @Autowired
    private PensionApplicationRepository pensionApplicationRepository;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public List<PensionApplication> getAllApplications() {
        return pensionApplicationRepository.findAll();
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<PensionApplication> approveApplication(@PathVariable Long id) {
        return pensionApplicationRepository.findById(id).map(application -> { application.setStatus(ApplicationStatus.APPROVED); application.setDecidedAt(LocalDateTime.now()); var saved = pensionApplicationRepository.save(application); emailService.sendStatusChangeEmail(saved.getCitizenUsername(), saved.getStatus().name(), saved.getDecisionNote()); return ResponseEntity.ok(saved); }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<PensionApplication> rejectApplication(@PathVariable Long id, @RequestParam(required = false) String decisionNote) {
        return pensionApplicationRepository.findById(id).map(application -> { application.setStatus(ApplicationStatus.REJECTED); application.setDecidedAt(LocalDateTime.now()); application.setDecisionNote(decisionNote); var saved = pensionApplicationRepository.save(application); emailService.sendStatusChangeEmail(saved.getCitizenUsername(), saved.getStatus().name(), saved.getDecisionNote()); return ResponseEntity.ok(saved); }).orElse(ResponseEntity.notFound().build());
    }
}