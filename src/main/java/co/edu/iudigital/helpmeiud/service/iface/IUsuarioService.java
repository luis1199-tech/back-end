package co.edu.iudigital.helpmeiud.service.iface;

import co.edu.iudigital.helpmeiud.dto.request.UsuarioDTORequest;
import co.edu.iudigital.helpmeiud.dto.respose.UsuarioDTO;
import co.edu.iudigital.helpmeiud.model.Usuario;

import java.util.List;

public interface IUsuarioService {
    List<UsuarioDTO> consultarTodos();

    UsuarioDTO consultarPorId(Long id);

    UsuarioDTO consultarPorUsername(String username);

    UsuarioDTO guardar(UsuarioDTORequest usuarioDTORequest);

    Usuario findByUsername(String username);
}
