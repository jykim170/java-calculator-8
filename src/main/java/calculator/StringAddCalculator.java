package calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringAddCalculater {

    public static int add(String input) {
        // 1단계: 빈 문자열 0
        if (input == null || input.isEmpty()) {
            return 0;
        }

        // 기본 구분자 목록
        List<String> delimiters = new ArrayList<>();
        delimiters.add(",");
        delimiters.add(":");

        String numbers = input;

        // ===== 2단계: 커스텀 구분자 처리 =====
        if (numbers.startsWith("//")) {
            int newlineIdx = numbers.indexOf('\n');
            if (newlineIdx < 0) {
                throw new IllegalArgumentException("커스텀 구분자 형식이 올바르지 않습니다: 개행(\\n) 없음");
            }
            String custom = numbers.substring(2, newlineIdx);
            if (custom.length() != 1) {
                throw new IllegalArgumentException("커스텀 구분자는 한 글자여야 합니다.");
            }
            delimiters.add(custom);
            numbers = numbers.substring(newlineIdx + 1); // 본문
            if (numbers.isEmpty()) {
                throw new IllegalArgumentException("커스텀 구분자 본문이 비어 있습니다.");
            }
        }

        // 구분자 리스트 -> 정규식 OR
        String delimiterRegex = toRegex(delimiters);

        String[] tokens = numbers.split(delimiterRegex, -1); // 빈 토큰도 보존
        int sum = 0;
        for (String token : tokens) {
            // 상세 검증은 3단계에서 강화 예정. 2단계에선 커스텀 형식만 엄격 체크.
            if (token == null || token.isEmpty()) {
                // 커스텀 케이스에서 완전 빈 본문/연속 구분자 방지용
                throw new IllegalArgumentException("잘못된 입력입니다.");
            }
            sum += Integer.parseInt(token.trim());
        }
        return sum;
    }

    private static String toRegex(List<String> delims) {
        return delims.stream()
                .map(Pattern::quote)
                .reduce((a, b) -> a + "|" + b)
                .orElse(",");
    }
}
