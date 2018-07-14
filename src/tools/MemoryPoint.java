package tools;

import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

@Measurement(name = "memory")
public class MemoryPoint {

    @Column(name = "time")
    public Instant time;

    @Column(name = "name")
    public String name;

    @Column(name = "free")
    public Long free;

    @Column(name = "used")
    public Long used;

    @Column(name = "buffer")
    public Long buffer;
}
