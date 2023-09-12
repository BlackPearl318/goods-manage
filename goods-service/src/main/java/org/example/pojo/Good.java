package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Good {

    private Integer gId;
    private String gName;
    private String gBrand;
    private BigDecimal gPrice;
    private Integer gNumber;
    private String gFrom;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gTime;
    private Integer gStatus;
    private Integer aId;

    private Admin admin;



}
