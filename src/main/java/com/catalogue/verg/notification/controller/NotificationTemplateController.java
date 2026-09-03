package com.catalogue.verg.notification.controller;

import com.catalogue.verg.core.dto.CustomResponse;
import com.catalogue.verg.core.dto.NotificationTemplateFilterRequest;
import com.catalogue.verg.core.dto.NotificationTemplateRequest;
import com.catalogue.verg.notification.service.NotificationTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification-templates")
public class NotificationTemplateController {

    private final NotificationTemplateService notificationTemplateService;

    public NotificationTemplateController(
            NotificationTemplateService notificationTemplateService) {
        this.notificationTemplateService = notificationTemplateService;
    }

    @PostMapping
    public ResponseEntity<CustomResponse> createTemplate(
            @RequestBody NotificationTemplateRequest request) {

        CustomResponse response =
                notificationTemplateService.createTemplate(request);

        return ResponseEntity
                .status(response.getResponseCode())
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse> updateTemplate(
            @PathVariable Long id,
            @RequestBody NotificationTemplateRequest request) {

        CustomResponse response =
                notificationTemplateService.updateTemplate(id, request);

        return ResponseEntity
                .status(response.getResponseCode())
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> deleteTemplate(
            @PathVariable Long id) {

        CustomResponse response =
                notificationTemplateService.deleteTemplate(id);

        return ResponseEntity
                .status(response.getResponseCode())
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> getTemplateById(
            @PathVariable Long id) {

        CustomResponse response =
                notificationTemplateService.getTemplateById(id);

        return ResponseEntity
                .status(response.getResponseCode())
                .body(response);
    }

    @GetMapping
    public ResponseEntity<CustomResponse> getTemplates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {

        CustomResponse response =
                notificationTemplateService.getTemplates(
                        page,
                        size,
                        search
                );

        return ResponseEntity
                .status(response.getResponseCode())
                .body(response);
    }

    @PostMapping("/search")
    public ResponseEntity<CustomResponse> getTemplates(
            @RequestBody NotificationTemplateFilterRequest request) {

        CustomResponse response =
                notificationTemplateService.getTemplatesAdmin(request);

        return ResponseEntity
                .status(response.getResponseCode())
                .body(response);
    }

    @GetMapping("/modules")
    public ResponseEntity<CustomResponse> getModules() {

        CustomResponse response =
                notificationTemplateService.getModules();

        return ResponseEntity
                .status(response.getResponseCode())
                .body(response);
    }

    @GetMapping("/statuses")
    public ResponseEntity<CustomResponse> getStatuses() {

        CustomResponse response =
                notificationTemplateService.getStatuses();

        return ResponseEntity
                .status(response.getResponseCode())
                .body(response);
    }

    @GetMapping("/receivers")
    public ResponseEntity<CustomResponse> getReceivers() {

        CustomResponse response =
                notificationTemplateService.getReceivers();

        return ResponseEntity
                .status(response.getResponseCode())
                .body(response);
    }
}