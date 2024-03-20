package controller;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * Utils class contains static methods to read and write XML files.
 * Seperated from functionality of the model and view.
 */
public class UtilsXML {
  /**
   * Creates an XML file of the user's schedule in the directory given by the method.
   * Calling this method twice will overwrite the current file in the given location.
   *
   * @param filePathToSave location where file should be saved
   * @param userName extra information to make file path unique (to user's schedule)
   * @param xmlContents contents that XML file should contain. bulk of actual info of file
   */
  public static void writeToFile(String filePathToSave, String userName, String xmlContents) {
    try {
      Writer file = new FileWriter(filePathToSave);
      file.write("<?xml version=\"1.0\"?>\n");
      file.write("<schedule id='" + userName + "'>");
      file.write(xmlContents);
      file.write("</schedule>");
      file.close();
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }

  /**
   * Reads the specific file, based on the given file path.
   * @param filePath file path where xml is located
   * @return a Document representing the current state of the XML, which will be used in
   *         other areas to interpret XML contents
   * @throws IllegalStateException if document builder can't be created or
   *                               the file can't be open or parsed
   */
  public static Document readXML(String filePath) {
    try {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmlDoc = builder.parse(new File(filePath));
      xmlDoc.getDocumentElement().normalize();
      return xmlDoc;
    } catch (ParserConfigurationException ex) {
      throw new IllegalStateException("Error in creating the builder");
    } catch (IOException ioEx) {
      throw new IllegalStateException("Error in opening the file");
    } catch (SAXException saxEx) {
      throw new IllegalStateException("Error in parsing the file");
    }
  }
}