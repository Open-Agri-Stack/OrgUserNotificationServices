package com.catalogue.verg.notification.repository;

import com.catalogue.verg.notification.entity.NotificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationTemplateRepository
        extends JpaRepository<NotificationTemplate, Long> {

    Optional<NotificationTemplate>
    findByTemplateCodeAndIsDeletedFalse(
            String templateCode
    );

    boolean existsByTemplateCodeAndIsDeletedFalse(
            String templateCode
    );

    Optional<NotificationTemplate>
    findByIdAndIsDeletedFalse(Long id);

    @Query("""
            SELECT n
            FROM NotificationTemplate n
            WHERE n.isDeleted = false
            AND (
                 LOWER(n.templateName) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(n.templateCode) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(n.templateModule) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(n.notificationChannel) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(n.status) LIKE LOWER(CONCAT('%', :search, '%'))
            )
            """)
    Page<NotificationTemplate> searchTemplates(
            @Param("search") String search,
            Pageable pageable
    );

    Page<NotificationTemplate> findByIsDeletedFalse(
            Pageable pageable
    );

    @Query("""
                SELECT n
                FROM NotificationTemplate n
                WHERE n.isDeleted = false
                  AND (
                        :search = ''
                        OR LOWER(n.templateName) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(n.templateCode) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(n.templateModule) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(n.notificationChannel) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(n.status) LIKE LOWER(CONCAT('%', :search, '%'))
                        OR LOWER(n.receiver) LIKE LOWER(CONCAT('%', :search, '%'))
                      )
                  AND (
                        :status = 'All'
                        OR n.status = :status
                      )
                  AND (
                        :module = 'All'
                        OR n.templateModule = :module
                      )
                  AND (
                        :receiver = 'All'
                        OR n.receiver = :receiver
                      )
            """)
    Page<NotificationTemplate> searchTemplatesAdmin(
            @Param("search") String search,
            @Param("status") String status,
            @Param("module") String module,
            @Param("receiver") String receiver,
            Pageable pageable
    );

    @Query("""
    SELECT DISTINCT n.templateModule
    FROM NotificationTemplate n
    WHERE n.isDeleted = false
      AND n.templateModule IS NOT NULL
      AND TRIM(n.templateModule) <> ''
    ORDER BY n.templateModule
""")
    List<String> findDistinctModules();

    @Query("""
    SELECT DISTINCT n.status
    FROM NotificationTemplate n
    WHERE n.isDeleted = false
      AND n.status IS NOT NULL
      AND TRIM(n.status) <> ''
    ORDER BY n.status
""")
    List<String> findDistinctStatuses();

    @Query("""
    SELECT DISTINCT n.receiver
    FROM NotificationTemplate n
    WHERE n.isDeleted = false
      AND n.receiver IS NOT NULL
      AND TRIM(n.receiver) <> ''
    ORDER BY n.receiver
""")
    List<String> findDistinctReceivers();
}