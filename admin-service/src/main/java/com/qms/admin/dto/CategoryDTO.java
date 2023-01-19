package com.qms.admin.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CategoryDTO implements Serializable {

	private static final long serialVersionUID = 6051937579960606024L;
	
	private Long id;
	private String categoryName;
}
