package com.mint.harvey.code.challenge.CardVerifier.data;

public class HTTPParams {

	private String url;
	private String mode;
	private int readTimeOut;
	private int connectionTimeOut;
	private String contentType;

	public HTTPParams(String url, String mode, int readTimeOut, int connectionTimeOut, String contentType) {
		this.url = url;
		this.mode = mode;
		this.readTimeOut = readTimeOut;
		this.connectionTimeOut = connectionTimeOut;
		this.contentType = contentType;
	}

	public String getUrl() {
		return url;
	}

	public String getMode() {
		return mode;
	}

	public int getReadTimeOut() {
		return readTimeOut;
	}

	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public String getContentType() {
		return contentType;
	}

	@Override
	public String toString() {
		return "{\"class\":\"HTTPParams\",\"url\":\"" + url + "\", \"mode\":\"" + mode + "\", \"readTimeOut\":\""
				+ readTimeOut + "\", \"connectionTimeOut\":\"" + connectionTimeOut + "\", \"contentType\":\""
				+ contentType + "\"}";
	}

	public static class Builder {
		private String url;
		private String mode;
		private int readTimeOut;
		private int connectionTimeOut;
		private String contentType;

		public Builder url(String url) {
			this.url = url;
			return this;
		}

		public Builder mode(String mode) {
			this.mode = mode;
			return this;
		}

		public Builder readTimeOut(int readTimeOut) {
			this.readTimeOut = readTimeOut;
			return this;
		}

		public Builder connectionTimeOut(int connectionTimeOut) {
			this.connectionTimeOut = connectionTimeOut;
			return this;
		}

		public Builder contentType(String contentType) {
			this.contentType = contentType;
			return this;
		}

		public HTTPParams build() {
			return new HTTPParams(this.url, this.mode, this.readTimeOut, this.connectionTimeOut, this.contentType);
		}

	}

}
