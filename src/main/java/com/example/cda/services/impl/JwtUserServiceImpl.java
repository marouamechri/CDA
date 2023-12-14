package com.example.cda.services.impl;

import com.example.cda.constents.CdaConstants;
import com.example.cda.dtos.ResponseUser;
import com.example.cda.exceptions.AccountExistsException;
import com.example.cda.modeles.Role;
import com.example.cda.modeles.User;
import com.example.cda.repositorys.RoleRepository;
import com.example.cda.repositorys.UserRepository;
import com.example.cda.services.JwtUserService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class JwtUserServiceImpl implements JwtUserService {
    private final String signingKey;
    //declarer une instance de logger
    public static final Logger logger = LoggerFactory.getLogger(JwtUserServiceImpl.class);


    public JwtUserServiceImpl() {
        this.signingKey = CdaConstants.JWT_SECRET;
    }

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Override
    public String generateJwtForUser(UserDetails userDetails) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3* 60*60*1000);
        String username = userDetails.getUsername();
        User user = (User)  userDetails;
        //String refresh_token = generateJwtRefreshToken(user);
        String jwtAccessToken = Jwts.builder()
                .setSubject(username)
                .claim("authorities", user.getRolesString())
                .setIssuedAt(now).setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, signingKey).compact();
        return jwtAccessToken;
    }

    @Override
    public UserDetails getUserFromJwt(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(jwt).getBody();
        String username = claims.getSubject();
        UserDetails user = loadUserByUsername(username);
        return user;
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {

        try{
            final  String username = getUserNameFromJwt(token);
            Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
            return username.equals(userDetails.getUsername())&& !isExpiredJwtToken(token);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException ex) {
            throw ex;
        }
    }

    @Override
    public Claims getAllClaimsFromToken(String token) {

        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getUserNameFromJwt(String JwtToken) {

        try {
            final  Claims claims= getAllClaimsFromToken(JwtToken);
            return  (String)  claims.getSubject();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean isExpiredJwtToken(String token) {

        try{
            Claims claims = getAllClaimsFromToken(token);
            Date expirationDate = claims.getExpiration();
            return expirationDate.before(new Date());
        }catch (ExpiredJwtException e){
            return true;
        }catch (Exception e){
            return  true;
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("The user cannot be found");
        }
        return user;
    }
    @Override
    public Authentication authenticate(String username, String password) throws Exception {
        //System.out.println("username auth"+ username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
            authentication = authenticationConfiguration.getAuthenticationManager().authenticate(authentication);
            return authentication;
    }

    @Override
    public UserDetails save(String username, String password, String name)  {

        Collection<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("USER"));

        User newuser = new User();
        newuser.setUsername(username);
        newuser.setName(name);
        newuser.setRoles(roles);
        newuser.setPassword(passwordEncoder.encode(password));
        User result =  userRepository.save(newuser);

        return result;
    }

    @Override
    public UserDetails get(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDetails findByUserName(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public List<ResponseUser> getAllAdmin() {
        Iterable<User> users = userRepository.findAll();
        List<ResponseUser> result = new ArrayList<>();
        for (User u:users) {
            if(u.getRolesString().contains("ADMIN")) {
                result.add( new ResponseUser(u.getName(), u.getUsername()));
            }
        }
        return result;
    }

   /* @Override
    public UserDetails changePassword(String username, String newPassword) {
        UserDetails userDetails = loadUserByUsername(username);
        if(userDetails!=null){
            User user = userRepository.findByUsername(username);
            user.setPassword(passwordEncoder.encode(newPassword));
            return  userRepository.save(user);
        }else return null;
    }*
    @Override
    public  String forgotPassword(String email){

    }*/


}
