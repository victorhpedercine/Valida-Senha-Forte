import java.util.*;

public class App {

    static String[] senhasObvias = { "12345678", "senha123", "admin123" };

    public static String avaliarSenha(String senha) {
        if (senha.length() < 8) {
            return "DICA: A senha deve ter no mínimo 8 caracteres..";
        }
        boolean temNumero = false;
        for (int i = 0; i < senha.length(); i++) {
            if (Character.isDigit(senha.charAt(i))) {
                temNumero = true;
            }

        }
        if (!temNumero) {
            return "DICA: Adicione pelo menos um número à sua senha.";
        }

        for (int i = 0; i < senhasObvias.length; i++) {
            if (senha.equals(senhasObvias[i])) {
                return "ALERTA: Esta senha é muito comum ou óbvia.";
            }
        }
        boolean temMaiuscula = false;
        for (int i = 0; i < senha.length(); i++) {
            if (Character.isUpperCase(senha.charAt(i))) {
                temMaiuscula = true;
            }
        }
        if (!temMaiuscula) {
            return "DICA: Adicione pelo menos uma letra maiúscula à sua senha.";
        }
        return "SUCESSO: Sua senha passou nos critérios básicos!";

    }

    public static void main(String[] args) throws Exception {
        Scanner entrada = new Scanner(System.in);
        boolean aprovada = true;

        do {
            System.out.print("Digite uma senha fictícia: ");
            String senha = entrada.nextLine();
            String mensagem = avaliarSenha(senha);
            System.out.println(mensagem);
            if (mensagem.startsWith("SUCESSO")) {
                aprovada = false;
            }
        } while (aprovada);
        entrada.close();
    }
}
