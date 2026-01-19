package com.realestatemanagement.controller;
import com.realestatemanagement.dto.response.MessageResponse;
import com.realestatemanagement.dto.response.NotificationResponse;
import com.realestatemanagement.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/notifications")
public class NotificationController {
   private final NotificationService notificationService;
   public NotificationController(NotificationService notificationService) {
       this.notificationService = notificationService;
   }
   // 48. GET /notifications
   @GetMapping
   public List<NotificationResponse> list() {
       return notificationService.list();
   }
   // 49. PATCH /notifications/{id}/read
   @PatchMapping("/{id}/read")
   public MessageResponse markRead(@PathVariable Long id) {
       return notificationService.markRead(id);
   }
   // 50. DELETE /notifications/{id}
   @DeleteMapping("/{id}")
   @ResponseStatus(HttpStatus.NO_CONTENT)
   public void delete(@PathVariable Long id) {
       notificationService.delete(id);
   }
}