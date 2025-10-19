package calculator;

import camp.nextstep.edu.missionutils.Console;

public class Application {
    public static void main(String[] args) {
        System.out.println("덧셈할 문자열을 입력해주세요.");
        String input = Console.readLine();

        try {
            int result = StringAddCalculator.add(input);
            System.out.println("결과 : " + result);
        } catch (IllegalArgumentException e) {
            // 과제 요구 예외는 그대로 재전달
            System.out.println(e.getMessage());
            throw e;
        } catch (Exception e) {
            // 그 밖의 모든 예외를 IllegalArgumentException으로 래핑해서 재전달
            throw new IllegalArgumentException(e);
        }
    }
}
