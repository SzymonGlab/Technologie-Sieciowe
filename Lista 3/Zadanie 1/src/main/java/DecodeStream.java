import java.io.*;
import java.util.Scanner;
import java.util.zip.CRC32;

class DecodeStream {

  private CRC32 newCRC = new CRC32();
  private int onesCounter = 0;
  private Scanner sc;
  private String stream;
  private StringBuilder outputStream = new StringBuilder();
  private int i = 0;
  private int streamLength;
  private String prevElement;
  private int zerosCounter;
  private String oldCRC;


  void readFromFile() throws FileNotFoundException {

    File streamFile = new File("src\\main\\resources\\codedStream.txt");
    sc = new Scanner(streamFile);
    stream = sc.next();

  }

  void writeToFile(){
    try {
      PrintWriter out = new PrintWriter("src\\main\\resources\\decodedStream.txt");
      out.print(outputStream);
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void decodeStream() {

    while (streamLength > 0) {
      if (stream.substring(i, i + 1).equals("1")) {
        outputStream.append("1");
        onesCounter++;
      } else {
        if(onesCounter == 5){
          onesCounter = 0;
        } else {
          onesCounter = 0;
          outputStream.append("0");
        }
      }
      streamLength--;
      i++;
    }
  }


  void removeTerminateSequence() {
    stream = stream.substring(8, stream.length() - 8);
    streamLength = stream.length();
  }

  void calculateCRC(){
    newCRC.update(outputStream.toString().getBytes());
  }

  void cutCRC(){
    oldCRC = outputStream.substring(outputStream.length()-32,outputStream.length());

    outputStream.delete(outputStream.length()-32,outputStream.length());

  }

  boolean isCRCequal(){

    StringBuilder tempCRC =  new StringBuilder(Long.toBinaryString(newCRC.getValue()));
    while(tempCRC.length()<32){
      tempCRC.insert(0,0);
    }
    return oldCRC.equals(tempCRC.toString());
  }
}

