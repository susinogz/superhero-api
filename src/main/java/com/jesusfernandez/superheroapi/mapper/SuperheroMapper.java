package com.jesusfernandez.superheroapi.mapper;

import com.jesusfernandez.superheroapi.model.dao.SuperheroDAO;
import com.jesusfernandez.superheroapi.model.dto.SuperheroDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SuperheroMapper extends DAOMapper<SuperheroDTO, SuperheroDAO> {

    SuperheroDAO toDao(SuperheroDTO dto);

    SuperheroDTO toDto(SuperheroDAO dao);

}
