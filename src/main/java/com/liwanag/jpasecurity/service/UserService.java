package com.liwanag.jpasecurity.service;

import com.liwanag.jpasecurity.dto.AuthorityDto;
import com.liwanag.jpasecurity.dto.UserDto;
import com.liwanag.jpasecurity.model.Authority;
import com.liwanag.jpasecurity.model.User;
import com.liwanag.jpasecurity.model.Profile;
import com.liwanag.jpasecurity.repository.AuthorityRepository;
import com.liwanag.jpasecurity.repository.ProfileRepository;
import com.liwanag.jpasecurity.repository.UserRepository;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    AuthorityRepository authorityRepository;

    ProfileRepository profileRepository;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.profileRepository = profileRepository;
    }

    public void createUser(UserDto userDto) {
        User user = this.getUser(userDto);
        this.userRepository.save(user);

        Profile profile = this.getProfile(userDto);
        this.profileRepository.save(profile);

        List<Authority> authorities = this.getAuthorities(userDto);
        for(Authority authority : authorities) {
            this.authorityRepository.save(authority);
        }
    }

    public Optional<UserDto> findUserByUsername(String username) {
        Optional<User> userOpt = this.userRepository.findById(username);
        Optional<UserDto> userDtoOpt;
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            Optional<Profile> profileOpt = this.profileRepository.findById(username);
            Profile profile = new Profile();
            if(profileOpt.isPresent()) {
                profile = profileOpt.get();
            }
            List<Authority> authorities = this.authorityRepository.findAuthoritiesByUsername(username);
            UserDto userDto = this.getUserDto(user, profile, authorities);
            return Optional.of(userDto);
        } else {
            return Optional.empty();
        }
    }

    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = this.userRepository.findAll();
        for(User user : users) {
            Optional<Profile> profileOpt = this.profileRepository.findById(user.getUsername());
            Profile profile = new Profile();
            if(profileOpt.isPresent()) {
                profile = profileOpt.get();
            }
            List<Authority> authorities = this.authorityRepository.findAuthoritiesByUsername(user.getUsername());
            UserDto userDto = this.getUserDto(user, profile, authorities);
            userDtos.add(userDto);
        }
        Collections.sort(userDtos);
        return userDtos;
    }

    private User getUser(UserDto userDto) {
        User user = new User();
        user.setEnabled(userDto.getEnabled());
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setUsername(userDto.getUsername());
        return user;
    }

    private Profile getProfile(UserDto userDto) {
        Profile profile = new Profile();
        profile.setFirstname(userDto.getFirstname());
        profile.setLastname(userDto.getLastname());
        profile.setUsername(userDto.getUsername());
        return profile;
    }

    private List<Authority> getAuthorities(UserDto userDto) {
        List<Authority> authorities = new ArrayList<Authority>();
        for(AuthorityDto authorityDto : userDto.getAuthorities()) {
            Authority authority = new Authority();
            authority.setAuthority(authorityDto.getAuthority());
            authority.setUsername(userDto.getUsername());
            authorities.add(authority);
        }
        return authorities;
    }

    private UserDto getUserDto(User user, Profile profile, List<Authority> authorities) {
        UserDto userDto = new UserDto();
        userDto.setEnabled(user.getEnabled());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setFirstname(profile.getFirstname());
        userDto.setLastname(profile.getLastname());
        List<AuthorityDto> authorityDtos = new ArrayList();
        for(Authority authority : authorities) {
            AuthorityDto authorityDto = new AuthorityDto();
            authorityDto.setAuthority(authority.getAuthority());
            authorityDtos.add(authorityDto);
        }
        userDto.setAuthorities(authorityDtos);
        return userDto;
    }
}
