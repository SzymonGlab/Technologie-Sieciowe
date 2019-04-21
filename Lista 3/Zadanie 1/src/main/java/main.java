import java.io.FileNotFoundException;

public class main {

  public static void main(String[] args) throws FileNotFoundException{


    CodeStream cs = new CodeStream();

    cs.readFromFile();
    cs.addCRC();
    cs.addTerminateSequence();
    cs.codeStream();
    cs.addTerminateSequence();
    cs.writeToFile();

    System.out.println(cs.getOutputStream());

    DecodeStream ds = new DecodeStream();
    ds.readFromFile();
    ds.removeTerminateSequence();
    ds.decodeStream();
    ds.cutCRC();
    ds.calculateCRC();


    if(ds.isCRCequal()){
      System.out.println("CRC EQUAL!");

    } else
      System.out.println("DIFFERENT CRC!");

    ds.writeToFile();

  }

}

