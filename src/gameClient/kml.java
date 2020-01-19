package gameClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class represent a KML_Logger object that creates a KML file for each game.
 * KML_Logger attributes :
 * 1.stage
 * 2.StringBuilder - to concat all the game info.
 */
public class kml {

    private int stage;
    private StringBuilder info;

    /**
     * Default constructor
     */
    public  kml(){}
    /**
     * Constructor, initialize the object and concat the standard start of a KML file.
     * @param level
     */
    public kml(int level) {
        this.stage = level;
        info = new StringBuilder();
        kmlStartFile();
    }

    /**
     * Concat the opening string for the KML file.
     * Sets the elements of the game such as: node, fruit and robot that will be added as a placemark to the KML file.
     */
    public void kmlStartFile()
    {
    	
        info.append(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                        "<kml xmlns=\"http://earth.google.com/kml/2.2\">\r\n" +
                        "  <Document>\r\n" +
                        "    <name>" + "Game stage :"+this.stage + "</name>" +"\r\n"+
                        " <Style id=\"node\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>http://maps.google.com/mapfiles/kml/pal3/icon35.png</href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>" +
                        " <Style id=\"fruit-banana\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>http://maps.google.com/mapfiles/kml/shapes/euro.png</href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>" +
                        " <Style id=\"fruit-apple\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>http://maps.google.com/mapfiles/kml/shapes/dollar.png</href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>" +
                        " <Style id=\"robot\">\r\n" +
                        "      <IconStyle>\r\n" +
                        "        <Icon>\r\n" +
                        "          <href>http://maps.google.com/mapfiles/kml/shapes/motorcycling.png></href>\r\n" +
                        "        </Icon>\r\n" +
                        "        <hotSpot x=\"32\" y=\"1\" xunits=\"pixels\" yunits=\"pixels\"/>\r\n" +
                        "      </IconStyle>\r\n" +
                        "    </Style>"
        );
    }

    /**
     * Add placemark to the KML file.
     * Each game element has a placemark id.
     * @param id
     * @param location
     */
    public void addPlaceMark(String id, String location)
    {
        LocalDateTime time = LocalDateTime.now();
        info.append(
                "    <Placemark>\r\n" +
                        "      <TimeStamp>\r\n" +
                        "        <when>" + time+ "</when>\r\n" +
                        "      </TimeStamp>\r\n" +
                        "      <styleUrl>#" + id + "</styleUrl>\r\n" +
                        "      <Point>\r\n" +
                        "        <coordinates>" + location + "</coordinates>\r\n" +
                        "      </Point>\r\n" +
                        "    </Placemark>\r\n"
        );
    }

    
    
    /**
     * Concat the closing string for the KML file.
     * Creates a kml file name=stage.kml and save it to the data folder in this project.
     */
    public void kmlEnd()
    {
        info.append("  \r\n</Document>\r\n" +
                "</kml>"
        );
        kmlSave();
    }
    public void kmlSave() {
        try
        {
            File f=new File("data/"+this.stage+".kml");
            PrintWriter pw=new PrintWriter(f);
            pw.write(info.toString());
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    }
