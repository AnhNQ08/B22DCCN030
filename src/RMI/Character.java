package RMI;

import java.rmi.Naming;
import java.util.LinkedHashMap;
import java.util.Map;

public class Character {
    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN030";
        String qCode = "qrrTHe9X";

        CharacterService service = (CharacterService) Naming.lookup("rmi://203.162.10.109/RMICharacterService");

        String input = service.requestCharacter(studentCode, qCode);

        Map<java.lang.Character, Integer> freq = new LinkedHashMap<>();
        for (char c : input.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        StringBuilder result = new StringBuilder();
        for (Map.Entry<java.lang.Character, Integer> e : freq.entrySet()) {
            result.append(e.getKey()).append(e.getValue());
        }

        service.submitCharacter(studentCode, qCode, result.toString());

    }
}
