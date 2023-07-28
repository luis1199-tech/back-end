package co.edu.iudigital.helpmeiud.service.iface;

import co.edu.iudigital.helpmeiud.dto.request.DelitoDTORequest;
import co.edu.iudigital.helpmeiud.dto.respose.DelitoDTO;
import co.edu.iudigital.helpmeiud.excepcions.BadRequestException;
import co.edu.iudigital.helpmeiud.excepcions.RestException;

import java.util.List;

public interface IDelitoService {

    /*
    * consultar todos los datos
    * */

    List<DelitoDTO> consultarTodos(); //Todo: throw exception

    /*
    * consultar delito por id
    *
    */
    DelitoDTO consultarPorId(Long id);

    /*
     * Guardar delito
     *
     */

    DelitoDTO guardarDelito(DelitoDTORequest delitoDTORequest) throws RestException;


    /**
     * borrar un delito por su id
     * @param id
     */
    void borrarDelitoPorId(Long id);
}
