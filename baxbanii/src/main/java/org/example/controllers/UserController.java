package org.example.controllers;
import lombok.AllArgsConstructor;
import org.example.controllers.requestClasses.UserDTO;
import org.example.service.MyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private MyService service;

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username){
        UserDTO user = service.getUserDTOByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
}
