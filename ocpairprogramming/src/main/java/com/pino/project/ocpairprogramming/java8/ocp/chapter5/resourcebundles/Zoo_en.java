package com.pino.project.ocpairprogramming.java8.ocp.chapter5.resourcebundles;

import java.util.ListResourceBundle;

public class Zoo_en extends ListResourceBundle{

	@Override
	protected Object[][] getContents() {
		return new Object[][] {
			{"hello","Hello!"},{"open","The zoo is open"}
		};
	}


}
