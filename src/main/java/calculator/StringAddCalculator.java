package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringAddCalculator {

    public static int add(String input) {
        // 1단계: 빈 문자열 0
        if (input == null || input.isEmpty()) {
            return 0;
        }

        // 기본 구분자
        List<String> delimiters = new ArrayList<>();
        delimiters.add(",");
        delimiters.add(":");

        String numbers = input;

        // 2단계: 커스텀 구분자 처리 (한 글자)
        if (numbers.startsWith("//")) {
            Matcher m = Pattern.compile("//(.)" + "(?:\\\\n|\\n)" + "(.*)", Pattern.DOTALL).matcher(numbers);
            if (!m.matches()) {
                throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다.");
            }
            String custom = m.group(1);
            delimiters.add(custom);
            numbers = m.group(2);
            if (numbers.isEmpty()) {
                throw new IllegalArgumentException("커스텀 구분자 본문이 비어 있습니다.");
            }
        }

        // 3단계: 검증 강화
        // - 연속 구분자/마지막 구분자 등 빈 토큰 검출 위해 -1 사용
        String regex = toRegex(delimiters);
        String[] tokens = numbers.split(regex, -1);

        int sum = 0;
        for (String token : tokens) {
            if (token == null || token.isBlank()) {
                // ",," 또는 끝이 구분자 등
                throw new IllegalArgumentException("잘못된 구분자 배치입니다.");
            }
            String t = token.trim();

            // 숫자만 허용
            if (!t.matches("\\d+|[-]\\d+")) { // 음수도 판별 위해 우선 허용 후 아래에서 거름
                throw new IllegalArgumentException("숫자만 입력할 수 있습니다: " + t);
            }

            int n = Integer.parseInt(t);

            // 음수 금지
            if (n < 0) {
                throw new IllegalArgumentException("음수는 허용되지 않습니다: " + n);
            }

            sum += n;
        }
        return sum;
    }

    private static String toRegex(List<String> delims) {
        // 각 구분자를 안전하게 escape하여 OR로 연결
        return delims.stream()
                .map(Pattern::quote)
                .reduce((a, b) -> a + "|" + b)
                .orElse(",");
    }
}
