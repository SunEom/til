package PizzaFactory;

public class NYStyleClamPizza extends Pizza {
    public NYStyleClamPizza() {
        name = "뉴욕 스타일 조개 피자";
        dough = "씬 크러스트 도우";
        sauce = "마리나라 소스";

        toppings.add("싱싱한 조개");
        toppings.add("잘게 썬 레지아노 치즈");
    }
}
