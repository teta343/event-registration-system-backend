package kigali.event.rw.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.event.rw.domain.Certificate;
import kigali.event.rw.service.CertificateService;

@RestController
@RequestMapping(value = "/api/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveCertificate(@RequestParam UUID registrationId, @RequestBody Certificate certificate) {
        String returnedMessage = certificateService.saveCertificate(registrationId, certificate);

        if (returnedMessage.equals("Certificate saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        } else if (returnedMessage.equals("Certificates are only for ATTENDED registrations")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/participant/{email}")
    public ResponseEntity<?> getCertificatesByParticipantEmail(@PathVariable String email) {
        return new ResponseEntity<>(certificateService.getCertificatesByParticipantEmail(email), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteCertificate(@PathVariable UUID id) {
        String returnedMessage = certificateService.deleteCertificate(id);

        if (returnedMessage.equals("Certificate deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

}
