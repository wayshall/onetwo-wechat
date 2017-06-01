package org.onetwo4j.sample.utils;

import java.util.stream.Stream;

final public class Enums {

	public static enum CommonStatus {
		NORMAL("正常"),
		DELETE("已删除");
		
		final private String label;

		private CommonStatus(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
		
	}
	
	
	public static enum UserStatus {
		NORMAL("正常"),
		UNCHECK("未验证"),
		FREEZE("冻结"),
		DELETE("注销");
		
		final private String label;

		private UserStatus(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
		
		public static UserStatus of(String status){
			return Stream.of(values()).filter(s->s.name().equalsIgnoreCase(status))
										.findAny()
										.orElseThrow(()->new IllegalArgumentException("error status: " + status));
		}
		
	}
	
	private Enums(){}

}
