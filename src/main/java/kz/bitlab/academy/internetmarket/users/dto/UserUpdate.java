package kz.bitlab.academy.internetmarket.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdate {

    private String firstName;
    private String lastName;
    private LocalDate birthdate;

}
