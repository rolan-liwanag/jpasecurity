package com.liwanag.jpasecurity;

import com.liwanag.jpasecurity.dto.AuthorityDto;
import com.liwanag.jpasecurity.dto.UserDto;
import com.liwanag.jpasecurity.model.User;
import com.liwanag.jpasecurity.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JpasecurityApplicationTests {

	@Autowired
	UserService userService;

	@BeforeAll
	public void setUp() {
		UserDto userDto1 = new UserDto();
		userDto1.setUsername("nuzamaki");
		userDto1.setPassword("password");
		userDto1.setEnabled(true);
		userDto1.setFirstname("Naruto");
		userDto1.setLastname("Uzamaki");
		List<AuthorityDto> authorityDtos1 = new ArrayList<>();
		AuthorityDto authorityDto1 = new AuthorityDto();
		authorityDto1.setAuthority("ROLE_USER");
		authorityDtos1.add(authorityDto1);
		userDto1.setAuthorities(authorityDtos1);
		this.userService.createUser(userDto1);

		UserDto userDto2 = new UserDto();
		userDto2.setUsername("hhyuga");
		userDto2.setPassword("password");
		userDto2.setEnabled(true);
		userDto2.setFirstname("Hinata");
		userDto2.setLastname("Hyuga");
		List<AuthorityDto> authorityDtos2 = new ArrayList<>();
		AuthorityDto authorityDto2 = new AuthorityDto();
		authorityDto2.setAuthority("ROLE_USER");
		authorityDtos2.add(authorityDto1);
		userDto2.setAuthorities(authorityDtos2);
		this.userService.createUser(userDto2);
	}

	@Test
	public void shouldGetAUserByUsername() {
		Optional<UserDto> userDto = this.userService.findUserByUsername("nuzamaki");

		assertThat(userDto.isPresent());
		assertThat(userDto.get().getUsername().equals("nuzamaki"));
	}

	@Test
	public void shouldGetAllUsersSortedByLastname() {
		List<UserDto> userDtos = this.userService.getAllUsers();

		assertThat(userDtos != null);
		assertThat(userDtos.size() > 0);
		assertThat(userDtos.get(0).getUsername().equals("hhyug"));
	}

	@Test
	public void shouldCreateNewUser() {
		List<UserDto> userDtosBefore = this.userService.getAllUsers();

		UserDto userDto = new UserDto();
		userDto.setUsername("sharuno");
		userDto.setPassword("password");
		userDto.setEnabled(true);
		userDto.setFirstname("Haruno");
		userDto.setLastname("Sakura");
		List<AuthorityDto> authorityDtos = new ArrayList<>();
		AuthorityDto authorityDto = new AuthorityDto();
		authorityDto.setAuthority("ROLE_USER");
		authorityDtos.add(authorityDto);
		userDto.setAuthorities(authorityDtos);
		this.userService.createUser(userDto);

		List<UserDto> userDtosAfter = this.userService.getAllUsers();

		assertThat(userDtosBefore.size() < userDtosAfter.size());
	}

	@Test
	public void shouldNotCreateNewUserWithSameFirstnameLastnameCombo() {
		List<UserDto> userDtosBefore = this.userService.getAllUsers();

		UserDto userDto = new UserDto();
		userDto.setUsername("nuzamaki");
		userDto.setPassword("password");
		userDto.setEnabled(true);
		userDto.setFirstname("Naruto");
		userDto.setLastname("Uzamaki");
		List<AuthorityDto> authorityDtos = new ArrayList<>();
		AuthorityDto authorityDto = new AuthorityDto();
		authorityDto.setAuthority("ROLE_USER");
		authorityDtos.add(authorityDto);
		userDto.setAuthorities(authorityDtos);
		this.userService.createUser(userDto);

		List<UserDto> userDtosAfter = this.userService.getAllUsers();

		assertThat(userDtosBefore.size() == userDtosAfter.size());
	}
}
