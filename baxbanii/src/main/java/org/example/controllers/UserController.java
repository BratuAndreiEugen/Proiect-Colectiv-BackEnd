package org.example.controllers;
import lombok.AllArgsConstructor;
import org.example.controllers.responseClasses.UserResponse;
import org.example.exceptions.DataChangeException;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private UserService userService;

    /**
     * Get user information by username.
     *
     * @param username Username of the target user.
     * @return ResponseEntity containing UserDTO object representing the user or an error message if not found.
     * Example Request: "/v1/users/john_doe"
     * Example Response: {"id": 1, "username": "john_doe", "bio": "A bio about John Doe"} or an error message
     */
    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {

        try{
            UserResponse user = userService.getUserDTOByUsername(username);
            return ResponseEntity.ok(user);
        }
        catch (DataChangeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
    /**
     * Get user information by user ID.
     *
     * @param id User ID of the target user.
     * @return ResponseEntity containing UserDTO object representing the user or an error message if not found.
     * Example Request: "/v1/users/byid/1"
     * Example Response: {"id": 1, "username": "john_doe", "bio": "A bio about John Doe"} or an error message
     */
    @GetMapping(value = "/byid/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {

        try{
            UserResponse user = userService.getUserDTOById(id);
            return ResponseEntity.ok(user);
        }
        catch (DataChangeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
