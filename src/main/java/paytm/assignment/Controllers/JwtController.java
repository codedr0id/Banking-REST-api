package paytm.assignment.Controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import paytm.assignment.DTO.JwtRequest;
import paytm.assignment.DTO.ResponseObject;
import paytm.assignment.utils.JwtUtil;

@Controller
public class JwtController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    // Get Logger for the Class
    private static Logger logger = LogManager.getLogger(JwtController.class);

    @PostMapping("/authenticate")
    public @ResponseBody
    ResponseObject createAuthenticationToken(@RequestBody JwtRequest requestDto) {
        logger.info("Authorization Request Received From Username : " + requestDto.getUserName() + ", Password : " + requestDto.getPassword());

        // Create Response
        ResponseObject response = new ResponseObject();

        try {
            logger.debug("Authenticating user !!!!!!!");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getUserName(), requestDto.getPassword())
            );
        } catch (BadCredentialsException ex) {

            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Invalid username or password");
            response.setResponse("No Token Generated");
            logger.error("Invalid User");
            return response;
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getUserName());
        String jwtToken = jwtUtil.generateToken(userDetails);

        logger.debug("Token : " + jwtToken);

        // On EveryThing Fine
        response.setResponse("Token :" + jwtToken);
        response.setMessage("Token Generated");
        response.setStatusCode(HttpStatus.CREATED.value());
        return response;
    }
}
