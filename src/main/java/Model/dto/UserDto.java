package Model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    String id;

    Integer timesFined;
    Integer uwuFineAmount;//TODO add amount of times fined and other metrics
    LocalDate birthday;

    List<String> sharedServerList;
    List<String> previousRoles;
}