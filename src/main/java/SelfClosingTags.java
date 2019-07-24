import java.util.Arrays;
import java.util.Optional;

public class SelfClosingTags { ;
    private static final String[] selfClosingTags = {
            "area",
            "base",
            "br",
            "embed",
            "hr",
            "iframe",
            "img",
            "input",
            "link",
            "meta",
            "param",
            "source",
            "track"
    };

    public static boolean isSelfClosingTag(String line){
        return Arrays.stream(selfClosingTags).filter(x->x.equals(line)).count()>0;
    }
}
