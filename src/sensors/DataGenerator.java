package sensors;

import java.util.Random;

public class DataGenerator {

    Random random= new Random();
    private final static byte RIME_CHANNEL1 = (byte) 2;
    private final static byte RIME_CHANNEL2 = (byte) 0;

    private final String packetTemplate = "simId:type:dataPoint";
    /**
     *
     * @return
     */
    public double getDataPointWithRandomVariance(double datapoint, int variancePercentage){
        if (random.nextBoolean()){
            // positive
            return datapoint * (100 +random.nextInt(variancePercentage))/100.0;
        }else
        {
            return datapoint * (100 -random.nextInt(variancePercentage))/100.0;
        }
    }

    public byte[] createRadioPacket(SimData data){
        String packet = packetTemplate.replaceAll("simId",String.valueOf(data.getSimId()))
                .replaceAll("type",String.valueOf(data.getType()))
                .replaceAll("dataPoint",String.valueOf(data.getDataPoint()));
        byte[] buff = new byte[(packet.getBytes().length+2)];
        buff[0]=RIME_CHANNEL1;
        buff[1]=RIME_CHANNEL2;
        System.arraycopy(packet.getBytes(),0,buff,2,buff.length-2);
        return buff;
    }

    public SimData getObjFromRadioPacket(byte[] data) throws Exception {
        SimData radioData = new SimData();

        if (data.length<2 || data[0] != RIME_CHANNEL1 || data[1] != RIME_CHANNEL2)
            throw new Exception("Invalid Packet");
        byte[] msgBuff= new byte[data.length-2];
        System.arraycopy(data,2,msgBuff,0,msgBuff.length);
        String strMsg = new String(msgBuff);
        String[] parts = strMsg.split(":");

        radioData.setSimId(Integer.parseInt(parts[0]));
        radioData.setType(parts[1]);
        radioData.setDataPoint(Double.parseDouble(parts[2]));

        return radioData;

    }

}
