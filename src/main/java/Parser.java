import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    private static final List<String> PARENT_STACK = new LinkedList<>();
    public Boolean parsePug(String pathString){
        //TODO change path variable to dynamic
        Path path = Paths.get("/Users/nursultansadyk/Projects/pugfile.pug");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            StringBuffer stringBuffer = new StringBuffer();
            String currentLine = null;
            while ((currentLine = reader.readLine()) != null) {
                stringBuffer.append(parseLine(currentLine));
            }
            FileActions.write(stringBuffer.toString(),"/Users/nursultansadyk/Projects/test.html");
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    private StringBuffer parseLine(String line) throws NullPointerException{
        if(isParent(line)){
            PARENT_STACK.pu
        }
            StringBuffer stringBuffer = new StringBuffer();
            String[] lineParts = line.split(":");
            if(lineParts.length>1){
                //TODO parse as Block Expansion it means a: img equals to <a> <img /> </a>
                stringBuffer.append("<");
                stringBuffer.append(lineParts[0]);
                stringBuffer.append(">");
                parseSelfClosingTags(stringBuffer,line);
                stringBuffer.append("<");
                stringBuffer.append(lineParts[0]);
                stringBuffer.append("/> \n");
            }else{
             lineParts = line.split("/");
                 if(lineParts.length>1){
                //TODO self closing tag foo/ or foo(bar='baz')/ equals to <foo/> or <foo bar="baz" />
                     stringBuffer.append("<");
                     stringBuffer.append(lineParts[1]);
                     stringBuffer.append("/> \n");
                 }else{
                    parseSelfClosingTags(stringBuffer,line);
                 }
            }
        return stringBuffer;
    }

    private boolean isParent(String line){
        char[] chars = line.toCharArray();
        int counter = 0;
        for (char c:chars
             ) {
            if(c==' ')
                counter++;
        }
        return counter < 2;
    }
    private void parseSelfClosingTags(StringBuffer stringBuffer, String line){
        if(SelfClosingTags.isSelfClosingTag(line)){
            stringBuffer.append("<");
            stringBuffer.append(line);
            stringBuffer.append("/> \n");
        }else{
            stringBuffer.append("<");
            stringBuffer.append(line);
            stringBuffer.append(">");
            stringBuffer.append("<");
            stringBuffer.append(line);
            stringBuffer.append("/> \n");
        }
    }
}
