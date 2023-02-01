package com.nhaccuaquang.musique.repository;

import com.nhaccuaquang.musique.entity.Role;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


//@RunWith(SpringRunner.class)
//@SpringBootTest(
//        properties = {
//                "spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true"
//        }
//
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository underTest;

    @ParameterizedTest
    @CsvSource({
            "ROLE_VIP_PRO, true",
            "AX, false",
            "ROLE_ADMIN, true"
    })
    void itShouldReturnTrueIfRoleExisted(String roleName, boolean expected) {
        //given

        //when
        Optional<Role> role = underTest.findByName(roleName);
        //then
        assertThat(role.isPresent()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "1,26",
            "0,0",
            "3,5",
            "2,5",
            "20,1",
            "0,4"
    })
    void itShouldReturnNumberOfAccountHaveThatRole(Long id, int expected) {
        //when
        int number = underTest.countAccountsHaveRoleById(id);

        //then
        assertThat(number).isEqualTo(expected);
    }
}