public class Regle {
    private int id;
    private Predicate<Code> condition;

    public Regle(int id, String expression) {
        this.id = id;
        this.condition = creerPredicate(expression);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean testerRegle(Code code) {
        return condition.test(code);
    }

    private Predicate<Code> creerPredicate(String expression) {
        return code -> {
            int b = code.getBleu();
            int j = code.getJaune();
            int v = code.getViolet();

            try {
                if (expression.contains("<")) {
                    String[] parts = expression.split("<");
                    return getValeur(parts[0], b, j, v) <
                           getValeur(parts[1], b, j, v);
                } else if (expression.contains(">")) {
                    String[] parts = expression.split(">");
                    return getValeur(parts[0], b, j, v) >
                           getValeur(parts[1], b, j, v);
                } else if (expression.contains("=")) {
                    String[] parts = expression.split("=");
                    return getValeur(parts[0], b, j, v) ==
                           getValeur(parts[1], b, j, v);
                } else if (expression.contains("%")) {
                    String[] parts = expression.split("%");
                    return getValeur(parts[0], b, j, v) %
                           Integer.parseInt(parts[1]) == 0;
                } else if (expression.contains("&")) {
                    String[] parts = expression.split("&");
                    return creerPredicate(parts[0]).test(code) &&
                           creerPredicate(parts[1]).test(code);
                } else if (expression.contains("!")) {
                    String[] parts = expression.split("!");
                    return !creerPredicate(parts[1]).test(code);
                } else if (expression.contains("*")) {
                    String[] parts = expression.split("\\*");
                    int n = Integer.parseInt(parts[0]);  // Nombre attendu d’occurrences
                    int valeur = Integer.parseInt(parts[1]);  // Valeur à vérifier
                    int count = 0;
                    if (b == valeur) count++;
                    if (j == valeur) count++;
                    if (v == valeur) count++;
                    return count == n;
                }
            } catch (Exception e) {
                System.err.println("Erreur dans l'expression : " + expression);
            }
            return false;
        };
    }

    private int getValeur(String token, int b, int j, int v) {
        switch (token.trim().toLowerCase()) {
            case "b": return b;
            case "j": return j;
            case "v": return v;
            case "p": return (b % 2 == 0 ? 1 : 0) + (j % 2 == 0 ? 1 : 0) + (v % 2 == 0 ? 1 : 0);
            case "i": return (b % 2 != 0 ? 1 : 0) + (j % 2 != 0 ? 1 : 0) + (v % 2 != 0 ? 1 : 0);
            default:
                if (token.matches("\\d+")) return Integer.parseInt(token);
                throw new IllegalArgumentException("Valeur inconnue : " + token);
        }
    }
}
