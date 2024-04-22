//opg7pgm

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;
public class SimpleMqttCallBack implements MqttCallback {
    int status = 0;
    String power;
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String res= new String(mqttMessage.getPayload());
        if (res.contains("POWER")) {
            JSONObject jsonObject = new JSONObject(res);
            power = jsonObject.getString("POWER");
        }
        if (res.contains("AM2301")) {
            String s1 = res;
            System.out.println(res);
            JSONObject jsonObject = new JSONObject(s1);
            JSONObject jo2 = jsonObject.getJSONObject("AM2301");
            double temp = (jo2.getDouble("Temperature"));
            double humidity = (jo2.getDouble("Humidity"));
            System.out.println("temp: " + temp + " - humidity: " + humidity);
            if (humidity > 80 && power.equals("OFF")) {
                MQTTprogram.publishMessage(MQTTprogram.sampleClient, "cmnd/grp3254/Power1", "1");
            }
            if(humidity < 80 && power.equals("ON")){
                MQTTprogram.publishMessage(MQTTprogram.sampleClient, "cmnd/grp3254/Power1", "0");
            }
        }

        // res indeholder en m ling som et JSON-object
        // put real stuff here     < --------    !!!!!!!!!!
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        // not used in this example
    }
} 