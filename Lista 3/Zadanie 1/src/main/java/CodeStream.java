import java.io.*;
import java.util.Scanner;
import java.util.zip.CRC32;

class CodeStream {

  private static final String terminateSequence = "01111110";

  private BufferedWriter writer;
  private CRC32 crc = new CRC32();
  private int onesCounter = 0;
  private Scanner sc;
  private String stream;
  private StringBuilder outputStream = new StringBuilder();
  private StringBuilder inputStream;
  private int i =0;
  private int inputStreamLength;


  void readFromFile() throws FileNotFoundException {

    File streamFile = new File("src/main/resources/stream.txt");
    sc = new Scanner(streamFile);
    sc.useDelimiter("\\Z");
    stream = sc.next();
    System.out.println("INPUT STREAM = " + stream);
    inputStream= new StringBuilder(stream);
    inputStreamLength = stream.length();
  }

  void codeStream(){

    while(inputStreamLength>0){

//      if(i<stream.length()){
//        System.out.println(inputStream.substring(i,i+1));
//      }

      if(onesCounter==5){
        outputStream.append("0");
        onesCounter = 0;
      }

      if(inputStream.substring(i,i+1).equals("1")){
        onesCounter++;
        outputStream.append("1");
      } else {
        onesCounter = 0;
        outputStream.append("0");
      }

//      if(i<stream.length()){
//        System.out.println(outputStream);
//        System.out.println();
//      }

      inputStreamLength--;
      i++;


    }
  }


  void addTerminateSequence(){
    outputStream.append(terminateSequence);
  }

  void addCRC(){

    crc.update(stream.getBytes());

    StringBuilder calculatedCRC = new StringBuilder();

    calculatedCRC.append(Long.toBinaryString(crc.getValue()));

    while(calculatedCRC.length() < 32){
      calculatedCRC.insert(0,0);
    }

    System.out.println("CALCULATED CRC = " + calculatedCRC);
    inputStream.append(calculatedCRC);
    inputStreamLength = inputStream.length();
  }

  void writeToFile(){
    try {
      PrintWriter out = new PrintWriter("src\\main\\resources\\codedStream.txt");
      out.print(outputStream);
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public StringBuilder getOutputStream() {
    return outputStream;
  }
}
