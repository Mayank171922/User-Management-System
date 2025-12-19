package in.mayankshukla.usermanagementsystem.dto;

public class UserResponse {
    private Long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String createdAt;

    public UserResponse(Long id, String email, String fullName, String phoneNumber, String createdAt) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCreatedAt() { return createdAt; }
}