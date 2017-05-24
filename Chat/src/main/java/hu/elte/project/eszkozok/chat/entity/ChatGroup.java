package hu.elte.project.eszkozok.chat.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * <h1>ChatGroup</h1> 
 * Chat szoba entitás a Hibernate ORM kapcsolathoz.
 * 
 * @author Katona Bence
 *
 */
@Entity
@Table(name = "ChatGroup")
public class ChatGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_chatgroup", joinColumns = {
			@JoinColumn(name = "chat_group_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "user_id", referencedColumnName = "id") })
	private Set<User> userSet;

	public ChatGroup() {
	}
	
	public ChatGroup(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}

	@Override
	public String toString() {
		return "ChatGroup [id=" + id + ", name=" + name + ", userSet=" + userSet + "]";
	}

}
