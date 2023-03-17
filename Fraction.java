public class Fraction {
    private int numerator;
    private int denominator;
    
    public static final Fraction ZERO = new Fraction(0, 1);

    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Знаменатель не может быть равен 0");
        }

        // Нормализация дроби
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }

        int gcd = gcd(Math.abs(numerator), denominator);
        this.numerator = numerator / gcd;
        this.denominator = denominator / gcd;
    }

    public Fraction add(Fraction other) {
        int newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction subtract(Fraction other) {
        int newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction multiply(Fraction other) {
        int newNumerator = this.numerator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction divide(Fraction other) {
        int newNumerator = this.numerator * other.denominator;
        int newDenominator = this.denominator * other.numerator;
        return new Fraction(newNumerator, newDenominator);
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    @Override
    public String toString() {
        if (denominator == 1) {
            return Integer.toString(numerator);
        } else {
            return numerator + "/" + denominator;
        }
    }

    public static Fraction fromString(String input) {
        String[] parts = input.split("/");
        if (parts.length == 1) {
            // Ввод целого числа
            int num = Integer.parseInt(parts[0]);
            return new Fraction(num, 1);
        } else if (parts.length == 2) {
            // Ввод дроби
            int num = Integer.parseInt(parts[0]);
            int den = Integer.parseInt(parts[1]);
            return new Fraction(num, den);
        } else {
            throw new IllegalArgumentException("Входная строка не является дробью: " + input);
        }
    }
}
