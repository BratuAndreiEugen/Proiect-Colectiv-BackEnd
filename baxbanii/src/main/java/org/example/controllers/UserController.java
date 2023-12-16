package org.example.controllers;
import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.UserDTO;
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

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {

        try{
            UserDTO user = userService.getUserDTOByUsername(username);
            return ResponseEntity.ok(user);
        }
        catch (DataChangeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @RequestMapping(value = "/byid/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserById(@PathVariable Long id) {

        try{
            UserDTO user = userService.getUserDTOById(id);
            return ResponseEntity.ok(user);
        }
        catch (DataChangeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
