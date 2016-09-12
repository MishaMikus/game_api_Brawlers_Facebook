import java.util.UnknownFormatConversionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class StringArgCounter {
    private static final Pattern fsPattern = Pattern.compile("%(\\d+\\$)?([-#+ 0,(<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])");
    private String format;

    StringArgCounter(String format) {
        this.format = format;
    }

    int length() {
        int count = 0;
        Matcher m = fsPattern.matcher(format);
        for (int i = 0, len = format.length(); i < len; ) {
            if (m.find(i)) {
                if (m.start() != i) {
                    checkText(format, i, m.start());
                }
                count++;
                i = m.end();
            } else {
                checkText(format, i, len);
                break;
            }
        }
        return count;
    }

    private void checkText(String s, int start, int end) {
        for (int i = start; i < end; i++) {
            if (s.charAt(i) == '%') {
                char c = (i == end - 1) ? '%' : s.charAt(i + 1);
                throw new UnknownFormatConversionException(String.valueOf(c));
            }
        }
    }
}

