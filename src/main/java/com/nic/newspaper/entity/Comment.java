package com.nic.newspaper.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "text")
	private String text;
	
	@Column(name = "date")
	private String date;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "articles_comments_users", 
	joinColumns = @JoinColumn(name = "comment_id"), 
	inverseJoinColumns = @JoinColumn(name = "user_id"))
	private User user;
	
	public Comment() {
		super();
	}

	public Comment(String text, String date) {
		super();
		this.text = text;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", text=" + text + ", date=" + date + "]";
	}
	
}
