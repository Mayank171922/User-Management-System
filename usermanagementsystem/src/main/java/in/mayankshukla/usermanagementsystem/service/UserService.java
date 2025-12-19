package in.mayankshukla.usermanagementsystem.service;



import in.mayankshukla.usermanagementsystem.dto.*;
import in.mayankshukla.usermanagementsystem.dto.RegisterRequest;
import in.mayankshukla.usermanagementsystem.entity.User;
import in.mayankshukla.usermanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public User registerUser(RegisterRequest request) throws Exception {
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new Exception("Email is required");
        }

        if (!EMAIL_PATTERN.matcher(request.getEmail()).matches()) {
            throw new Exception("Invalid email format");
        }

        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new Exception("Password must be at least 6 characters");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new Exception("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());

        return userRepository.save(user);
    }

    public User loginUser(LoginRequest request) throws Exception {
        if (request.getEmail() == null || request.getPassword() == null) {
            throw new Exception("Email and password are required");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new Exception("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new Exception("Invalid credentials");
        }

        return user;
    }

    public User getUserById(Long userId) throws Exception {
        return userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
    }

    public User updateProfile(Long userId, UpdateProfileRequest request) throws Exception {
        User user = getUserById(userId);

        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }

        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }

        return userRepository.save(user);
    }
}