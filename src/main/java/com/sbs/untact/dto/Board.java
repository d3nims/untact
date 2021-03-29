package com.sbs.untact.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Board extends EntityDto {
	
	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private int boardId;
	private String code;
	private String name;
	
	private String extra__boardName;
	
}
