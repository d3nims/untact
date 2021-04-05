package com.sbs.untact.dto;

import java.util.HashMap;
import java.util.Map;

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
	private String name;
	private String code;
	
	private String extra__writer;
	private String extra__boardName;
	private String extra__thumbImg;
	
	private Map<String, Object> extra;
	public Map<String, Object> getExtraNotNull() {
		if ( extra == null ) {
			extra = new HashMap<String, Object>();
		}

		return extra;
	}
	
	public String getWriterThumbImgUrl() {
		return "/common/genFile/file/member/" + memberId + "/common/attachment/1";
	}
	
}
