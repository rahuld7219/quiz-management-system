package com.qms.attendee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
	private Integer empId;
	private String empName;
	private Double empSal;
	private String empDept;
}
