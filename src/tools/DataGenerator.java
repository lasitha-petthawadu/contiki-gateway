package tools;

import com.itemanalysis.psychometrics.histogram.Bin;
import com.itemanalysis.psychometrics.histogram.BinCalculationType;
import com.itemanalysis.psychometrics.histogram.Histogram;
import com.itemanalysis.psychometrics.histogram.HistogramType;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.inference.ChiSquareTest;
import org.jfree.data.statistics.SimpleHistogramDataset;
import se.sics.cooja.util.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataGenerator {

    private static int repeatDelay = 24*60;
    private static Date start;
    private static Date chunkEnd;
    private static Histogram histogram;
    private static List<Double> dataset;
    private static List<Histogram> histograms =new ArrayList<>();
    private static int histId =0;
    private static List<Double> chiList = new ArrayList<>();
    private static HashMap<Integer,List<Double>> datasets = new HashMap<>();
    private static DescriptiveStatistics chiStats = new DescriptiveStatistics();
    private static DescriptiveStatistics binDifference = new DescriptiveStatistics();

    public static void main(String[] args) throws FileNotFoundException {

        PrintWriter writer = new PrintWriter(new FileOutputStream(new File("/home/user/data-files/test.csv")));
        Calendar calendar = Calendar.getInstance();
        for (int i=0;i<50;i++){
            calendar.set(Calendar.HOUR_OF_DAY,4);
            calendar.set(Calendar.MINUTE,0);
          //  System.out.println("Raw Data set "+i);
          //  System.out.println("_________________");
            for(int j=0;j<6*24;j++){
                calendar.add(Calendar.MINUTE,9/*+(int)(Math.random()*2)*/);
                calendar.set(Calendar.SECOND,0/*(int)(Math.random()*100) % 60*/);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                double temperature = (25+((hour<=12)?Math.abs(4-hour):Math.abs(20-hour)))+(((((int)((Math.random()*10)%2))==0)?9:0) % 10);
              //  System.out.println(j+" - "+temperature);
               // System.out.println(temperature);
                String time = new SimpleDateFormat("yyyy-MM-dd.hh:mm:ss").format(calendar.getTime()).toString();
                captureLearning(calendar.getTime(),temperature);
                writer.append(time+","+temperature+"\r\n");
            }
            //System.out.println("_________________");
           // System.out.println();
        }
        compareHists(histograms);

        System.out.println("Summary");
        System.out.println("____________");
        System.out.println("\t Bin Variance Mean : "+binDifference.getMean());
        System.out.println("\t Bin Variance Max : "+binDifference.getMax());
        System.out.println("\t Bin Variance Min : "+binDifference.getMin());
        System.out.println("\t Bin Variance Std : "+binDifference.getStandardDeviation());

        System.out.println("Summary");
        System.out.println("____________");
        System.out.println("\t Chi Variance Mean : "+chiStats.getMean());
       // System.out.println("\t Chi Variance Variance : "+chiStats.getVariance());
        System.out.println("\t Chi Variance Max : "+chiStats.getMax());
        System.out.println("\t Chi Variance Min : "+chiStats.getMin());
        System.out.println("\t Chi Variance Std : "+chiStats.getStandardDeviation());

        writer.flush();
        writer.close();

    }

    private static void captureLearning(Date time,double temp){
        if (start == null){
            Calendar c = Calendar.getInstance();
            start = time;
            c.setTime(start);
            c.add(Calendar.MINUTE,repeatDelay);
            chunkEnd = c.getTime();
            histogram = new Histogram(HistogramType.DENSITY, BinCalculationType.FREEDMAN_DIACONIS,false);
            dataset = new ArrayList<>();

        }else{
            if (time.after(chunkEnd)){
                // Ready to create histogram.
                histogram.evaluate();
                //System.out.println("Histogram Chunk "+(histId++));

                for(int i=0;i<histogram.getNumberOfBins();i++){
                    Bin b=histogram.getBinAt(i);
                  //  System.out.println("\t Bin "+i+" ("+Math.round(b.getLowerBound())+"-"+Math.round(b.getUpperBound())+") - "+b.getFrequency());
                }
                histograms.add(histogram);
                datasets.put(histId++,dataset);


                if (datasets.size()>1)
              //  compareHists(datasets,10);
                start = null;
            }else
            {
                //Long l = new Long(temp);
                histogram.increment(temp);

                dataset.add(temp);
            }
        }

    }

    private static void compareHists(HashMap<Integer,List<Long>> datasets, int size) {
        ChiSquareTest test = new ChiSquareTest();
        double[] iHist = Arrays.copyOfRange(datasets.get(0).stream().mapToDouble(l ->l).toArray(),0,datasets.get(0).size()/2);
        double[] jHist = Arrays.copyOfRange(datasets.get(1).stream().mapToDouble(l ->l).toArray(),0,datasets.get(1).size()/2);
        Histogram histogram1 = new Histogram(HistogramType.DENSITY, BinCalculationType.STURGES,false);
        histogram1.setData(iHist);
        Histogram histogram2 = new Histogram(HistogramType.DENSITY, BinCalculationType.STURGES,false);
        histogram2.setData(jHist);
        histogram1.evaluate();
        histogram2.evaluate();
        double chi = test.chiSquareDataSetsComparison(getHistFrequency(histogram1),getHistFrequency(histogram2));
        System.out.println("Half Chi "+chi);
    }

    private static void compareHists(List<Histogram> histograms) {
        HashMap<Integer,long[]> frequencymap = new HashMap<>();
        ChiSquareTest test = new ChiSquareTest();
       for (int i=0; i<histograms.size();i++){
           long[] iHist;
           if (frequencymap.containsKey(i))
            iHist=frequencymap.get(i);
           else {
               iHist = getHistFrequency(histograms.get(i));
               frequencymap.put(i,iHist);
           }
           for (int j=0; j<histograms.size();j++){
                if (i==j) continue;
                long[] jHist;
               if (frequencymap.containsKey(j))
                   jHist=frequencymap.get(j);
               else {
                   jHist = getHistFrequency(histograms.get(j));
                   frequencymap.put(j,jHist);

               }
               // Chi
               double chi = 0;
               if (iHist.length == jHist.length) {
                   for(int k=0 ;k<iHist.length;k++){
                       binDifference.addValue(Math.abs(iHist[k]-jHist[k]));
                   }
                   chi = test.chiSquareDataSetsComparison(iHist, jHist);
                   chiList.add(chi);

                   chiStats.addValue(chi);
               }
             //  if (chi >0.5) {
                 //  System.out.println("Histogram i:"+i);
                   for(int k=0;k<histograms.get(i).getNumberOfBins();k++){

                       Bin b=histograms.get(i).getBinAt(k);
                     //  System.out.println("\t Bin "+k+" ("+Math.round(b.getLowerBound())+"-"+Math.round(b.getUpperBound())+") - "+b.getFrequency());
                   }
                   //System.out.println("Histogram j:"+j);
                   for(int k=0;k<histograms.get(j).getNumberOfBins();k++){
                       Bin b=histograms.get(j).getBinAt(k);

                       //  System.out.println("\t Bin "+k+" ("+Math.round(b.getLowerBound())+"-"+Math.round(b.getUpperBound())+") - "+b.getFrequency());
                   }
                   System.out.println(i + " vs " + j + " : " + String.format("%.2f", chi));
                   if (chi>5){

                       printHistogram("Hist "+i,histograms.get(i));
                       printHistogram("Hist "+j,histograms.get(j));
                       break;
                   }

             //  }


               /*System.out.println("\t Chi Mean :"+chiStats.getMean());
               System.out.println("\t Chi Max :"+chiStats.getMax());
               System.out.println("\t Chi Mix :"+chiStats.getMin());
               System.out.println("\t Chi Std :"+chiStats.getStandardDeviation());
                */

           }
       }


    }

    private static long[] getHistFrequency(Histogram histogram) {
        List<Long> freq = new ArrayList();
        for (int i=0; i<histogram.getNumberOfBins();i++){
            freq.add((long)histogram.getBinAt(i).getFrequency());
        }
        return freq.stream().mapToLong(l ->l).toArray();

    }

    private static void chiSquare(){
        ChiSquareTest test = new ChiSquareTest();
        long[] binFrequency1 = new long[]{10,20,30,40,50,60,70,80,90,100};
        long[] binFrequency2 = new long[]{10,20,30,40,150,60,70,80,90,100};
        System.out.println("Chi :"+test.chiSquareDataSetsComparison(binFrequency1,binFrequency2));

    }

    private static void createCustomHistogram( List<Integer> binRanges){


    }
    private static void printHistogram(String name,Histogram hist)
    {
        System.out.println("");
        System.out.println("Histogram "+ name);
        System.out.println("__________________");
        for (int i=0;i<hist.getNumberOfBins();i++) {
            System.out.println("("+hist.getBinAt(i).getLowerBound()+" - "+hist.getBinAt(i).getUpperBound()+") - "+hist.getBinAt(i).getFrequency());
        }
        System.out.println("__________________");
    }
}
