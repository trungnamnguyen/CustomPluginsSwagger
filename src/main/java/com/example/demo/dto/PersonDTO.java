package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Demo")
@JsonRootName(value = "person")
public class PersonDTO {
    @NotNull
    @Min(1)
    private Integer name;
    @NotNull
    private Integer id;

    List<StudentDTO> studentDTOS;
}
