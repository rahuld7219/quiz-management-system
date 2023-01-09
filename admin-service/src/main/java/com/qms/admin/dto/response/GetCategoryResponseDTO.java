package com.qms.admin.dto.response;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCategoryResponseDTO {
	
	private Long id;

	private String name;

	private OffsetDateTime createdOn;

	private OffsetDateTime updatedOn;
}
