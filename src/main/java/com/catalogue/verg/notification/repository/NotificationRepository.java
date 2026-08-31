package com.catalogue.verg.notification.repository;

import com.catalogue.verg.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    @Modifying
    @Query("""
        UPDATE Notification n
        SET n.markedAsRead = true,
            n.updatedAt = CURRENT_TIMESTAMP
        WHERE n.id = :notificationId
          AND n.userId = :userId
          AND n.notificationType = 'PORTAL'
          AND n.cleared = false
        """)
    int markAsRead(
            @Param("notificationId") Long notificationId,
            @Param("userId") String userId
    );

    @Modifying
    @Query("""
        UPDATE Notification n
        SET n.markedAsRead = true,
            n.updatedAt = CURRENT_TIMESTAMP
        WHERE n.userId = :userId
          AND n.notificationType = 'PORTAL'
          AND n.cleared = false
          AND n.markedAsRead = false
        """)
    int markAllAsRead(
            @Param("userId") String userId
    );

    @Modifying
    @Query("""
        UPDATE Notification n
        SET n.cleared = true,
            n.updatedAt = CURRENT_TIMESTAMP
        WHERE n.id = :notificationId
          AND n.userId = :userId
          AND n.notificationType = 'PORTAL'
          AND n.cleared = false
        """)
    int clearNotification(
            @Param("notificationId") Long notificationId,
            @Param("userId") String userId
    );

    @Modifying
    @Query("""
        UPDATE Notification n
        SET n.cleared = true,
            n.updatedAt = CURRENT_TIMESTAMP
        WHERE n.userId = :userId
          AND n.notificationType = 'PORTAL'
          AND n.cleared = false
        """)
    int clearAllNotifications(
            @Param("userId") String userId
    );

    @Query("""
            SELECT n
            FROM Notification n
            WHERE n.userId = :userId
              AND n.notificationType = 'PORTAL'
              AND n.cleared = false
            ORDER BY n.createdAt DESC
            """)
    Page<Notification> findPortalNotifications(
            @Param("userId") String userId,
            Pageable pageable
    );

    @Query("""
            SELECT n
            FROM Notification n
            WHERE n.userId = :userId
              AND n.notificationType = 'PORTAL'
              AND n.cleared = false
              AND n.markedAsRead = :readStatus
            ORDER BY n.createdAt DESC
            """)



    Page<Notification> findPortalNotificationsByReadStatus(
            @Param("userId") String userId,
            @Param("readStatus") Boolean readStatus,
            Pageable pageable
    );
}
