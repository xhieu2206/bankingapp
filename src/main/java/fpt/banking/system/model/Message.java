package fpt.banking.system.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "message")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Message {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;

	@Column(name = "created_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "message_detail")
	private String messageDetail;

	@Column(name = "message_type")
	private boolean messgaeType;
	// Relationship -------------------------
	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.DETACH })
	@JoinColumn(name = "conversation_id")
	@JsonIgnore
	private Conversation conversation;

	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.DETACH })
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	// --------------------------------------
	
	// Constructor --------------------------
	public Message() {
	}

	public Message(Date createdAt, String messageDetail) {
		this.createdAt = createdAt;
		this.messageDetail = messageDetail;
	}

	public Message(Long id, Date createdAt, String messageDetail) {
		this.id = id;
		this.createdAt = createdAt;
		this.messageDetail = messageDetail;
	}
	// --------------------------------------

	// GETTER/SETTER ------------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getMessageDetail() {
		return messageDetail;
	}

	public void setMessageDetail(String messageDetail) {
		this.messageDetail = messageDetail;
	}

	public boolean isMessgaeType() {
		return messgaeType;
	}

	public void setMessgaeType(boolean messgaeType) {
		this.messgaeType = messgaeType;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	// --------------------------------------
}
