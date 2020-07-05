package fpt.banking.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fpt.banking.system.dao.NotificationDAO;
import fpt.banking.system.model.User;
import fpt.banking.system.payload.NotificationsResponse;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationDAO notificationDAO;

	@Override
	@Transactional
	public void saveNotification(String message, User user) {
		notificationDAO.saveNotification(message, user);
	}

	@Override
	@Transactional
	public NotificationsResponse getNotificationsWithPage(long userId, int page) {
		NotificationsResponse results = new NotificationsResponse();
		results.setPageNumber(page);
		results.setTotalCount(notificationDAO.getTotalNotifications(userId));
		System.out.println(results.getTotalCount());
		int totalPage = (int) Math.ceil(notificationDAO.getTotalNotifications(userId) / 5);
		if (notificationDAO.getTotalNotifications(userId) % 5 > 0) {
			totalPage ++;
		}
		results.setTotalPage(totalPage);
		System.out.println(results.getTotalPage());
		results.setItems(notificationDAO.getNotifications(userId, page));
		results.setPageSize(results.getItems().size());
		return results;
	}

	@Override
	@Transactional
	public int getTotalUnreadMessage(long userId) {
		return notificationDAO.getTotalUnreadMessage(userId);
	}

	@Override
	@Transactional
	public void markAsRead(long userId) {
		notificationDAO.markAsRead(userId);
	}

}
