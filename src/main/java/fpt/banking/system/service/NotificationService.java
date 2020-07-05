package fpt.banking.system.service;

import java.util.List;

import fpt.banking.system.model.Notification;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.NotificationsResponse;

public interface NotificationService {
	public void saveNotification(String message, User user);

	public NotificationsResponse getNotificationsWithPage(long userId, int page);
	
	public int getTotalUnreadMessage(long userId);
	
	public void markAsRead(long userId);
}
