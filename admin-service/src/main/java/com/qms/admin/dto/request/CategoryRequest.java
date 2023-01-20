package com.qms.admin.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class CategoryRequest {

	@NotBlank(message = "Category name cannot be blank.")
	private String name;
}
