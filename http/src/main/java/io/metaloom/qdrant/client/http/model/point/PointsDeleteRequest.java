package io.metaloom.qdrant.client.http.model.point;

import java.util.List;

import io.metaloom.qdrant.client.http.model.RestRequestModel;
import io.metaloom.qdrant.client.http.model.collection.filter.Filter;

public class PointsDeleteRequest implements RestRequestModel {

	private List<Long> points;
	private Filter filter;

	public List<Long> getPoints() {
		return points;
	}

	public PointsDeleteRequest setPoints(List<Long> points) {
		this.points = points;
		return this;
	}

	public Filter getFilter() {
		return filter;
	}

	public PointsDeleteRequest setFilter(Filter filter) {
		this.filter = filter;
		return this;
	}
}
