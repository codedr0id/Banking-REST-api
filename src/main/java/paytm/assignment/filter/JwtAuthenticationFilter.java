package paytm.assignment.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import paytm.assignment.utils.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    // Get Logger for the class
    private static final Logger logger = LogManager.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        logger.info("Filter : Filtering The Request");
        logger.warn("Filter : Getting Authorization Token From Authorization : 'Bearer Token' ");

        final String authHeader = request.getHeader("Authorization");
        String userThatRequestingApi = request.getParameter("userName");
        String userNameFetchedFromToken = null;
        String jwt = null;


        logger.info("Filter : Token :: " + authHeader);


        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            userNameFetchedFromToken = jwtUtil.extractUsername(jwt);
            userThatRequestingApi = userNameFetchedFromToken;
            logger.debug("Filter : User Requesting API Call : " + userThatRequestingApi);
            logger.debug("Filter : Username Fetched From Token ::  " + userNameFetchedFromToken);
        }


        if (userNameFetchedFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
            logger.info(" User fetched from both API and Token");
            UserDetails userDetails = userDetailsService.loadUserByUsername(userNameFetchedFromToken);

            if(userNameFetchedFromToken.equalsIgnoreCase(userThatRequestingApi)) {
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    logger.info(" Validating User");
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
            else
            {
                logger.info("User whose token used :: " + userNameFetchedFromToken );
                logger.info("User who request Api call :: " + userThatRequestingApi);
                logger.warn(userThatRequestingApi + " is trying to attack .");
            }
        }

        filterChain.doFilter(request, response);

    }
}