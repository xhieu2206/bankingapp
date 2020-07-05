package fpt.banking.system.dao;

import java.util.List;

import fpt.banking.system.model.Notification;
import fpt.banking.system.model.User;

public interface NotificationDAO {
	
	public void saveNotification(String message, User user);

	public List<Notification> getNotifications(long userId, int page);
	
	public int getTotalUnreadMessage(long userId);
	
	public int getTotalNotifications(long userId);
	
	public void markAsRead(long userId);
}
