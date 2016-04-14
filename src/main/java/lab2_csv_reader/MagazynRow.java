package lab2_csv_reader;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MagazynRow implements Serializable {
    private Integer nrMag;
    private String nrKarty;
    private Integer nrOdpadu;
    private Integer nrKlienta;
    private Integer firma;
    private String jednostka;
    private Double masa;
    private Date dataD;
}