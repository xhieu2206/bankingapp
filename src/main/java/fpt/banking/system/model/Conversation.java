package fpt.banking.system.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "conversation")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Conversation {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "title")
	private String title;

	@Column(name = "created_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "read_from_questioner")
	private boolean readFromQuestioner;

	@Column(name = "read_from_respondent")
	private boolean readFromRespondent;

	// Relationship -------------------------
	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.DETACH })
	@JoinColumn(name = "questioner_id")
	@JsonIgnore
	private User questioner;

	@ManyToOne(
			fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST,
					CascadeType.MERGE,
					CascadeType.REFRESH,
					CascadeType.DETACH })
	@JoinColumn(name = "respondent_id")
	@JsonIgnore
	private User respondent;

	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "conversation",
			cascade = {
				CascadeType.PERSIST,
				CascadeType.MERGE,
				CascadeType.REFRESH,
				CascadeType.DETACH})
	@JsonIgnore
	private List<Message> messages;
	// --------------------------------------
	
	// Constructor --------------------------
	public Conversation() {
	}

	public Conversation(Date createdAt, String title, boolean readFromQuestioner, boolean readFromRespondent) {
		this.createdAt = createdAt;
		this.title = title;
		this.readFromQuestioner = readFromQuestioner;
		this.readFromRespondent = readFromRespondent;
	}

	public Conversation(Long id, Date createdAt, String title, boolean readFromQuestioner, boolean readFromRespondent) {
		this.id = id;
		this.createdAt = createdAt;
		this.title = title;
		this.readFromQuestioner = readFromQuestioner;
		this.readFromRespondent = readFromRespondent;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isReadFromQuestioner() {
		return readFromQuestioner;
	}

	public void setReadFromQuestioner(boolean readFromQuestioner) {
		this.readFromQuestioner = readFromQuestioner;
	}

	public boolean isReadFromRespondent() {
		return readFromRespondent;
	}

	public void setReadFromRespondent(boolean readFromRespondent) {
		this.readFromRespondent = readFromRespondent;
	}

	public User getQuestioner() {
		return questioner;
	}

	public void setQuestioner(User questioner) {
		this.questioner = questioner;
	}

	public User getRespondent() {
		return respondent;
	}

	public void setRespondent(User respondent) {
		this.respondent = respondent;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	// --------------------------------------
}
