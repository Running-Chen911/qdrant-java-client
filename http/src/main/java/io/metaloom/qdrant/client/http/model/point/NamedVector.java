package io.metaloom.qdrant.client.http.model.point;

import java.util.List;

import io.metaloom.qdrant.client.http.model.RestModel;

public class NamedVector implements RestModel {

	private String name;

	private List<Float> vector;

	public String getName() {
		return name;
	}

	public NamedVector setName(String name) {
		this.name = name;
		return this;
	}

	public List<Float> getVector() {
		return vector;
	}

	public NamedVector setVector(List<Float> vector) {
		this.vector = vector;
		return this;
	}
}
