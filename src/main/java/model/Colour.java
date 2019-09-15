package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Colour {
    private Long id;
    private String name;
    private int red;
    private int green;
    private int blue;
    private int alpha;
}
