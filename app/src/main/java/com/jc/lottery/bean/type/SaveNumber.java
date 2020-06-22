package com.jc.lottery.bean.type;

import java.io.Serializable;

public class SaveNumber implements Serializable{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String type;
		private String categories;
		private String result;
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getCategories() {
			return categories;
		}

		public void setCategories(String categories) {
			this.categories = categories;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		private String code;
		
		
}
