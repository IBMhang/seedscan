package asl.seedscan.metrics;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import asl.testutils.ResourceManager;

public class StationDeviationMetricTest {

	private StationDeviationMetric metric;
	private static MetricData data1;
	private static MetricData data2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		data1 = (MetricData) ResourceManager.loadCompressedObject("/data/IU.ANMO.2015.206.MetricData.ser.gz", false);
		data2 = (MetricData) ResourceManager.loadCompressedObject("/data/GS.OK029.2015.360.MetricData.ser.gz", false);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		data1 = null;
		data2 = null;
	}

	@Before
	public void setUp() throws Exception {
		metric = new StationDeviationMetric();
		metric.add("lower-limit", "90");
		metric.add("upper-limit", "110");
		metric.add("modelpath", ResourceManager.getDirectoryPath("/models"));
	}

	@Test
	public final void testGetVersion() throws Exception {
		assertEquals("Version #: ", (long) 2, metric.getVersion());
	}

	@Test
	public final void testProcessANMO() throws Exception {
		metric.setData(data1);
		
		HashMap<String, Double> expect = new HashMap<String, Double>();
		expect.put("00,LH1", -6.259435685534593);
		expect.put("00,LH2", -3.6049945557454826);
		expect.put("00,LHZ", -4.813953146458895);
		expect.put("10,LH1", -14.172829523795704);
		expect.put("10,LH2", -11.9362081816316);
		expect.put("10,LHZ", -10.650704302448048);

		TestUtils.testMetric(metric, expect);	
	}
	
	@Test
	public final void testProcessGSOK() throws Exception {
		metric.setData(data2);
		
		HashMap<String, Double> expect = new HashMap<String, Double>();
		expect.put("00,LH1", 14.257283986992178);
		expect.put("00,LH2", 24.696306735105786);
		
		expect.put("00,HH1", 15.821418158610228);
		expect.put("00,HH2", 25.1707731853566);
		expect.put("00,HHZ", 25.399405846808705);

		TestUtils.testMetric(metric, expect);	
	}

	@Test
	public final void testGetBaseName() throws Exception {
		assertEquals("BaseName", "StationDeviationMetric", metric.getBaseName());
	}

}
