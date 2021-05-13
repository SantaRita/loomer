package org.srcom.loomerdb.api.model;

import java.util.List;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Generico0DTO implements Generico0 {

    private List<Generico0.MapObject> mapObject;


}