package org.srcom.rest;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public interface Generico0 {

	List<MapObject> getMapObject();

	@Getter
	@Setter
	class MapObject{
	   Map map;
	}
	
}
