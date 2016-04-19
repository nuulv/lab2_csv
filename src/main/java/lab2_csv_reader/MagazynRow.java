package lab2_csv_reader;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Order({0,1,2,3,4,5,6,7}) // default order
//@Order({7,6,5,4,3,2,1,0}) // reverse
public class MagazynRow implements Serializable{
    private Integer nrMag;
    private CardNumber nrKarty;
    private Integer nrOdpadu;
    private Integer nrKlienta;
    private Integer firma;
    private String jednostka;
    private Double masa;
    private Date dataD;
}