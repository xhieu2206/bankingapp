package fpt.banking.system.model;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@Column(name = "name", length = 255, nullable = false)
	private String name;

	public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Role(String name) {
		this.name = name;
	}

	public Role() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
