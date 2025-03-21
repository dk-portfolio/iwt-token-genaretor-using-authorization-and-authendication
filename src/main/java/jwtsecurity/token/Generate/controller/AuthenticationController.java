package jwtsecurity.token.Generate.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jwtsecurity.token.Generate.DTO.AuthenticationRequest;
import jwtsecurity.token.Generate.DTO.AuthenticationResponse;
import jwtsecurity.token.Generate.DTO.RegistrationRequest;
import jwtsecurity.token.Generate.Services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {



        private final AuthenticationService service;

        @PostMapping("/register")
        @ResponseStatus(HttpStatus.ACCEPTED)
        public ResponseEntity<?> register(
                @RequestBody @Valid RegistrationRequest request
        ) throws MessagingException {
            service.register(request);
            return ResponseEntity.accepted().build();
        }

        @PostMapping("/authenticate")
        public ResponseEntity<AuthenticationResponse> authenticate(
                @RequestBody AuthenticationRequest request
        ) {
            return ResponseEntity.ok(service.authenticate(request));
        }
        @GetMapping("/activate-account")
        public void confirm(
                @RequestParam String token
        ) throws MessagingException {
            service.activateAccount(token);
        }


        @GetMapping("/user")
        @PreAuthorize("hasAuthority('ROLE_USER')")
        public String user() throws MessagingException {

            return "this is user";
        }
        @GetMapping("/adminUser")
        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        public String admin() throws MessagingException {

            return "this is admin";
        }

    }


