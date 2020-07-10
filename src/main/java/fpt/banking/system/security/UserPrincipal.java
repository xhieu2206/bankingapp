package fpt.banking.system.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fpt.banking.system.model.Membership;
import fpt.banking.system.model.User;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 8372487913190650599L;

	private Long id;

    private String fullname;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Date birthday;

    private String address;

    private String gender;

    private String image;

    private String idCardNumber;

    private String phone;

    private Date createdAt;

    private Date updatedAt;

    private boolean status;

    private boolean locked;

    private Membership membership;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id,
    		String fullname, String username, String email, String password,
    		Collection<? extends GrantedAuthority> authorities, Date birthday,
    		String address, String gender, String image, String idCardNumber,
    		String phone, Date createdAt, Date updatedAt, boolean status, boolean locked, Membership membership) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.birthday = birthday;
        this.address = address;
        this.gender = gender;
        this.image = image;
        this.idCardNumber = idCardNumber;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.locked = locked;
        this.membership = membership;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getFullname(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                user.getBirthday(),
                user.getAddress(),
                user.getGender(),
                user.getImage(),
                user.getIdCardNumber(),
                user.getPhone(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.isStatus(),
                user.isLocked(),
                user.getMembership()
        );
    }

    public Long getId() {
        return id;
    }

    public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getAddress() {
		return address;
	}

	public String getGender() {
		return gender;
	}

	public String getImage() {
		return image;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public String getPhone() {
		return phone;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	public boolean isStatus() {
		return status;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

	@Override
	public String toString() {
		return "UserPrincipal [id=" + id + ", fullname=" + fullname + ", username=" + username + ", email=" + email
				+ ", password=" + password + ", birthday=" + birthday + ", address=" + address + ", gender=" + gender
				+ ", image=" + image + ", idCardNumber=" + idCardNumber + ", phone=" + phone + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", status=" + status + ", locked=" + locked + "]";
	}

}
