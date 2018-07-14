package tools;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.PushGateway;

import java.io.IOException;

/**
 * Class to check adding code to
 */
public class PrometheusPushTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        CollectorRegistry registry = new CollectorRegistry();
        PushGateway pg = new PushGateway("127.0.0.1:9091");

        Gauge duration = Gauge.build()
                .name("my_batch_job_duration_seconds").help("Duration of my batch job in seconds.").register(registry);

        Gauge lastSuccess = Gauge.build()
                .name("my_batch_job_last_success").help("Last time my batch job succeeded, in unixtime.").register(registry);
        try {

            for (int i =0 ; i<2000; i++) {


                Thread.sleep(1000);
                duration.set((int)(Math.random()*1000 % 5000));
                lastSuccess.setToCurrentTime();

                pg.pushAdd(registry, "my_batch_job");
            }
        } finally {


        }
    }
}
