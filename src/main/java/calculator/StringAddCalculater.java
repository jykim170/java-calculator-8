package calculator;

import java.util.ArrayList;
import java.util.List;

public class StringAddCalculator {

    /**
     * 1단계 요구:
     * - ""(빈 문자열) => 0
     * - 기본 구분자(쉼표, 콜론)로 분리해 합산
     * - 구분자 확장을 대비해 리스트로 보관
     */
    public static int add(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        // 기본 구분자 리스트(추후 2단계에서 여기에 커스텀 구분자를 추가할 예정)
        List<String> delimiters = new ArrayList<>();
        delimiters.add(",");
        delimiters.add(":");

        // 구분자 리스트 -> 정규식 OR 로 변환
        String delimiterRegex = toRegex(delimiters);

        // 분리
        String[] tokens = input.split(delimiterRegex);

        // 합산 (1단계: 숫자만 온다고 가정, 공백 제거 정도만 수행)
        int sum = 0;
        for (String token : tokens) {
            if (token == null || token.isEmpty()) {
                // 연속 구분자 등 상세 검증은 3단계에서 강화 예정
                continue;
            }
            String trimmed = token.trim();
            // 1단계에서는 음수/비숫자 예외 처리를 강하게 하지 않고 최소 구현
            sum += Integer.parseInt(trimmed);
        }
        return sum;
    }

    private static String toRegex(List<String> delims) {
        // 각 구분자를 안전하게 escape 해서 OR 로 묶는다
        return delims.stream()
                .map(java.util.regex.Pattern::quote)
                .reduce((a, b) -> a + "|" + b)
                .orElse(",");
    }
}
