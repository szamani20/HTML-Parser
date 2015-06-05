package database;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception{

        String fileName = "database.rtd";
        File file = new File(fileName);

        List<Student> studentList = new LinkedList<Student>();
        Student student = new Student("null", "null");

        String address = "http://retrievetabledata.blogfa.com/";

            Document doc = Jsoup.connect(address).get();

            Elements tableElements = doc.select("table");

            Elements tableRowElements = tableElements.select(":not(thead) tr");

        for (int i=0; i<tableRowElements.size(); ++i) {
            Element row = tableRowElements.get(i);

            Elements rowItem = row.select("td");

            if (rowItem.size() != 0) {

                student.setName(rowItem.get(0).text());
                student.setLastName(rowItem.get(1).text());
                student.setNickName(rowItem.get(2).text());
                student.setPhoneNumber(rowItem.get(3).text());
                student.setStudentNumber(rowItem.get(4).text());
                student.setId(rowItem.get(5).text());
                student.setLastDescription(rowItem.get(6).text());

                List<String> des = new LinkedList<String>();
                des.add(student.getLastDescription());
                student.setDescription(des);
            }
        }

        studentList.add(student);

        FileOutputStream fos;
        ObjectOutputStream oos;
        try {

            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);

            Student[] students = studentList.toArray(new Student[studentList.size()]);

            oos.writeObject(students);

            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
