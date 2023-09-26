package com.camping.dto;

import java.util.List;

public class InterestsRequest {
	private List<String> interests;
	
	public InterestsRequest() {
	}

	public InterestsRequest(List<String> interests) {
		this.interests = interests;
	}
	
	public List<String> getPositions(){
		return interests;
	}

	@Override
	public String toString() {
		
		return "InterestsRequest{interests=" + interests + "}" ;
	}
	
	
	
}
