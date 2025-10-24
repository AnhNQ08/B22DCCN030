package WS;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface CharacterService {
    @WebMethod
    List<String> requestStringArray(String studentCode, String qCode);

    @WebMethod
    void submitCharacterStringArray(String studentCode, String qCode, List<String> data);
}
