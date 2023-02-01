package io.metaloom.qdrant.client.grpc.method;

import static io.metaloom.qdrant.client.grpc.ModelHelper.point;
import static io.metaloom.qdrant.client.grpc.ModelHelper.pointId;
import static io.metaloom.qdrant.client.grpc.ModelHelper.value;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import io.metaloom.qdrant.client.grpc.AbstractGRPCClientTest;
import io.metaloom.qdrant.client.grpc.ModelHelper;
import io.metaloom.qdrant.client.grpc.proto.JsonWithInt.Value;
import io.metaloom.qdrant.client.grpc.proto.Points.PointStruct;
import io.metaloom.qdrant.client.grpc.proto.Points.UpdateStatus;
import io.metaloom.qdrant.client.grpc.proto.Points.Vector;
import io.metaloom.qdrant.client.grpc.proto.Points.Vectors;
import io.metaloom.qdrant.client.testcases.PointClientTestcases;

public class PointGRPCClientTest extends AbstractGRPCClientTest implements PointClientTestcases {

	@Before
	public void setupTestData() {
		createCollection(TEST_COLLECTION_NAME);

		Map<String, Value> values = new HashMap<>();
		values.put("color", value("blue"));
		PointStruct p1 = point(42L, new float[] { 7.43f, 0.1f, 0.25f, 1.5f }, values);
		PointStruct p2 = point(43L, new float[] { 0.45f, 2.61f, 0.88f, 6.25f }, values);
		PointStruct p3 = point(44L, new float[] { 2.41f, 0.9f, 0.81f, 2.45f }, values);
		PointStruct p4 = point(45L, new float[] { 0.42f, 1.0f, 0.51f, 5.85f }, values);

		client.upsertPoint(TEST_COLLECTION_NAME, p1, true).sync();
		client.upsertPoint(TEST_COLLECTION_NAME, p2, true).sync();
		client.upsertPoint(TEST_COLLECTION_NAME, p3, true).sync();
		client.upsertPoint(TEST_COLLECTION_NAME, p4, true).sync();

		assertPointCount(4, TEST_COLLECTION_NAME);

	}

	@Test
	@Override
	public void testGetPoint() throws Exception {
		client.getPoint(TEST_COLLECTION_NAME, pointId(42L));
	}

	@Test
	@Override
	public void testGetPoints() throws Exception {
		fail("Not implemented");
	}

	@Test
	@Override
	public void testUpsertPointViaList() throws Exception {
		fail("Not implemented");
	}

	@Test
	public void testUpsertPointWithUuid() {
		PointStruct point = point(UUID.randomUUID(), new float[] { 0.2f, 0.1f, 0.3f, 0.4f }, null);
		client.upsertPoint(TEST_COLLECTION_NAME, point, true).sync();
		assertPointCount(4 + 1, TEST_COLLECTION_NAME);
	}

	@Test
	@Override
	public void testUpsertPointsViaListBatch() throws Exception {
		fail("Not implemented");
	}

	@Test
	@Override
	public void testUpsertPointsViaNamedBatch() throws Exception {
		fail("Not implemented");
	}

	@Test
	@Override
	public void testDeletePoints() throws Exception {
		assertPointCount(4, TEST_COLLECTION_NAME);
		client.deletePoints(TEST_COLLECTION_NAME, true, pointId(42L)).sync();
		assertPointCount(3, TEST_COLLECTION_NAME);
	}

	@Test
	@Override
	public void testSetPointPayload() throws Exception {
		fail("Not implemented");
	}

	@Test
	@Override
	public void testOverwritePointPayload() throws Exception {
		fail("Not implemented");
	}

	@Test
	@Override
	public void testDeletePointPayload() throws Exception {
		fail("Not implemented");
	}

	@Test
	@Override
	public void testClearPointPayload() throws Exception {
		fail("Not implemented");
	}

	@Test
	@Override
	public void testScrollPoints() throws Exception {
		fail("Not implemented");
	}

	@Test
	@Override
	public void testSearchPoints() throws Exception {
		fail("Not implemented");
	}

	@Test
	@Override
	public void testSearchBatchPoints() throws Exception {
		fail("Not implemented");
	}

	@Test
	@Override
	public void testRecommendPoints() throws Exception {
		fail("Not implemented");
	}

	@Test
	@Override
	public void testRecommendBatchPoints() throws Exception {
		fail("Not implemented");
	}

	@Test
	@Override
	public void testCountPoints() throws Exception {
		// Insert a new vector
		for (int i = 0; i < 10; i++) {
			Vector vector = ModelHelper.toVector(new float[] { 0.43f + i, 0.1f, 0.61f, 1.45f });
			PointStruct point = PointStruct.newBuilder()
				.putPayload("color", ModelHelper.value("blue"))
				.setId(ModelHelper.pointId(42L + i))
				.setVectors(Vectors.newBuilder().setVector(vector))
				.build();
			assertEquals(UpdateStatus.Completed, client.upsertPoint(TEST_COLLECTION_NAME, point, true).sync().getResult().getStatus());
		}
		assertPointCount(4 + 10, TEST_COLLECTION_NAME);
	}

}
