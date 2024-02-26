package Database.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Media implements Serializable {
    private String id;
    private String title;
    private String type;
    private String genre;
    private String completed;
    private String addedBy;
    private String addedOnDate;
    private String lastUpdated;
    private String lastUpdatedBy;
}
