package com.rbc.nexgen.batch.service.TEST;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	private String href;
    private List<Data2> data;
    private List<Link> links;
}