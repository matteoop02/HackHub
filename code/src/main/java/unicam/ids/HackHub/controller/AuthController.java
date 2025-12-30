package unicam.ids.HackHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unicam.ids.HackHub.dto.UserDTO;
import unicam.ids.HackHub.model.User;
import unicam.ids.HackHub.services.UserService;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        User user = userService.login(userDTO);
        if(user != null) {
            return ResponseEntity.ok("Login effettuato");
        } else {
            return ResponseEntity.badRequest().body("Username o Password errati");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        try {
            if(userService.usernameAlreadyExist(userDTO.getUsername())) {
                return ResponseEntity.badRequest().body("Username già esistente");
            }
            if(userDTO.getPassword().length() < 5) {
                return ResponseEntity.badRequest().body("Password troppo corta. Inserire una password lunga almeno 5 caratteri");
            }
            userService.register(userDTO);
            return ResponseEntity.ok("Registrazione completata");
        } catch(Exception ex) {
                return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
