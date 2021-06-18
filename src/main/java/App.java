import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.util.UUID;

public class App {
    public static void main(String[] args) throws Exception {
        String publisherId = UUID.randomUUID().toString();

        IMqttClient client = new MqttClient("tcp://127.0.0.1:1883",publisherId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);

        client.connect(options);

        //subscreve ao tópico
        client.subscribe(EngineTemperatureSensor.TOPIC, (topic, msg) -> {
            System.out.println(msg);
        });

        //publica mensagem de temperatura
        EngineTemperatureSensor engineTemperatureSensor = new EngineTemperatureSensor(client);

        for (int i = 0; i<= 1000; i++ ){
            engineTemperatureSensor.call();
        }
    }
}
