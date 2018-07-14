package tools;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.influxdb.impl.InfluxDBResultMapper;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class InfluxDbTest {
    public static void main(String[] args) {
        InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086");

        influxDB.setLogLevel(InfluxDB.LogLevel.BASIC);
        Pong response = influxDB.ping();
        if (response.getVersion().equalsIgnoreCase("unknown")) {
            System.out.println("Error in connecting..");
            return;
        }
        Query q =new Query("CREATE DATABASE IoT","IoT",true);
        influxDB.query(q);
        q = new Query("CREATE RETENTION POLICY defaultPolicy ON IoT DURATION 30d REPLICATION 1 DEFAULT","IoT",true);
        influxDB.query(q);
        influxDB.setDatabase("IoT");
        influxDB.enableBatch(100, 200, TimeUnit.MILLISECONDS);
        BatchPoints batchPoints = BatchPoints
                .database("IoT")
                .retentionPolicy("defaultPolicy")
                .build();

        Point point1 = Point.measurement("memory")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("name", "server1")
                .addField("free", 4743656L)
                .addField("used", 1015096L)
                .addField("buffer", 1010467L)
                .build();

        Point point2 = Point.measurement("memory")
                .time(System.currentTimeMillis() - 100, TimeUnit.MILLISECONDS)
                .addField("name", "server1")
                .addField("free", 4743696L)
                .addField("used", 1016096L)
                .addField("buffer", 1008467L)
                .build();

        batchPoints.point(point1);
        batchPoints.point(point2);
        influxDB.write(batchPoints);
        influxDB.flush();
        QueryResult queryResult = influxDB.query(new Query("Select * from memory", "IoT"));
        System.out.println(queryResult.getResults().size());
        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
        List<MemoryPoint> memoryPointList = resultMapper.toPOJO(queryResult, MemoryPoint.class);
        System.out.println(memoryPointList.size());
        for (MemoryPoint p: memoryPointList   ) {
            System.out.println(" "+p.name);
        }
    }
}
